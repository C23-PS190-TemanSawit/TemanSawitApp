package com.example.temansawit.ui.screen.faq

import BottomSheet
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.temansawit.R
import com.example.temansawit.ScaffoldApp
import com.example.temansawit.model.Faqs.FAQS
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FaqScreen(
    navHostController: NavHostController,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val openIndexs = remember { mutableStateOf(-1) }
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedBottomSheet by remember { mutableStateOf(BottomSheetType.None) }

    BottomSheet(
        modalSheetState = modalSheetState,
        selectedBottomSheet = selectedBottomSheet,
        onBottomSheetSelected = { sheetType ->
            selectedBottomSheet = sheetType
            coroutineScope.launch {
                modalSheetState.show()
            }
        }
    ) {
        ScaffoldApp(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "FAQ",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            },
            bottomBar = { BottomBar(navHostController = navHostController) },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                if (currentRoute != Screen.DetailTransaction.route) {
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            selectedBottomSheet = BottomSheetType.Camera
                            coroutineScope.launch {
                                modalSheetState.show()
                            }
                        },
                        backgroundColor = Green700,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_camera_alt_24),
                            contentDescription = "Deteksi Sawit"
                        )
                    }
                }
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(FAQS) { index, item ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Box(modifier = Modifier.weight(1f)) {
                                    ClickableText(
                                        text = AnnotatedString(item.question),
                                        onClick = {
                                            openIndexs.value =
                                                if (openIndexs.value == index) -1 else index
                                            Log.d("TESTES", openIndexs.value.toString())
                                        }
                                    )
                                }
                                Icon(
                                    painter = painterResource(R.drawable.ic_keyboard),
                                    contentDescription = "Question Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        if (openIndexs.value == index) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = GreenPrimary.copy(alpha = 0.2f),
                                elevation = 0.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 8.dp
                                    )
                                ) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        Text(item.answer)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}