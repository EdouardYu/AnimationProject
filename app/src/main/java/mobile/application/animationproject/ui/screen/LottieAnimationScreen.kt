package mobile.application.animationproject.ui.screen

import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun LottieAnimationScreen(navController: NavController) {
    var showTransition by remember { mutableStateOf(false) }
    var swipeDirection by remember { mutableStateOf<SwipeDirection?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        showTransition = true
                    },
                    onDragEnd = {
                        showTransition = false
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        if (dragAmount < -50f) {
                            swipeDirection = SwipeDirection.LEFT
                            navController.navigate("scroll_lottie_animation")
                        } else if (dragAmount > 50f) {
                            showTransition = false
                        }
                    }
                )
            }
    ) {
        if (showTransition) {
            TransitionAnimationExample(direction = swipeDirection)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Icon Animation", modifier = Modifier.padding(8.dp))
            IconAnimationExample()

            Spacer(modifier = Modifier.height(32.dp))

            Text("Loading Animation", modifier = Modifier.padding(8.dp))
            LoadingAnimationExample()
        }
    }
}

@Composable
fun ScrollLottieAnimationScreen(navController: NavController) {
    var showTransition by remember { mutableStateOf(false) }
    var swipeDirection by remember { mutableStateOf<SwipeDirection?>(null) }

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("scroll_animation.json"))
    val scrollState = rememberScrollState()
    val progress = scrollState.value.toFloat() / (scrollState.maxValue.toFloat() + 1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        showTransition = true
                    },
                    onDragEnd = {
                        showTransition = false
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        if (dragAmount > 50f) {
                            swipeDirection = SwipeDirection.RIGHT
                            navController.navigate("lottie_animation")
                        } else if (dragAmount < -50f) {
                            showTransition = false
                        }
                    }
                )
            }
    ) {
        if (showTransition) {
            TransitionAnimationExample(direction = swipeDirection)
        }

        Box(
            modifier = Modifier
                .verticalScroll(scrollState) // Assurez-vous que le scroll vertical fonctionne
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(300.dp).graphicsLayer(alpha = 1f)
            )
        }
    }
}

@Composable
fun TransitionAnimationExample(direction: SwipeDirection?) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("transition_animation.json"))
    val progress by animateLottieCompositionAsState(composition)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = if (direction == SwipeDirection.RIGHT) -1f else 1f
            )
            .background(Color.Transparent),
        contentAlignment = Alignment.BottomCenter
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(250.dp)
        )
    }
}

@Composable
fun LoadingAnimationExample() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("loading_animation.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun IconAnimationExample() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("icon_animation.json"))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(100.dp)
    )
}

enum class SwipeDirection {
    LEFT, RIGHT
}
