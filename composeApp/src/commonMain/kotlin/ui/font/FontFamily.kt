package ui.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_55_Regular
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_65_Medium
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_75_SemiBold
import homepage.composeapp.generated.resources.AlibabaPuHuiTi_3_85_Bold
import homepage.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

val alibabaPuHuiTi @Composable get() = FontFamily(
  Font(Res.font.AlibabaPuHuiTi_3_55_Regular),
  Font(Res.font.AlibabaPuHuiTi_3_65_Medium),
  Font(Res.font.AlibabaPuHuiTi_3_75_SemiBold),
  Font(Res.font.AlibabaPuHuiTi_3_85_Bold),
)
