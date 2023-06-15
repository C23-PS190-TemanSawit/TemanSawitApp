

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ModelPredictor(private val interpreter: Interpreter) {

    companion object {
        private const val INPUT_IMAGE_SIZE = 320
    }

    private val inputShape: IntArray = intArrayOf(1, INPUT_IMAGE_SIZE, INPUT_IMAGE_SIZE, 3)
    private val outputNames: Array<String> = arrayOf("StatefulPartitionedCall:0",
        "StatefulPartitionedCall:1", "StatefulPartitionedCall:2", "StatefulPartitionedCall:3")

    init {
        // Get input shape and output names from the TFLite model signature
    }

    fun predict(image: Bitmap): FloatArray {
//        val numOfDetection: FloatArray
//        val scoreDetection:

        // Resize the input image
        val resizedImage = resizeImage(image)
        // Preprocess the resized image into a ByteBuffer
        val inputBuffer = preprocessImage(resizedImage)

        // Prepare outputBuffer
        val outputMap = mutableMapOf<Int, Any>()

        // Prepare outputBuffer for every StatefulPartitionCall
        // Prepare for StatefulPartitionedCall:0 = Number of object
        val outputBufferCall0 = TensorBuffer.createFixedSize(intArrayOf(1), DataType.FLOAT32)
        outputMap[interpreter.getOutputIndex("StatefulPartitionedCall:0")] = outputBufferCall0.buffer

        // Prepare for StatefulPartitionedCall:1 = Score of every object
        val outputBufferCall1 = TensorBuffer.createFixedSize(intArrayOf(1, 10), DataType.FLOAT32)
        outputMap[interpreter.getOutputIndex("StatefulPartitionedCall:1")] = outputBufferCall1.buffer

        // Prepare for StatefulPartitionedCall:2 = Class of every object
        val outputBufferCall2 = TensorBuffer.createFixedSize(intArrayOf(1, 10), DataType.FLOAT32)
        outputMap[interpreter.getOutputIndex("StatefulPartitionedCall:2")] = outputBufferCall2.buffer

        // Prepare for StatefulPartitionedCall:3 = Coordinate
        val outputBufferCall3 = TensorBuffer.createFixedSize(intArrayOf(1, 10, 4), DataType.FLOAT32)
        outputMap[interpreter.getOutputIndex("StatefulPartitionedCall:3")] = outputBufferCall3.buffer

        // Run the inference
        interpreter.runForMultipleInputsOutputs(arrayOf(inputBuffer),
            outputMap as Map<Int, Any>
        )

        // Get Score for every detected box
        val boxScore  = outputBufferCall1.floatArray

        // Highest score index
//        val maxIdx = boxScore.indices.maxOrNull() ?: -1

        // Get coordinate for every detected box

        var maxScore = Float.MIN_VALUE
        var maxIdx = -1

        for (i in boxScore.indices) {
            if (boxScore[i] > maxScore) {
                maxScore = boxScore[i]
                maxIdx = i
            }
        }

        if (maxIdx != -1) {
            val boxCoordinate = outputBufferCall3.floatArray
        // Get final coordinate
        val top     = boxCoordinate[maxIdx * 4]         // Let's say box idx 0 => 0 | box idx 9 => 36
        val left    = boxCoordinate[maxIdx * 4 + 1]     //                        1 | box idx 9 => 37
        val bottom  = boxCoordinate[maxIdx * 4 + 2]     //                        2 | box idx 9 => 38
        val right   = boxCoordinate[maxIdx * 4 + 3]     //                     => 3 | box idx 9 => 39
        val finalCoordinate = arrayOf<Float>(top, left, bottom, right).toFloatArray()

        finalCoordinate.forEach {
            Log.d("model coordinate", it.toString() )
        }

        // RAW Output from model
        return finalCoordinate
        } else {
            // Handle error when maxIdx is not found
            return FloatArray(0)
        }
        // Normalized with input image output
//        return normalizePrediction(finalCoordinate, image.width, image.height)
    }

    private fun resizeImage(image: Bitmap): Bitmap {
        // Calculate the scale factors for resizing the image
        val scaleWidth = INPUT_IMAGE_SIZE.toFloat() / image.width
        val scaleHeight = INPUT_IMAGE_SIZE.toFloat() / image.height
        // Create a matrix with the scale factors
        val matrix = Matrix().apply { setScale(scaleWidth, scaleHeight) }
        // Resize the image using the matrix
        return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
    }

    private fun preprocessImage(image: Bitmap): ByteBuffer {
        // Allocate a ByteBuffer for the input image
        val inputBuffer = ByteBuffer.allocateDirect(4 * inputShape.product())
        inputBuffer.order(ByteOrder.nativeOrder())
        // Resize the image to the desired input shape
        val resizedBitmap =
            Bitmap.createScaledBitmap(image, INPUT_IMAGE_SIZE, INPUT_IMAGE_SIZE, true)
        // Get the pixel values of the resized image
        val intValues = IntArray(INPUT_IMAGE_SIZE * INPUT_IMAGE_SIZE)
        resizedBitmap.getPixels(
            intValues,
            0,
            INPUT_IMAGE_SIZE,
            0,
            0,
            INPUT_IMAGE_SIZE,
            INPUT_IMAGE_SIZE
        )
        // Preprocess the pixel values and store them in the input buffer
        for (pixelValue in intValues) {
            val normalizedPixelValue = (pixelValue and 0xFF) / 255.0f
            inputBuffer.putFloat(normalizedPixelValue)
        }
        return inputBuffer
    }

    private fun normalizePrediction(prediction: FloatArray, imageWidth: Int, imageHeight: Int): FloatArray {
        Log.d("img w", imageWidth.toString())
        Log.d("img h", imageHeight.toString())

        val normalizedPrediction = FloatArray(prediction.size)
        // Denormalize the prediction values
        normalizedPrediction[0] = prediction[0] * imageHeight // Top
        normalizedPrediction[1] = prediction[1] * imageWidth // Left
        normalizedPrediction[2] = prediction[2] * imageHeight // Bottom
        normalizedPrediction[3] = prediction[3] * imageWidth // Right
        return normalizedPrediction
    }

    private fun IntArray.product(): Int = reduce { acc, i -> acc * i }

}
