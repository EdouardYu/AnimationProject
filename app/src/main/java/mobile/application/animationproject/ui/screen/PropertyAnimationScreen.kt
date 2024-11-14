package mobile.application.animationproject.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset

@Composable
fun PropertyAnimationScreen(modifier: Modifier = Modifier) {
    var animate by remember { mutableStateOf(false) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val translationX by animateDpAsState(
        targetValue = if (animate) screenWidth - 150.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "TranslationX"
    )

    val rotation by animateFloatAsState(
        targetValue = if (animate) 360f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "RotationAnimation"
    )

    val scale by animateFloatAsState(
        targetValue = if (animate) 1.5f else 1f,
        animationSpec = tween(durationMillis = 1000),
        label = "ScaleAnimation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (animate) 0.5f else 1f,
        animationSpec = tween(durationMillis = 1000),
        label = "AlphaAnimation"
    )

    val color by animateColorAsState(
        targetValue = if (animate) Color.Red else Color.Blue,
        animationSpec = tween(durationMillis = 1000),
        label = "ColorAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(translationX.roundToPx(), 50.dp.roundToPx()) }
                .graphicsLayer(
                    rotationZ = rotation,
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                )
                .background(color = color, shape = RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = { animate = !animate },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Animate")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
