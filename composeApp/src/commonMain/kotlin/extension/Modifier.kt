package extension

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.debugBorder(width: Dp = 1.dp) =
  this then Modifier.border(width, listOf(Color.Red, Color.Green, Color.Blue).random())

fun Modifier.clickableWithoutRipple(
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  onClick: () -> Unit
) = this then clickable(
  enabled = enabled,
  interactionSource = MutableInteractionSource(),
  indication = null,
  onClick = onClick,
  role = role,
  onClickLabel = onClickLabel
)
