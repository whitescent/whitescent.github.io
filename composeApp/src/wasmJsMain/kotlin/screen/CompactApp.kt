package root.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import extension.clickableWithoutRipple
import extension.pxRound
import homepage.composeapp.generated.resources.Res
import homepage.composeapp.generated.resources.github
import homepage.composeapp.generated.resources.header_bg
import homepage.composeapp.generated.resources.soundcloud
import org.jetbrains.compose.resources.painterResource
import ui.component.CenterRow
import ui.component.Gap
import ui.font.alibabaPuHuiTi

@Composable
fun CompactApp(
  modifier: Modifier = Modifier
) = Column(
  modifier = modifier
    .padding(bottom = 36.dp)
) {
  val uriHandler = LocalUriHandler.current
  val density = LocalDensity.current
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(160.dp + avatarSize / 2)
  ) {
    Image(
      painter = painterResource(Res.drawable.header_bg),
      contentDescription = null,
      modifier = Modifier.fillMaxWidth().height(160.dp),
      contentScale = ContentScale.Crop
    )

    AsyncImage(
      model = "https://avatars.githubusercontent.com/u/31311826?v=4",
      contentDescription = null,
      modifier = Modifier
        .layout { measurable, constraints ->
          val placeable = measurable.measure(constraints)
          layout(placeable.width, placeable.height) {
            placeable.place(
              x = constraints.maxWidth / 2 - placeable.width / 2,
              y = -placeable.height / 2 + density.pxRound(160.dp)
            )
          }
        }
        .shadow(12.dp, CircleShape)
        .clip(CircleShape)
        .size(avatarSize),
      contentScale = ContentScale.Crop
    )
  }
  Gap(16.dp)
  CenterRow(
    modifier = Modifier.align(Alignment.CenterHorizontally)
  ) {
    Icon(
      painter = painterResource(Res.drawable.github),
      contentDescription = null,
      modifier = Modifier
        .size(28.dp)
        .clickableWithoutRipple { uriHandler.openUri("https://github.com/whitescent") },
      tint = Color(0xFF181717)
    )
    Gap(36.dp)
    Icon(
      painter = painterResource(Res.drawable.soundcloud),
      contentDescription = null,
      modifier = Modifier
        .size(28.dp)
        .clickableWithoutRipple { uriHandler.openUri("https://soundcloud.com/lovetheeif/tracks") },
      tint = Color(0xFFFF5500)
    )
  }
  Gap(16.dp)
  Column(
    modifier = Modifier.padding(horizontal = 24.dp)
  ) {
    BasicText(
      text = "Hello, my name is Nem, I am currently an Android developer, and also an amateur electronic music producer. I like listening to electronic music and rock, and I am also learning Japanese.",
      style = TextStyle(
        fontFamily = alibabaPuHuiTi,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
      )
    )
    Gap(30.dp)
    Lyrics(Modifier.height(IntrinsicSize.Min))
  }
}
