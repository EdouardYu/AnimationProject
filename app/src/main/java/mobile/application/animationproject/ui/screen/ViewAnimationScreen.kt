package mobile.application.animationproject.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class ShapeType {
    CIRCLE, SQUARE
}

@Composable
fun ViewAnimationScreen() {
    var shapes by remember { mutableStateOf(listOf<ShapeType>()) }
    val coroutineScope = rememberCoroutineScope()
    val animatedOffsets = remember { mutableStateListOf<Pair<Animatable<Float, *>, Animatable<Float, *>>>() }
    val shapeSizes = remember { mutableStateListOf(100.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        shapes.forEachIndexed { index, shapeType ->

            var boxOffset by remember { mutableStateOf(Offset(0f, 0f)) }
            val animatedOffsetX = remember { Animatable(0f) }
            val animatedOffsetY = remember { Animatable(0f) }

            if (shapeSizes.size <= index) {
                shapeSizes.add(100.dp)
            }

            if (animatedOffsets.size <= index) {
                animatedOffsets.add(animatedOffsetX to animatedOffsetY)
            }

            Box(
                modifier = Modifier
                    .size(shapeSizes[index])
                    .offset {
                        IntOffset(
                            animatedOffsetX.value.roundToInt(),
                            animatedOffsetY.value.roundToInt()
                        )
                    }
                    .background(
                        color = if (shapeType == ShapeType.CIRCLE) Color.Blue else Color.Red,
                        shape = if (shapeType == ShapeType.CIRCLE) CircleShape else RoundedCornerShape(8.dp)
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                shapeSizes[index] = if (shapeSizes[index] < 200.dp) shapeSizes[index] + 20.dp else 100.dp
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            boxOffset = Offset(
                                boxOffset.x + dragAmount.x,
                                boxOffset.y + dragAmount.y
                            )
                            coroutineScope.launch {
                                animatedOffsetX.snapTo(boxOffset.x)
                                animatedOffsetY.snapTo(boxOffset.y)
                            }
                        }
                    }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        shapes.forEachIndexed { index, _ ->
                            launch {
                                animatedOffsets[index].first.animateTo(0f, animationSpec = tween(1000))
                                animatedOffsets[index].second.animateTo(0f, animationSpec = tween(1000))
                            }
                            shapeSizes[index] = 100.dp
                        }
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Recenter Objects")
            }
        }

        FloatingActionButton(
            onClick = {
                // circle or square added
                val nextShape = if (shapes.size % 2 == 0) ShapeType.SQUARE else ShapeType.CIRCLE
                shapes = shapes + nextShape
                shapeSizes.add(100.dp) // default size
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Element")
        }
    }
}