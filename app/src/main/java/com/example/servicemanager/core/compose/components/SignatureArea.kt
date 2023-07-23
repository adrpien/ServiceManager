
import android.graphics.Bitmap
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
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
import com.example.servicemanager.core.util.Helper.Companion.toDp
import com.example.servicemanager.ui.theme.TiemedMediumBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignatureArea(
    modifier: Modifier = Modifier,
    updateImageBitmap: (Bitmap) -> Unit,
) {

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
        modifier = Modifier.border(2.dp, TiemedMediumBlue),
        color = Color.White
            ){
        Box(
            modifier = Modifier
                .width(700.toDp.dp)
                .height(350.toDp.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TiemedVeryLightBeige)
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
                            color = TiemedMediumBlue,
                            style = Stroke(
                                width = 4.dp.toPx()
                            )
                        )
                        val bitmap = Bitmap.createBitmap(700, 350, Bitmap.Config.ARGB_8888)
                        val paint = Paint()
                        paint.apply {

                            color = TiemedMediumBlue
                            style = PaintingStyle.Stroke
                            strokeWidth = 8f
                        }
                        val backgroundPaint = Paint()
                        backgroundPaint.apply {
                            color = TiemedVeryLightBeige
                            style = PaintingStyle.Fill
                        }
                        val canvas = Canvas(bitmap.asImageBitmap())
                        canvas.drawRect(Rect(0F,0F,bitmap.width.toFloat(), bitmap.height.toFloat()), backgroundPaint)
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
