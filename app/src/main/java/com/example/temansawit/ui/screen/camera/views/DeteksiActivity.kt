package com.example.temansawit.ui.screen.camera.views

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.temansawit.databinding.ActivityDeteksiBinding
import com.example.temansawit.ui.screen.camera.reduceFileImage
import com.example.temansawit.ui.screen.camera.rotateFile
import com.example.temansawit.ui.screen.camera.viewmodel.ApiS
import com.example.temansawit.ui.screen.camera.viewmodel.ModelResponse
import com.example.temansawit.ui.screen.camera.viewmodel.Top2
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DeteksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeteksiBinding
    private var myFile: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan izin.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDeteksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraButton.setOnClickListener { startCameraX() }
        binding.upload.setOnClickListener { uploadImage() }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraXActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == CAMERA_X_RESULT) {
            myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("picture") as? File
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("picture") as? File
            }

            val isBackCamera = result.data?.getBooleanExtra("isBackCamera", true) ?: true

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }


        }
    }

    private fun uploadImage() {
        if (myFile != null) {
            val file = reduceFileImage(myFile as File)
            val description = "Ini adalah deskripsi gambar".toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            val service = ApiS().getApiService().uploadImage(imageMultiPart)
            service.enqueue(object : Callback<ModelResponse> {
                override fun onResponse(
                    call: Call<ModelResponse>,
                    response: Response<ModelResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val ripeList = responseBody?.top2
                        ripeList?.let {
                            val intent = Intent(this@DeteksiActivity, HasilActivity::class.java)
                            intent.putExtra("picture", myFile)
                            intent.putExtra("ripe", ripeList.ripe)
                            intent.putExtra("underripe", ripeList.underripe)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@DeteksiActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                    Toast.makeText(this@DeteksiActivity, "Gagal mengirim permintaan", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@DeteksiActivity, "Silakan ambil gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }
}
