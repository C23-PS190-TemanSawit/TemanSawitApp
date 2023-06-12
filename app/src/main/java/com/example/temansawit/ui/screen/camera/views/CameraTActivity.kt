package com.example.temansawit.ui.screen.camera.views

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.BuildCompat
import com.example.temansawit.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.random.Random

class CameraTActivity : AppCompatActivity() {
    private lateinit var container: ConstraintLayout
    private lateinit var bitmapBuffer: Bitmap

    private lateinit var executor: ExecutorService
    private val permissions = listOf(android.Manifest.permission.CAMERA)
    private val permissionsRequestCode = Random.nextInt(0, 10000)

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private val isFrontFacing get() = lensFacing == CameraSelector.LENS_FACING_FRONT

    private var pauseAnalysis = false
    private var imageRotationDegrees: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_tactivity)
        container = findViewById(R.id.camera_container)
        executor = Executors.newSingleThreadExecutor()

        // Initialize other components and setup camera here
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionsRequestCode && hasPermissions(this)) {
//            bindCameraUseCases()
        } else {
            finish() // If we don't have the required permissions, we can't run
        }
    }
    // Add other methods and functions here
    private fun hasPermissions(context: Context) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private val TAG = CameraTActivity::class.java.simpleName

        private const val ACCURACY_THRESHOLD = 0.5f
        private const val MODEL_PATH = "coco_ssd_mobilenet_v1_1.0_quant.tflite"
        private const val LABELS_PATH = "coco_ssd_mobilenet_v1_1.0_labels.txt"
    }
}
