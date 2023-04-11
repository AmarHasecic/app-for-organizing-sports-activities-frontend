import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Shapes
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ba.unsa.etf.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Color(0xFF3C3131),
    primaryVariant = Color(0xFF868282),
    secondary = Color(0xFF000000),
    background = Color.White,
    surface = Color.DarkGray,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF2E1F1F),
    primaryVariant = Color(0xFF868282),
    secondary = Color(0xFF000000),
    background = Color.White,
    surface = Color.DarkGray,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black
)

private val Typography = androidx.compose.material.Typography(
    h1 = androidx.compose.ui.text.TextStyle(
        fontSize = 28.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 1.15.sp
    ),
    h2 = androidx.compose.ui.text.TextStyle(
        fontSize = 24.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.15.sp
    ),
    body1 = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    button = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 1.25.sp
    )
)

val Shapes = Shapes(
    small = RoundedCornerShape(percent = 6),
    medium = RoundedCornerShape(0f),
    large = CutCornerShape(
        topStart = 5.dp,
        topEnd = 0.dp,
        bottomStart = 5.dp,
        bottomEnd = 0.dp
    )
)

@Composable
fun SportEventsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {



    val colors = if (darkTheme) {
        DarkColorPalette

    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(color = Color(0x80BEBEBE))
        }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = {
            Box(
                modifier = Modifier.background(
                    color = Color.White,
                )
            ) {
                Box {

                    content()
                }
            }
        }

        )
}
