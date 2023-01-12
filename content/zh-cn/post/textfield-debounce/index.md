---
title: 使用 Flow 实现 TextField 的防抖功能
slug: textfield-debounce
categories:
    - Jetpack Compose
    - Android
date: 2023-01-13 02:30:00+1000
image: cover.jpg
---

## 前言

在我们开发实际的 App 中，我们经常会遇到检测用户输入的文本字段是否合法，例如检查用户名是否已经被其他人注册了，而为了减轻后端的压力，我们并不能在用户每输入新的字符或是删除字符的时候就发送请求检测用户名是否合法，这样会导致我们发送很多不必要的请求。

例如在表单中输入 `whitescent`，我们可能只想要检测 `whitescent` 这个用户名是否被注册，而不是在输入前面的 w-h-i 之类的字符就发送请求检测用户名是否合法。

## Flow

Flow 是一个强大的数据流类型，它能够在很多应用场景下进行建模，并且带有很多好用的操作符函数，接下来我们就来使用 flow 中的 `debounce` 函数来实现防抖功能。

最终实现的效果如下：

![](https://raw.githubusercontent.com/whitescent/whitescent.github.io/main/content/zh-cn/post/textfield-debounce/demo.gif)

## 前期工作

UI 层很容易，不过我们需要注意的是，在 Jetpack Compose 中写组件，我们需要尽量保持状态提升的概念以避免重组带来的性能问题，我们先将我们的 `TextField` 封装成一个 `@Composable` 函数

```kotlin
@Composable
fun MyTextField(
  state: MyTextFieldUiState,
  onValueChange: (String) -> Unit
) {
    ...
}
```

接着我们为 `TextField` 写一个界面状态，可以在[这里](https://developer.android.com/topic/architecture/ui-layer?hl=zh-cn#define-ui-state)查看关于界面状态的更多信息，其中我们分别为 `TextField` 设计了两种特殊的状态，一个是在用户输入的时候显示进度条的 `isTyping` 另一个是根据用户输入的名字判断是否符合要求的 `isError`。 

```kotlin
enum class ErrorType {
  LengthLimited, InvalidName
}

data class MyTextFieldUiState(
  val text: String = "",
  val isError: Boolean = false,
  val isTyping: Boolean = false,
  val error: ErrorType? = null
)
```

## 为 TextField 建立数据流

`StateFlow` 是一个数据容器式可观察数据流，它可以知道当前数据流的状态以及为数据流设置新的状态，`StateFlow` 也适用于维护可观察的不可变状态的类。

如果我们需要更新 `StateFlow` 的状态，我们需要对 `MutableStateFlow` 的 `value` 属性进行更新。

接下来，我们在 `viewModel` 中创建 `textField` 的数据流

```kotlin
@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {
    private val _textFieldState = MutableStateFlow(MyTextFieldUiState())
    val textFieldUiState = _textFieldState.asStateFlow()  
}
```

我们用 `.asStateFlow` 方法给界面公开我们的数据流，数据流的具体更新逻辑应当在 `viewModel` 层完成。

接下来为 `TextField` 的文本更新写方法：

```kotlin
@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {
  private val _textFieldState = MutableStateFlow(MyTextFieldUiState())
  val textFieldUiState = _textFieldState.asStateFlow()  

  fun onValueChange(text: String) {
    _textFieldState.value = _textFieldState.value.copy(text = text, isTyping = true)
  }
}
```

UI 层的代码：

```kotlin
@Composable
fun MyTextField(
  state: MyTextFieldUiState,
  onValueChange: (String) -> Unit
) {
  Column {
    OutlinedTextField(
      value = state.text,
      onValueChange = onValueChange,
      isError = state.isError
    )
    AnimatedVisibility(state.text.isNotEmpty()) {
      Column { ... }
    }
  }
}
```

接下来我们编写具体的防抖逻辑，实际上得益于 flow 强大的运算符，写起来很简单：

```kotlin
@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {

  private val _textFieldState = MutableStateFlow(MyTextFieldUiState())
  val textFieldUiState = _textFieldState.asStateFlow()

  init {
    viewModelScope.launch {
      _textFieldState
        .debounce(800) // 设置防抖动的阈值，debounce 方法会在设定的期间过滤所有新发出的值
        .filter { // 设置只有符合条件的 value 才能进行接下来的 collect 操作
          it.text.isNotEmpty()
        }
        .collect {
          when {
            it.text.length > 15 -> _textFieldState.emit(
              it.copy(isError = true, isTyping = false, error = ErrorType.LengthLimited)
            )
            usernameList.contains(it.text) -> _textFieldState.emit(
              it.copy(isError = true, isTyping = false, error = ErrorType.InvalidName)
            )
            else -> _textFieldState.emit(
              it.copy(isError = false, isTyping = false, error = null)
            )
          }
        }
    }
  }
}
```

`collect` 方法里就是我们检测用户名是否符合预期的地方，这里我简单模拟了一下，实际上在这里也许需要请求后端来获得结果，不过实际上大同小异。

在检测完，我们只需要通过 emit 方法或者手动为 _textFieldState 设置新的 value 来更新最新的数据流。

在 UI 方面，我们也需要写一些具体的逻辑。

```kotlin
setContent {
  AppTheme {
    val viewModel: MyPageViewModel = hiltViewModel()
    val state by viewModel.textFieldUiState.collectAsState() 
    // 获取 textFieldUiState，并当作参数向下传递给 @Composable 组件

    MyTextField(
      state = state,
      onValueChange = viewModel::onValueChange
    )
  }
}
```

`MyTextField`：

```kotlin
@Composable
fun MyTextField(
  state: MyTextFieldUiState,
  onValueChange: (String) -> Unit
) {
  Column {
    OutlinedTextField(
      value = state.text,
      onValueChange = onValueChange,
      label = { Text("用户名") },
      isError = state.isError
    )
    AnimatedVisibility(state.text.isNotEmpty()) {
      Column {
        // 首先根据用户是否在输入划分 TextField 底下的提示
        when (state.isTyping) {
          true -> { CircularProgressIndicator(...) }
          else -> {
            when (state.isError) {
              true -> {
                when (state.error) {
                  ErrorType.InvalidName -> ErrorText("此用户名不可用")
                  ErrorType.LengthLimited -> ErrorText("用户名长度超出限制")
                  else -> Unit
                }
              }
              else -> {
                Row(
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = "此用户名可用")
                  Icon(Icons.Rounded.Done)
                }
              }
            }
          }
        }
      }
    }
  }
}
```

![](https://raw.githubusercontent.com/whitescent/whitescent.github.io/main/content/zh-cn/post/textfield-debounce/demo.gif)

