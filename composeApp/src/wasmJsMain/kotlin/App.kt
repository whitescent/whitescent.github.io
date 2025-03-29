package root

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import extension.pxRound
import homepage.composeapp.generated.resources.Res
import homepage.composeapp.generated.resources.github
import homepage.composeapp.generated.resources.header_bg
import homepage.composeapp.generated.resources.soundcloud
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import sv.lib.squircleshape.SquircleShape
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
          val x = density.pxRound(startHorizontalPadding)
          placeable.placeRelative(x, -placeable.height / 2)
        }
      }
      .shadow(12.dp, CircleShape)
      .clip(CircleShape)
      .size(160.dp),
    contentScale = ContentScale.Crop
  )

  Gap(30.dp)

  Content(
    modifier = Modifier
      .offset { IntOffset(0, -density.pxRound(avatarSize / 2)) }
      .padding(horizontal = startHorizontalPadding)
      .fillMaxHeight()
      .padding(bottom = 24.dp)
  )
}

@Composable
private fun Content(
  modifier: Modifier = Modifier
) = Row(modifier) {
  Column {
    BasicText(
      text = "你好 👋，我叫 Nem，目前是一名 Android 开发者，也是业余电子音乐制作人，我喜欢听电子音乐和摇滚，我还在学习日语",
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
        modifier = Modifier.align(Alignment.Bottom)
      )
      Gap(46.dp)
      SocialCard(
        icon = Res.drawable.soundcloud,
        name = "SoundCloud",
        background = Color(0xFFFF5500),
        tint = Color.White,
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
  modifier: Modifier = Modifier,
  shape: Shape = SquircleShape(24.dp),
  tint: Color = Color.Unspecified,
) = Box(
  modifier = modifier
    .clip(shape)
    .background(background, shape)
    .padding(24.dp)
    .size(140.dp)
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

private val avatarSize = 160.dp
private val startHorizontalPadding = 140.dp
