package com.example.temansawit.components.navigation

import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.temansawit.model.ListBottomMenu

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current.applicationContext
    val listBottomMenu = ListBottomMenu()
    var selectedItem by remember {
        mutableStateOf("Beranda")
    }

    BottomAppBar(
        modifier = modifier,
        backgroundColor = Color.White,
//        contentColor = Color.Red,
        cutoutShape = CircleShape
    ) {
        listBottomMenu.forEachIndexed { index, list->
            if (index == 2) {
                BottomNavigationItem(
                    selected = false,
                    onClick = { },
                    icon = {},
                    enabled = false
                )
            }

            BottomNavigationItem(
                selected = selectedItem == list.title,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                icon = {
                    Icon(painter = list.icon, contentDescription = list.title)
                },
                label = { Text(text = list.title) },
                enabled = true,
                onClick = {
                    selectedItem = list.title
                    Toast.makeText(
                        context,
                        list.title, Toast.LENGTH_SHORT
                    ).show()
                })
        }
    }
}