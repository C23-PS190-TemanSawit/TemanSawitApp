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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPrimary

@Composable
fun WelcomeRegister() {
    Box {
        Box(
            Modifier
                .background(GreenPrimary.copy(alpha = 0.2F))
        ) {
            Image(
                painter = painterResource(id = R.drawable.pattern),
                contentDescription = "pattern temansawit",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(200.dp).fillMaxWidth()
            )
            LogoTemanSawit()
            AuthText(
                modifier = Modifier.padding(top = 100.dp),
                loginText = "Selamat Datang ,",
                loginBodyText = "Silakan lengkapi formulir pendaftaran untuk membuat akun."
            )
        }
    }
}

@Composable
fun RegisterInput(
    username: String,
    email: String,
    password: String,
    konfirmasiPassword: String,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onKonfirmasiPasswordChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    val showKonfirmasiPassword = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .padding(bottom = 8.dp)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = email,
            label = { Text(text = "Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email") },
            onValueChange = onEmailChange,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = password,
            label = { Text(text = "Password") },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = konfirmasiPassword,
            label = { Text(text = "Konfirmasi Password") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_key_24), contentDescription = "y") },
            onValueChange = onKonfirmasiPasswordChange,
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
            visualTransformation = if (showKonfirmasiPassword.value)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
    }
}

@Composable
fun BtnRegister(
    navHostController: NavHostController,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 16.dp)
            .height(40.dp),
        shape = RoundedCornerShape(50),
        onClick = onClick
    ) {
        Text(text = "Daftar")
    }
    Text(text = "Atau", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(40.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = { /*TODO*/ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = "daftar dengan google",
                tint = Color.Unspecified

            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Text(text = "Daftar dengan akun Google")
        }
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Sudah punya akun?")
        ClickableText(
            text = AnnotatedString(text = " Masuk disini"),
            onClick = {navHostController.navigate("login")}
        )
    }
}