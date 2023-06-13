package com.example.temansawit.ui.screen.profile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.temansawit.R
import com.example.temansawit.data.Result
import com.example.temansawit.network.ApiConfig
import com.example.temansawit.network.response.RegisterResponse
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.camera.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


@Composable
fun EditProfile(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = LocalContext.current as? AppCompatActivity
    val income1 = remember { mutableStateOf("") }
    val income2 = remember { mutableStateOf("") }
    val income3 = remember { mutableStateOf("") }
    val income4 = remember { mutableStateOf("") }
    val income1Input = income1.value
    val income2Input = income2.value
    val income3Input = income3.value
    val income4Input = income4.value
    val genderOptions = listOf("Laki-laki", "Wanita")
    val selectedGender = remember { mutableStateOf("") }
    val selectedGenderInput = selectedGender.value
//    var getFile: File?= null
    var imageUser by remember { mutableStateOf("https://www.citypng.com/public/uploads/preview/free-round-flat-male-portrait-avatar-user-icon-png-11639648873oplfof4loj.png") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (activity != null && uri != null) {
            selectedImageUri.value = uri
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Profile") },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick =  navigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_navigate_before_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {it
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        painter = rememberImagePainter(
                            data = selectedImageUri.value?.toString() ?: imageUser,
                            builder = {
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,

                    )

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Icon",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    ClickableText(
                        text = AnnotatedString("Ganti Foto Profil"),
                        onClick = { galleryLauncher.launch("image/*") }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        genderOptions.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedGenderInput == option,
                                    onClick = { selectedGender.value = option },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colors.primary
                                    )
                                )
                                Text(
                                    text = option,
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = income1Input,
                    onValueChange = { income1.value = it },
                    label = { Text(text = "Nama Lengkap") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = income2Input,
                    onValueChange = { income2.value = it },
                    label = { Text(text = "No Handphone") }
                )
//                OutlinedTextField(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    value = income3Input,
//                    onValueChange = { income3.value = it },
//                    label = { Text(text = "Email") }
//                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = income4Input,
                    onValueChange = { income4.value = it },
                    label = { Text(text = "Tanggal Lahir (YYYY-MM-DD") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_calendar),
                            contentDescription = "Calendar",
                            tint = Color.Gray
                        )
                    }
                )
                Spacer(modifier = modifier.padding(20.dp))
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(100.dp)),
                    onClick = {
                        viewModel.updateProfile(income2Input, income3Input, income4Input, income1Input).observe(lifecycleOwner, {
                            when (it) {
                                is Result.Loading -> {
                                    // Handle loading state if needed
                                }
                                is Result.Success -> {
                                    changePhoto(context)
                                    Toast.makeText(context, it.data.message, Toast.LENGTH_LONG).show()
                                }
                                is Result.Error -> {
                                    Toast.makeText(context, "Terjadi kesalahan saat update profile", Toast.LENGTH_LONG).show()
                                }
                                else -> {}
                            }
                        })

                    }
                ) {
                    Text(text = "SIMPAN PERUBAHAN")
                }
            }
        }
    )
}

private fun changePhoto(context: Context) {
    val myFile: File? = null
    val file = reduceFileImage(myFile as File)
    val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
    val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "file",
        file.name,
        requestImageFile
    )

    val response = ApiConfig.getApiService(context).changePhoto(imageMultiPart)
    response.enqueue(object : Callback<RegisterResponse> {
        override fun onResponse(
            call: Call<RegisterResponse>,
            response: Response<RegisterResponse>
        ) {
            if (response.isSuccessful){
                val responseBody = response.body()?.message
                Result.Success(responseBody)
            }
        }

        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            Result.Error<RegisterResponse>(t.message.toString())
        }
    })
}