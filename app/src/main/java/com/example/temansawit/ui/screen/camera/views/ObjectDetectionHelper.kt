import android.graphics.Bitmap
import android.graphics.RectF
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import java.nio.ByteBuffer

class ObjectDetectionHelper(private val tflite: Interpreter) {
    data class ObjectPrediction(val location: RectF)

    private val locationsBuffer: ByteBuffer = ByteBuffer.allocateDirect(320 * 320 * 3 * 4) // Assuming 320x320 image size and 3 channels

    private val outputBuffer = mapOf(
        0 to locationsBuffer,
        1 to ByteBuffer.allocateDirect(4) // Assuming output size of 4 bytes
    )

    val predictions get() = (0 until OBJECT_COUNT).map {
        ObjectPrediction(
            location = RectF(
                locationsBuffer.getFloat(it * 16),
                locationsBuffer.getFloat(it * 16 + 4),
                locationsBuffer.getFloat(it * 16 + 8),
                locationsBuffer.getFloat(it * 16 + 12)
            )
        )
    }

    fun predict(image: TensorImage): List<ObjectPrediction> {
        val resizedImage = resizeImageTo320x320(image.bitmap)
        val tensorImage = TensorImage.fromBitmap(resizedImage)
        tflite.runForMultipleInputsOutputs(arrayOf(tensorImage.buffer), outputBuffer)
        return predictions
    }

    private fun resizeImageTo320x320(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 320, 320, true)
    }

    companion object {
        const val OBJECT_COUNT = 1
    }
}
