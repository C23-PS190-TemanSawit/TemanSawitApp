package com.example.temansawit.ui.screen.profile

import android.widget.ScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPressed

@Composable
fun ProfileScreen( modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            AppBar(title = "Profile")
        }
    ) {it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFC0DBCE))
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                backgroundColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Image(
                        painter = painterResource(R.drawable.putri),
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Hai",
                            color = Color.Gray,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "John Doe",
                            color = Color.Gray,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "john.doe@example.com",
                            color = Color.DarkGray,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 7.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ProfileCard()
            Spacer(modifier = Modifier.height(10.dp))
            LogoutButton()
        }
    }
}

@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = GreenPressed,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@Composable
fun ProfileCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileItem(icon = R.drawable.ic_editing_profile, text = "Edit Profil")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
            ProfileItem(icon = R.drawable.ic_change_password_profile, text = "Ganti Password")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
            ProfileItem(icon = R.drawable.ic_privacy_policy, text = "Kebijakan Privasi")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
            ProfileItem(icon = R.drawable.ic_terms_conditions, text = "Syarat dan Ketentuan")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
            ProfileItem(icon = R.drawable.ic_contact, text = "Hubungi Kami")
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
            ProfileItem(icon = R.drawable.ic_about_us, text = "Tentang Kami")
        }

    }
}

@Composable
fun LogoutButton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_log_out),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Keluar",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ProfileItem(icon: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 20.dp)) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}
