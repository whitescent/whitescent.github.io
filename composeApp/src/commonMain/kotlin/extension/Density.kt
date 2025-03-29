package extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit


/**
 * Converts the given pixel value to a [Dp] (device-independent pixel) value using
 * the current [Density].
 *
 * @param px The pixel value to be converted to [Dp].
 */
fun Density.dp(px: Int): Dp = px.toDp()

/**
 * Converts the given pixel value to a [Dp] (device-independent pixel) value using
 * the current [Density].
 *
 * @param px The pixel value to be converted to [Dp].
 */
fun Density.dp(px: Float): Dp = px.toDp()

/**
 * Converts the given [TextUnit] value to a [Dp] (device-independent pixel) value using
 * the current [Density].
 *
 * @param px The [TextUnit] value to be converted to [Dp].
 */
fun Density.dp(px: TextUnit): Dp = px.toDp()

/**
 * Converts the given [Dp] (device-independent pixel) value to a pixel value using
 * the current [Density].
 *
 * @param dp The [Dp] value to be converted to pixels.
 */
fun Density.px(dp: Dp): Float = dp.toPx()

/**
 * Converts the given [TextUnit] value to a pixel value using the current [Density].
 *
 * @param dp The [TextUnit] value to be converted to pixels.
 */
fun Density.px(dp: TextUnit): Float = dp.toPx()

/**
 * Rounds the given [Dp] (device-independent pixel) value to the nearest integer pixel
 * value using the current [Density].
 *
 * @param dp The [Dp] value to be rounded to the nearest integer pixel value.
 */
fun Density.pxRound(dp: Dp): Int = dp.roundToPx()

/**
 * Converts the given [Offset] to a [DpOffset] using the current [Density].
 *
 * @param offset The [Offset] to be converted to a [DpOffset].
 */
fun Density.dpOffset(offset: Offset): DpOffset =
  DpOffset(offset.x.toDp(), offset.y.toDp())

/**
 * Converts the given [DpOffset] to an [Offset] using the current [Density].
 *
 * @param dpOffset The [DpOffset] to be converted to an [Offset].
 */
fun Density.offset(dpOffset: DpOffset): Offset =
  Offset(dpOffset.x.toPx(), dpOffset.y.toPx())

fun Size(all: Float) = Size(all, all)

fun Size(
  all: Dp,
  density: Density,
) = Size(density.px(all))

fun Size(
  width: Dp,
  height: Dp,
  density: Density,
) = Size(density.px(width), density.px(height))
