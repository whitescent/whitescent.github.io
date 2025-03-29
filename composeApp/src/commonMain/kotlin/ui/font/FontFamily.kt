package ui.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_55_Regular
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_65_Medium
import homepage.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

enum class FontItem(val filename: String) {
  ALIBABA_PU_HUI_TI_3_55_REGULAR("AlibabaPuHuiTi-3_55-Regular"),
  ALIBABA_PU_HUI_TI_3_65_MEDIUM("AlibabaPuHuiTi-3-65-Medium"),
}

val alibabaPuHuiTi @Composable get() = FontFamily(
  Font(Res.font.AlibabaPuHuiTi_3_55_Regular),
  Font(Res.font.AlibabaPuHuiTi_3_65_Medium),
)
