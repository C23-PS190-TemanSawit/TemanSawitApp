package com.example.temansawit.ui.screen.camera.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.temansawit.R
import com.example.temansawit.databinding.ActivityDeteksiKematanganBinding
import com.example.temansawit.ui.screen.camera.reduceFileImage
import com.example.temansawit.ui.screen.camera.rotateFile
import com.example.temansawit.ui.screen.camera.viewmodel.ApiS
import com.example.temansawit.ui.screen.camera.viewmodel.ModelResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DeteksiKematanganActivity : ComponentActivity() {
    private lateinit var binding: ActivityDeteksiKematanganBinding
    private var myFile: File? = null
    private lateinit var loadingIndicator: ProgressBar

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
//        supportActionBar?.hide()
        binding = ActivityDeteksiKematanganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        loadingIndicator = findViewById(R.id.loadingIndicator)

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
            loadingIndicator.visibility = View.VISIBLE
            val file = reduceFileImage(myFile as File)
            val description = "Ini adalah deskripsi gambar".toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            val service = ApiS().getApiService2().uploadImage(imageMultiPart)
            service.enqueue(object : Callback<ModelResponse> {
                override fun onResponse(
                    call: Call<ModelResponse>,
                    response: Response<ModelResponse>
                ) {
                    loadingIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val ripeList = responseBody?.top2
                        Log.d("ahaye", ripeList.toString())
                        Log.d("ahayee", responseBody.toString())
                        ripeList?.let {
                            val intent = Intent(this@DeteksiKematanganActivity, HasilActivity::class.java)
                            intent.putExtra("picture", myFile)
                            intent.putExtra("ripe", ripeList.ripe)
                            intent.putExtra("underripe", ripeList.underripe)
                            intent.putExtra("unripe", ripeList.unripe)
                            intent.putExtra("overripe", ripeList.overripe)
                            intent.putExtra("rotten", ripeList.rotten)
                            intent.putExtra("empty_bunch", ripeList.empty_bunch)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@DeteksiKematanganActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                    loadingIndicator.visibility = View.GONE
                    Toast.makeText(this@DeteksiKematanganActivity, "Gagal mengirim permintaan", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@DeteksiKematanganActivity, "Silakan ambil gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }
}
