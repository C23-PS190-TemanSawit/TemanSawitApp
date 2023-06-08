package com.example.temansawit.ui.screen.camera

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.temansawit.databinding.ActivityCameraXactivityBinding
import java.io.File
import java.nio.file.Files.createFile
import java.text.SimpleDateFormat
import java.util.*

class CameraXActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityCameraXactivityBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraXactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun setupAction(){
//        binding.captureImage.setOnClickListener { takePhoto() }
        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

////    private fun takePhoto() {
//        val imageCapture = imageCapture ?: return
////        val photoFile = createFile(application)
////        showLoading(true)
//        Toast.makeText(this,"AMBIL PHOTO", Toast.LENGTH_SHORT).show()
////        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    showLoading(false)
//                    Toast.makeText(
//                        this@CameraActivity,
//                        R.string.failed_taking_pic,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//
//                    Toast.makeText(
//                        this@CameraXActivity,
//                       "BERHASIL",
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    val intent = Intent()
////                    intent.putExtra("picture", photoFile)
////                    intent.putExtra("isBackCamera", cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
////                    )
////                    setResult(AddStoryActivity.CAMERA_X_RESULT, intent)
//                    finish()
//                }
//            }
//        )
//    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraXActivity,
                  "GAGAL MEMBUKA KAMERA",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
}