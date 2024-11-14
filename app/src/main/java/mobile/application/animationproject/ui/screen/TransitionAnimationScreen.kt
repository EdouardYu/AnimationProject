package mobile.application.animationproject.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TransitionAnimationScreen() {
    var showCircles by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                CircleWithFixedWidth(
                    visible = showCircles,
                    animationEnter = fadeIn(animationSpec = tween(2000)),
                    animationExit = fadeOut(animationSpec = tween(2000)),
                    label = "Fade",
                    color = Color.Red
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircleWithFixedWidth(
                    visible = showCircles,
                    animationEnter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(1000)) + fadeIn(animationSpec = tween(2000)),
                    animationExit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(1000)) + fadeOut(animationSpec = tween(2000)),
                    label = "Slide",
                    color = Color.Blue
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircleWithFixedWidth(
                    visible = showCircles,
                    animationEnter = expandIn(expandFrom = Alignment.Center, animationSpec = tween(2000)) + fadeIn(animationSpec = tween(2000)),
                    animationExit = shrinkOut(shrinkTowards = Alignment.Center, animationSpec = tween(2000)) + fadeOut(animationSpec = tween(2000)),
                    label = "Explode",
                    color = Color.Green
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircleWithFixedWidth(
                    visible = showCircles,
                    animationEnter = scaleIn(initialScale = 0.2f, animationSpec = tween(2000)) + fadeIn(animationSpec = tween(2000)),
                    animationExit = scaleOut(targetScale = 0.2f, animationSpec = tween(2000)) + fadeOut(animationSpec = tween(2000)),
                    label = "Scale",
                    color = Color.Magenta
                )
            }
        }

        Button(
            onClick = { showCircles = !showCircles },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(if (showCircles) "Hide Circles" else "Show Circles")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CircleWithFixedWidth(
    visible: Boolean,
    animationEnter: EnterTransition,
    animationExit: ExitTransition,
    label: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = animationEnter,
            exit = animationExit
        ) {
            Circle(label = label, color = color)
        }
    }
}

@Composable
fun Circle(label: String, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .background(color, shape = CircleShape)
    ) {
        Text(label, color = Color.White)
    }
}
