package root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import extension.dp
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_55_Regular
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_65_Medium
import homepage.composeapp.generated.resources.Res
import homepage.composeapp.generated.resources.header_bg
import kotlinx.browser.window
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.asDeferred
import kotlinx.coroutines.await
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont
import org.jetbrains.compose.resources.preloadImageBitmap
import org.khronos.webgl.ArrayBuffer
import org.w3c.fetch.Response
import root.screen.CompactApp
import root.screen.ExpandedApp

private suspend fun loadResAsync(url: String): Deferred<ArrayBuffer> {
  return window.fetch(url).await<Response>().arrayBuffer().asDeferred()
}

suspend fun loadRes(url: String): ArrayBuffer {
  return loadResAsync(url).await()
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalResourceApi::class)
@Composable
fun App() = Column(
  modifier = Modifier.fillMaxSize()
) {
  val platformContext = LocalPlatformContext.current
  val windowSizeClass = calculateWindowSizeClass()
  val density = LocalDensity.current
  val currentWidth = density.dp(LocalWindowInfo.current.containerSize.width)
  val windowWidthSizeClass = windowSizeClass.widthSizeClass
  var showWebsite by remember { mutableStateOf(false) }

  val fontFamilyResolver = LocalFontFamilyResolver.current

  val font by preloadFont(Res.font.AlibabaPuHuiTi_3_55_Regular)
  val font2 by preloadFont(Res.font.AlibabaPuHuiTi_3_65_Medium, FontWeight.Medium)
  val image by preloadImageBitmap(Res.drawable.header_bg)

  setSingletonImageLoaderFactory {
    ImageLoader(platformContext)
      .newBuilder()
      .build()
  }

  if (font != null && font2 != null && image != null) showWebsite = true

  if (showWebsite) {
    if (currentWidth < 1000.dp) {
      CompactApp(Modifier.verticalScroll(rememberScrollState()))
      return@Column
    }
    when (windowWidthSizeClass) {
      WindowWidthSizeClass.Expanded -> {
        ExpandedApp()
      }
      WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
        CompactApp(Modifier.verticalScroll(rememberScrollState()))
      }
    }
  }
}
