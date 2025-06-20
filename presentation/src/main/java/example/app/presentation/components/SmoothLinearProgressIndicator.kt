package example.app.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SmoothLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    height: Dp = 8.dp,
    cornerRadius: Dp = 4.dp
)  {
    Box(modifier = modifier
        .height(height)
        .clip(RoundedCornerShape(cornerRadius))) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = size.width
            val barHeight = size.height

            drawRoundRect(
                color = backgroundColor,
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barHeight / 2, barHeight / 2)
            )

            drawRoundRect(
                color = color,
                size = Size(barWidth * progress.coerceIn(0f, 1f), barHeight),
                cornerRadius = CornerRadius(barHeight / 2, barHeight / 2)
            )
        }
    }
}