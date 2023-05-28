package com.example.temansawit.ui.screen.faq

import androidx.compose.ui.graphics.Color
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.temansawit.R
import com.example.temansawit.model.Faqs.FAQS
import com.example.temansawit.ui.theme.GreenPrimary

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FaqScreen(
    modifier: Modifier = Modifier
) {
    val openIndexs = remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "FAQ",textAlign = TextAlign.Center,modifier =Modifier.fillMaxWidth())},
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
        }
    ) {
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
                                    openIndexs.value = if (openIndexs.value == index) -1 else index
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
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                            Box(modifier = Modifier.weight(1f)) {
                                Text(item.answer)
                            }
                        }
                    }
                }
            }
        }
    }
}
