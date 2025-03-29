package extension

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.debugBorder(width: Dp = 1.dp) =
  this then Modifier.border(width, listOf(Color.Red, Color.Green, Color.Blue).random())
