package root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.util.DebugLogger

@Composable
fun App() {
  val platformContext = LocalPlatformContext.current
  setSingletonImageLoaderFactory {
    ImageLoader(platformContext)
      .newBuilder()
      .logger(DebugLogger())
      .build()
  }
  Box(
    modifier = Modifier.fillMaxSize()
      .background(Color.Gray),
    contentAlignment = Alignment.Center
  ) {
    Column {
      BasicText("Nem")
      AsyncImage(
        model = "https://avatars.githubusercontent.com/u/31311826?v=4",
        contentDescription = null,
        modifier = Modifier.clip(CircleShape)
          .size(120.dp)
      )
    }
  }
}
