package mobile.application.animationproject.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun PhysicsAnimationScreen() {
    var showShapes by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showShapes) {
                    RotatingShape(
                        modifier = Modifier.size(100.dp),
                        shapeColor = Color.Red,
                        shape = CircleShape
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (showShapes) {
                    FallingShapeWithBounce(
                        modifier = Modifier.size(120.dp),
                        initialColor = Color.Blue,
                        shape = RoundedCornerShape(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (showShapes) {
                    ExplodingShape(
                        modifier = Modifier.size(120.dp),
                        initialColor = Color.Green,
                        shape = CutCornerShape(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showShapes = !showShapes },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(if (showShapes) "Hide Shapes" else "Show Shapes")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun RotatingShape(
    modifier: Modifier,
    shapeColor: Color,
    shape: androidx.compose.ui.graphics.Shape
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = LinearEasing),
            RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = modifier
            .graphicsLayer(rotationZ = rotation)
            .background(shapeColor, shape = shape),
        contentAlignment = Alignment.Center
    ) {
        Text("Rotate", color = Color.White)
    }
}

@Composable
fun FallingShapeWithBounce(
    modifier: Modifier,
    initialColor: Color,
    shape: androidx.compose.ui.graphics.Shape
) {
    val springOffsetY = remember { Animatable(-500f) }

    LaunchedEffect(Unit) {
        springOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier = modifier
            .offset { IntOffset(0, springOffsetY.value.roundToInt()) }
            .background(initialColor, shape = shape),
        contentAlignment = Alignment.Center
    ) {
        Text("Falling", color = Color.White)
    }
}

@Composable
fun ExplodingShape(
    modifier: Modifier,
    initialColor: Color,
    shape: androidx.compose.ui.graphics.Shape
) {
    val explodeScale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        explodeScale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(durationMillis = 500)
        )
        explodeScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    Box(
        modifier = modifier
            .graphicsLayer(
                scaleX = explodeScale.value,
                scaleY = explodeScale.value
            )
            .background(initialColor, shape = shape),
        contentAlignment = Alignment.Center
    ) {
        Text("Explode", color = Color.White)
    }
}
