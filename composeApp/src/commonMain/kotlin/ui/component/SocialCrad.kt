package ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.font.alibabaPuHuiTi

@Composable
fun SocialCard(
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
    targetValue = when (pressState) {
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
