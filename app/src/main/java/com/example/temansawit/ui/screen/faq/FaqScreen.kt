package com.example.temansawit.ui.screen.faq

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.temansawit.model.Faqs.FAQS


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FaqScreen(
    modifier: Modifier = Modifier
) {
    var openIndexs= remember {
        mutableStateOf(-1)
    }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(FAQS) {
                index, item ->
                Column (modifier = Modifier.fillMaxWidth() ) {
                    ClickableText(text = AnnotatedString(item.question),
                    onClick = {

                        if (openIndexs.value == index) {
                            openIndexs.value = -1

                        } else {
                            openIndexs.value = index
                        }
                        Log.d("TESTES", openIndexs.value.toString())

                    }
                        ) //row+icon

                    if (openIndexs.value == index) Text(item.answer)

                }

            }
            
//            items(faqs) { Faq ->
//                ListItem(
//                    modifier = Modifier.padding(8.dp),
//                    text = { Text(text = item) }
//                )
//            }
        }

}
