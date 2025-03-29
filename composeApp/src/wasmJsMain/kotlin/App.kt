package root

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import extension.pxRound
import homepage.composeapp.generated.resources.Res
import homepage.composeapp.generated.resources.compose_multiplatform
import homepage.composeapp.generated.resources.github
import homepage.composeapp.generated.resources.header_bg
import homepage.composeapp.generated.resources.soundcloud
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.component.CenterRow
import ui.component.Gap
import ui.font.alibabaPuHuiTi

@Composable
fun App() = Column(
  modifier = Modifier.fillMaxSize()
) {
  val platformContext = LocalPlatformContext.current
  val density = LocalDensity.current

  setSingletonImageLoaderFactory {
    ImageLoader(platformContext)
      .newBuilder()
      .build()
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(250.dp + avatarSize / 2)
  ) {
    Image(
      painter = painterResource(Res.drawable.header_bg),
      contentDescription = null,
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
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
              x = density.pxRound(startHorizontalPadding),
              y = -placeable.height / 2 + density.pxRound(250.dp)
            )
          }
        }
        .shadow(12.dp, CircleShape)
        .clip(CircleShape)
        .size(avatarSize),
      contentScale = ContentScale.Crop
    )
  }

  Gap(30.dp)

  Column(
    modifier = Modifier
      .padding(horizontal = startHorizontalPadding)
  ) {
    Content(
      modifier = Modifier.weight(1f)
    )

    CenterRow {

      BasicText(
        text = "Build with Compose Multiplatform",
        style = TextStyle(
          fontFamily = alibabaPuHuiTi,
          fontSize = 13.sp,
          color = Color(0xFF8B8B8B)
        )
      )
      Gap(6.dp)
      Image(
        painter = painterResource(Res.drawable.compose_multiplatform),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
      )
    }

    Gap(30.dp)
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier
) = Row(modifier) {
  val uriHandler = LocalUriHandler.current
  Column {
    BasicText(
      text = "Hello, my name is Nem, I am currently an Android developer, and also an amateur electronic music producer. I like listening to electronic music and rock, and I am also learning Japanese.",
      style = TextStyle(
        fontFamily = alibabaPuHuiTi,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
      ),
      modifier = Modifier.widthIn(max = 500.dp)
    )
    Gap(26.dp)
    Row {
      Lyrics(Modifier.height(IntrinsicSize.Min))
      SocialCard(
        icon = Res.drawable.github,
        name = "Github",
        background = Color(0xFF181717),
        tint = Color.White,
        onClick = { uriHandler.openUri("https://github.com/whitescent") },
        modifier = Modifier.align(Alignment.Bottom)
      )
      Gap(46.dp)
      SocialCard(
        icon = Res.drawable.soundcloud,
        name = "SoundCloud",
        background = Color(0xFFFF5500),
        tint = Color.White,
        onClick = { uriHandler.openUri("https://soundcloud.com/lovetheeif/tracks") },
        modifier = Modifier.align(Alignment.Bottom)
      )
    }
  }
}

@Composable
private fun Lyrics(
  modifier: Modifier = Modifier
) = CenterRow(modifier) {
  Box(
    modifier = Modifier
      .width(5.dp)
      .fillMaxHeight()
      .clip(CircleShape)
      .background(Color(0xFFD3D3D3))
  )
  Gap(24.dp)
  BasicText(
    text = "陽射しを吸い込んで眺め\u2028この世の果てを見せて欲しいから\u2028まだ息をしていてほしいだけ\u2028昔聴いた曲の名前は\u2028もう忘れてしまったから\u2028ポケットにしまっていた\u2028心に触れていく\u2028It's still hurting\u2028I'm too worthless\u2028Try to hide it, but\u2028I can't make it\u2028Too heavy for my heart\u2028Scream out loud\u2028Saying goodbye",
    style = TextStyle(
      fontFamily = alibabaPuHuiTi,
      fontSize = 20.sp,
      fontWeight = FontWeight.Normal,
      color = Color(0xFF8B8B8B)
    ),
    modifier = Modifier.widthIn(max = 300.dp)
  )
}

@Composable
private fun SocialCard(
  icon: DrawableResource,
  name: String,
  background: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(24.dp),
  tint: Color = Color.Unspecified,
) {
  val interaction = remember { MutableInteractionSource() }
  val pressState by interaction.collectIsHoveredAsState()
  val scale by animateFloatAsState(
    targetValue = when(pressState) {
      true -> 0.9f
      false -> 1f
    }
  )

  Box(
    modifier = modifier
      .graphicsLayer {
        scaleX = scale
        scaleY = scale
      }
      .clip(shape)
      .background(background, shape)
      .clickable(
        onClick = onClick,
        interactionSource = interaction,
        indication = LocalIndication.current,
      )
      .padding(24.dp)
      .size(120.dp)
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = null,
      modifier = Modifier
        .size(36.dp)
        .align(Alignment.TopStart),
      tint = tint
    )
    BasicText(
      text = name,
      style = TextStyle(
        fontFamily = alibabaPuHuiTi,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = tint
      ),
      modifier = Modifier
        .align(Alignment.BottomStart)
    )
  }
}

private val avatarSize = 160.dp
private val startHorizontalPadding = 140.dp
