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
  state: InputUiState,
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

data class InputUiState(
  val text: String = "",
  val error: ErrorType? = null,
  val isTyping: Boolean = false,
) {
  val isError: Boolean inline get() = error != null
  inline fun isEmpty() = text.isEmpty()
}
```

## 为 TextField 建立数据流

`StateFlow` 是一个数据容器式可观察数据流，它可以知道当前数据流的状态以及为数据流设置新的状态，`StateFlow` 也适用于维护可观察的不可变状态的类。

如果我们需要更新 `StateFlow` 的状态，我们需要对 `MutableStateFlow` 的 `value` 属性进行更新。

接下来，我们在 `viewModel` 中创建 `textField` 的数据流

```kotlin
@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {
  private val inputText = MutableStateFlow("")
  private val inputUi = MutableStateFlow(InputUiState())
  val inputUiState = inputUi.asStateFlow()
}
```

在刚才的代码中，我们为 `TextField` 分别创建了两个不同的可变数据流，`inputText` 用于检测用户输入的东西是否符合预期，而 `inputUi` 则是纯粹的维护界面状态，
我们用 `.asStateFlow` 方法给界面公开我们的数据流。


接下来为 `TextField` 的文本更新写方法：

```kotlin
@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {
  private val inputText = MutableStateFlow("")
  private val inputUi = MutableStateFlow(InputUiState())
  val inputUiState = inputUi.asStateFlow()

  fun onValueChange(text: String) {
    inputText.update { text }  // 更新用于验证的输入
    inputUi.update { it.copy(text = text, isTyping = true) } 
    // 更新用于显示在 UI 上的输入
  }
}
```

UI 层的代码：

```kotlin
@Composable
fun MyTextField(
  state: InputUiState,
  onValueChange: (String) -> Unit
) {
  Column {
    OutlinedTextField(
      value = state.text,
      onValueChange = onValueChange,
      isError = state.isError
    )
    AnimatedVisibility(!state.isEmpty()) {
      Column { ... }
    }
  }
}
```

接下来我们编写具体的防抖逻辑，实际上得益于 flow 强大的运算符，写起来很简单：

```kotlin
@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {

  private val inputText = MutableStateFlow("")
  private val inputUi = MutableStateFlow(InputUiState())
  val inputUiState = inputUi.asStateFlow()

  init {
    viewModelScope.launch {
      inputText
        .debounce(800)
        .filterNot(String::isEmpty)
        .collectLatest {
          // 防抖验证 Input 是否可用，以更新反馈可用性的 UI
          inputUi.value = inputUi.value.copy(
            isTyping = false,
            error = when {
              it.length > 15 -> ErrorType.LengthLimited
              it in usernameList -> ErrorType.InvalidName
              else -> null
            },
          )
        }
    }
  }
}
```

`collectLatest` 方法里就是我们检测用户名是否符合预期的地方，这里我简单模拟了一下，实际上在这里也许需要请求后端来获得结果，不过实际上大同小异。
在检测完，我们通过 inputUi 的 flow 流来为界面层更新最新的状态。

网络请求的简单版本：

```kotlin
inputText
  .debounce(800)
  .filterNot(String::isEmpty)
  .onEach {
    ... // 处理网络请求并更新 UI 状态
  }
  .catch { throwable ->
    ... // 更新 UiState，告诉用户错误的原因
  }
  .collectLatest { }
```

在 UI 方面，我们也需要写一些具体的逻辑。

```kotlin
setContent {
  AppTheme {
    val viewModel: MyPageViewModel = hiltViewModel()
    val state by viewModel.inputUiState.collectAsState()
    // 获取 inputUiState，并当作参数向下传递给 @Composable 组件

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
  state: InputUiState,
  onValueChange: (String) -> Unit
) {
  Column {
    OutlinedTextField(
      value = state.text,
      onValueChange = onValueChange,
      label = { Text("用户名") },
      isError = state.isError
    )
    AnimatedVisibility(!state.isEmpty()) {
      Column {
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
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

