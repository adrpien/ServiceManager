package com.example.core_ui.components.signature

import android.graphics.Bitmap
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.example.core.util.Dimensions
import com.example.core.util.Helper.Companion.toDp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignatureArea(
    modifier: Modifier = Modifier,
    pathColor: Color = MaterialTheme.colorScheme.onSecondary,
    updateImageBitmap: (Bitmap) -> Unit,
) {

    var bitmap = Bitmap.createBitmap(Dimensions.signatureWidth, Dimensions.signatureHeight, Bitmap.Config.ARGB_8888)
    val backgroundPaint = Paint()

    backgroundPaint.apply {
        MaterialTheme.colorScheme.secondary.also { color = it }
        style = PaintingStyle.Fill
    }

    val canvas = Canvas(bitmap.asImageBitmap())

    canvas.drawRect(
        rect = Rect(0F,0F,bitmap.width.toFloat(), bitmap.height.toFloat()),
        paint = backgroundPaint)


    val lastTouchX = remember {
        mutableStateOf(0f)
    }
    val lastTouchY = remember {
        mutableStateOf(0f)
    }

    val path = remember {
        mutableStateOf<Path?>(Path())
    }

    Surface(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.onSecondary),
        color = Color.White
            ){
        Box(
            modifier = Modifier
                .width(Dimensions.signatureWidth.toDp.dp)
                .height(Dimensions.signatureHeight.toDp.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .pointerInteropFilter { motionEvent ->
                        when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> {
                                path.value?.moveTo(motionEvent.x, motionEvent.y)
                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y
                            }

                            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                                val historySize = motionEvent.historySize
                                for (i in 0 until historySize) {
                                    val historicalX = motionEvent.getHistoricalX(i)
                                    val historicalY = motionEvent.getHistoricalY(i)

                                    path.value?.lineTo(historicalX, historicalY)
                                }
                                path.value?.lineTo(motionEvent.x, motionEvent.y)
                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y
                            }
                        }
                        lastTouchX.value = motionEvent.x
                        lastTouchY.value = motionEvent.y

                        val tempPath = path.value
                        path.value = null
                        path.value = tempPath
                        true
                    },
                onDraw = {
                    path.value?.let { path ->
                        drawPath(
                            path = path,
                            color = pathColor,
                            style = Stroke(
                                width = 2.dp.toPx()
                            )
                        )
                        val paint = Paint()
                        paint.apply {
                            color = pathColor
                            style = PaintingStyle.Stroke
                            strokeWidth = 4f
                        }
                        canvas.drawPath(
                            path  = path,
                            paint = paint
                        )
                        updateImageBitmap(bitmap)
                    }
                },
            )
        }
    }
}
