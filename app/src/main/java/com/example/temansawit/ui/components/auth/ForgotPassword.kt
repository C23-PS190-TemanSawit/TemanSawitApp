package com.example.temansawit.ui.components.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPrimary

@Composable
fun WelcomeForgot(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(GreenPrimary.copy(alpha = 0.2F))
    ) {
        Image(
            painter = painterResource(id = R.drawable.pattern),
            contentDescription = "pattern temansawit",
            contentScale = ContentScale.Crop,
            modifier = modifier.height(298.dp).fillMaxWidth()
        )
        LogoTemanSawit()
        AuthText(
            modifier = modifier.padding(top = 100.dp),
            loginText = "Lupa Password,",
            loginBodyText = "Silahkan masukkan data untuk reset password."
        )
    }
}

@Composable
fun ForgotPasswordInput(
    modifier: Modifier = Modifier,
    username: String,
    newPassword: String,
    confPassword: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfPasswordChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    val showKonfirmasiPassword = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = (-50).dp)
            .background(Color.White)
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .padding(horizontal = 16.dp),
            value = username,
            label = { Text(text = "Username") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "username") },
            onValueChange = onUsernameChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = newPassword,
            label = { Text(text = "Password Baru") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_key_24), contentDescription = "y") },
            onValueChange = onPasswordChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            trailingIcon = {
                val (icon, iconColor) = if (showPassword.value) {
                    Pair(
                        painterResource(id = R.drawable.baseline_visibility_24),
                        colorResource(id = R.color.black)
                    )
                } else {
                    Pair(painterResource(id = R.drawable.baseline_visibility_off_24), colorResource(id = R.color.black))
                }

                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        icon,
                        contentDescription = "Visibility",
                        tint = iconColor
                    )
                }
            },
            visualTransformation = if (showPassword.value)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = confPassword,
            label = { Text(text = "Konfirmasi Password Baru") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_key_24), contentDescription = "y") },
            onValueChange = onConfPasswordChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            trailingIcon = {
                val (icon, iconColor) = if (showKonfirmasiPassword.value) {
                    Pair(
                        painterResource(id = R.drawable.baseline_visibility_24),
                        colorResource(id = R.color.black)
                    )
                } else {
                    Pair(painterResource(id = R.drawable.baseline_visibility_off_24), colorResource(id = R.color.black))
                }

                IconButton(onClick = { showKonfirmasiPassword.value = !showKonfirmasiPassword.value }) {
                    Icon(
                        icon,
                        contentDescription = "Visibility",
                        tint = iconColor
                    )
                }
            },
            visualTransformation = if (showPassword.value)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
    }
}

@Composable
fun BtnForgotPassword(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .height(40.dp),
        shape = RoundedCornerShape(50),
        onClick = onClick
    ) {
        Text(text = "Ganti Password")
    }
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ClickableText(
            text = AnnotatedString(text = "Kembali ke halaman Login", spanStyle = SpanStyle(fontWeight = FontWeight.Bold)),
            onClick = { navHostController.navigate("login") }
        )
    }
}