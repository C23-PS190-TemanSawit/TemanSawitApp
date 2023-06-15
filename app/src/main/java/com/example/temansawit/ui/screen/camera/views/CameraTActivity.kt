package com.example.temansawit.ui.screen.camera.views


import ModelPredictor
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.temansawit.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import java.util.concurrent.Executors
import kotlin.random.Random


@ExperimentalGetImage class CameraTActivity : AppCompatActivity() {
    private lateinit var container: ConstraintLayout
    private lateinit var bitmapBuffer: Bitmap

    private val executor = Executors.newSingleThreadExecutor()
    private val permissions = listOf(Manifest.permission.CAMERA)
    private val permissionsRequestCode = Random.nextInt(0, 10000)

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private val isFrontFacing get() = lensFacing == CameraSelector.LENS_FACING_FRONT

    private var pauseAnalysis = false
    private var imageRotationDegrees: Int = 0
    private val tfImageBuffer = TensorImage(DataType.UINT8)
    private val view_finder: PreviewView
        get() = findViewById<PreviewView>(R.id.view_finder)
    private val image_predicted: ImageView
        get() = findViewById<ImageView>(R.id.image_predicted)
    private val box_prediction: View
        get() = findViewById<View>(R.id.box_prediction)


    private val tfImageProcessor by lazy {
        val cropSize = minOf(bitmapBuffer.width, bitmapBuffer.height)
        ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(cropSize, cropSize))
            .add(ResizeOp(
                tfInputSize.height, tfInputSize.width, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(Rot90Op(imageRotationDegrees / 90))
            .add(NormalizeOp(0f, 1f))
            .add(CastOp(DataType.UINT8))
            .build()
    }

    private val tflite by lazy {
        Interpreter(
            FileUtil.loadMappedFile(this, MODEL_PATH),
            Interpreter.Options().addDelegate(NnApiDelegate()))
    }

    private val detector by lazy {
        ModelPredictor(tflite)
    }

    private val tfInputSize by lazy {
        val inputIndex = 0
        val inputShape = tflite.getInputTensor(inputIndex).shape()
        println("inputShape: ${inputShape.contentToString()}")
        Size(inputShape[2], inputShape[1])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_camera_tactivity)
        container = findViewById(R.id.camera_container)

    }


    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindCameraUseCases() = view_finder.post {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {

            val cameraProvider = cameraProviderFuture.get()


            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(view_finder.display.rotation)
                .build()


            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(view_finder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            var frameCounter = 0
            var lastFpsTimestamp = System.currentTimeMillis()
            val converter = YuvToRgbConverter(this)

            imageAnalysis.setAnalyzer(executor, ImageAnalysis.Analyzer { image ->
                if (!::bitmapBuffer.isInitialized) {

                    imageRotationDegrees = image.imageInfo.rotationDegrees
                    bitmapBuffer = Bitmap.createBitmap(
                        image.width, image.height, Bitmap.Config.ARGB_8888)
                }


                if (pauseAnalysis) {
                    image.close()
                    return@Analyzer
                }


                image.use { converter.yuvToRgb(image.image!!, bitmapBuffer) }


                Log.d(TAG, "New predict")
                val predictions = detector.predict(bitmapBuffer)

                updateBox(predictions)


                val frameCount = 10
                if (++frameCounter % frameCount == 0) {
                    frameCounter = 0
                    val now = System.currentTimeMillis()
                    val delta = now - lastFpsTimestamp
                    val fps = 1000 * frameCount.toFloat() / delta
                    Log.d(TAG, "FPS: ${"%.02f".format(fps)}")
                    lastFpsTimestamp = now
                }
            })


            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()


            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner, cameraSelector, preview, imageAnalysis)


            preview.setSurfaceProvider(view_finder.getSurfaceProvider())

        }, ContextCompat.getMainExecutor(this))
    }

    private fun updateBox(
        predictions: FloatArray
    ) = view_finder.post {


        if (predictions == null) {
            box_prediction.visibility = View.GONE

            return@post
        }


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        predictions[0] = predictions[0] * height // Top
        predictions[1] = predictions[1] * width // Left
        predictions[2] = predictions[2] * height // Bottom
        predictions[3] = predictions[3] * width // Right


        box_prediction.y = predictions[0]
        box_prediction.x = predictions[1]
        val params: ViewGroup.LayoutParams = box_prediction.layoutParams
        params.width = predictions[3].toInt() - predictions[1].toInt()
        params.height = predictions[2].toInt() - predictions[0].toInt()
        box_prediction.layoutParams = params


        box_prediction.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()

        if (!hasPermissions(this)) {
            ActivityCompat.requestPermissions(
                this, permissions.toTypedArray(), permissionsRequestCode)
        } else {
            bindCameraUseCases()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionsRequestCode && hasPermissions(this)) {
            bindCameraUseCases()
        } else {
            finish()
        }
    }


    private fun hasPermissions(context: Context) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val TAG = CameraTActivity::class.java.simpleName

        private const val ACCURACY_THRESHOLD = 0.5f
        private const val MODEL_PATH = "detect.tflite"
        private const val LABELS_PATH = "labelmap.txt"
    }
}
