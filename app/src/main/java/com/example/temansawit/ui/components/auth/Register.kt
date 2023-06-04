package com.example.temansawit.ui.components.auth

import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.temansawit.R
import com.example.temansawit.di.Preferences
import com.example.temansawit.ui.theme.GreenPrimary

@Composable
fun WelcomeRegister(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(GreenPrimary.copy(alpha = 0.2F))
    ) {
        Image(
            painter = painterResource(id = R.drawable.pattern),
            contentDescription = "pattern temansawit",
            contentScale = ContentScale.Crop,
            modifier = modifier.height(298.dp)
        )
        LogoTemanSawit()
        AuthText(
            modifier = modifier.padding(top = 100.dp),
            loginText = "Selamat Datang ,",
            loginBodyText = "Silakan lengkapi formulir pendaftaran untuk membuat akun."
        )
    }
}

@Composable
fun RegisterInput(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    val showKonfirmasiPassword = remember { mutableStateOf(false) }
    var username by remember{ mutableStateOf(TextFieldValue("")) }
    var email by remember{ mutableStateOf(TextFieldValue("")) }
    var password by remember{ mutableStateOf(TextFieldValue("")) }
    var konfirmasiPassword by remember{ mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = (-100).dp)
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
            onValueChange = { username = it },
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
                .padding(horizontal = 16.dp),
            value = email,
            label = { Text(text = "Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email") },
            onValueChange = { email = it },
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
            value = password,
            label = { Text(text = "Password") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_key_24), contentDescription = "y") },
            onValueChange = { password = it },
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
            value = konfirmasiPassword,
            label = { Text(text = "Konfirmasi Password") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_key_24), contentDescription = "y") },
            onValueChange = { konfirmasiPassword = it },
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
        ClickableText(
            text = AnnotatedString("Lupa password"),
            modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            style = TextStyle(textAlign = TextAlign.End),
            onClick = {
                Log.d("ClickableText", "$it-th character is clicked.")
            }
        )
        BtnRegister(navHostController = navHostController)
    }
}

@Composable
fun BtnRegister(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val context = LocalContext.current

    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(40.dp),
        shape = RoundedCornerShape(50),
        onClick = { val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            Preferences.setOnboarded(sharedPreferences, true)
            navHostController.popBackStack()
            navHostController.navigate("home") }
    ) {
        Text(text = "Daftar")
    }
    Text(text = "Atau", modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Button(
        modifier = modifier
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
            Spacer(modifier = modifier.padding(horizontal = 8.dp))
            Text(text = "Daftar dengan akun Google")
        }
    }
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Sudah punya akun?")
        ClickableText(
            text = AnnotatedString(text = " Masuk disini"),
            onClick = {}
        )
    }
}