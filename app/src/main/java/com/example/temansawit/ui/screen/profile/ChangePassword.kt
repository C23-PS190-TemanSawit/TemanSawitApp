package com.example.temansawit.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.temansawit.R

@Composable
fun ChangePassword(modifier: Modifier = Modifier) {
    val currentPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val currentPasswordVisibility = remember { mutableStateOf(false) }
    val newPasswordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    Scaffold(

        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Ganti Password") },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_navigate_before_24),
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
        content = { it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Masukkan Password saat ini untuk otentikasi")
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = currentPassword.value,
                    label = { Text(text = "Password Saat ini") },
                    onValueChange = { currentPassword.value = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            currentPasswordVisibility.value = !currentPasswordVisibility.value
                        }) {
                            Icon(
                                imageVector = if (currentPasswordVisibility.value)
                                    Icons.Filled.Visibility

                                else
                                    Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle Visibility"
                            )
                        }
                    },
                    visualTransformation = if (currentPasswordVisibility.value)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Masukkan kata sandi baru yang terdiri dari 8 karakter yang mengandung angka dan huruf.")
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = newPassword.value,
                    label = { Text(text = "Password Baru") },
                    onValueChange = { newPassword.value = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            newPasswordVisibility.value = !newPasswordVisibility.value
                        }) {
                            Icon(
                                imageVector = if (currentPasswordVisibility.value)
                                    Icons.Filled.Visibility

                                else
                                    Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle Visibility"
                            )
                        }
                    },
                    visualTransformation = if (newPasswordVisibility.value)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = confirmPassword.value,
                    label = { Text(text = "Konfirmasi Password Baru") },
                    onValueChange = { confirmPassword.value = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                        }) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility.value)
                                    Icons.Filled.Visibility

                                else
                                    Icons.Filled.VisibilityOff ,
                                contentDescription = "Toggle Visibility"
                            )

                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility.value)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
                )
                Spacer(modifier = modifier.padding(20.dp))
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(100.dp)),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "KONFIRMASI")
                }
            }
        }
    )
}