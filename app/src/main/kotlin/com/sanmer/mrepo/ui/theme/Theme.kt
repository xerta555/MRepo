package com.sanmer.mrepo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sanmer.mrepo.app.Config

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = Config.isDarkTheme()
    val themeColor = Config.THEME_COLOR

    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
    }

    val color = getColor(context = context, id = themeColor)

    val colorScheme = when {
        darkTheme -> color.darkColorScheme
        else -> color.lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}