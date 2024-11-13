package mobile.application.animationproject.ui.screen
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun AnimationScreen(modifier: Modifier = Modifier) {
    var animate by remember { mutableStateOf(false) }
    var secondVisible by remember { mutableStateOf(true) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Translation animation (X only) for the first box
    val translationX by animateDpAsState(
        targetValue = if (animate) screenWidth - 150.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "ScreenWidthBasedTranslation"
    )

    // Rotation animation for the first box
    val rotation by animateFloatAsState(
        targetValue = if (animate) 360f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "RotationAnimation"
    )

    // Scale animation
    val scale by animateFloatAsState(
        targetValue = if (animate) 1.5f else 1f,
        animationSpec = tween(durationMillis = 1000),
        label = "ScaleAnimation"
    )

    // Opacity animation
    val alpha by animateFloatAsState(
        targetValue = if (animate) 0.5f else 1f,
        animationSpec = tween(durationMillis = 1000),
        label = "AlphaAnimation"
    )

    // Background color animation
    val color by animateColorAsState(
        targetValue = if (animate) Color.Red else Color.Blue,
        animationSpec = tween(durationMillis = 1000),
        label = "ColorAnimation"
    )

    // Shape transition
    val shape = if (animate) RoundedCornerShape(8.dp) else CircleShape

    // Infinite rotation for the second box
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val infiniteRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    // Custom trajectory for the second box
    val trajectoryX by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val trajectoryY = sin(trajectoryX / 50 * Math.PI).toFloat() * 20f

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // First box remains in its original position
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
                .background(color = color, shape = shape)
        )

        // Second box with infinite rotation and custom trajectory
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            AnimatedVisibility(
                visible = secondVisible,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .graphicsLayer(
                            rotationZ = infiniteRotation
                        )
                        .offset { IntOffset(trajectoryX.roundToInt(), trajectoryY.roundToInt()) }
                        .background(Color.Green, shape = RoundedCornerShape(8.dp))
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Button under the first box
            Spacer(modifier = Modifier.height(200.dp)) // Space to align below first box
            Button(onClick = { animate = !animate }) {
                Text("Animate")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Button under the second box, static position
            Button(onClick = { secondVisible = !secondVisible }) {
                Text(if (secondVisible) "Hide Second Box" else "Show Second Box")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Status text for the animation
            AnimatedContent(targetState = animate, label = "") { targetState ->
                Text(
                    text = if (targetState) "Animation Active" else "Animation Inactive",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
