package com.example.temansawit.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.temansawit.R

data class BottomBarItem(val title: String, val icon: Painter)

@Composable
fun ListBottomMenu() : List<BottomBarItem> {
    val bottomBarItemList = arrayListOf<BottomBarItem>()

    bottomBarItemList.add(
        BottomBarItem(
            title = stringResource(id = R.string.home_nav),
            icon = painterResource(id = R.drawable.baseline_home_24)
        ))
    bottomBarItemList.add(
        BottomBarItem(
        title = stringResource(id = R.string.about_nav),
        icon = painterResource(id = R.drawable.baseline_quiz_24)
    ))
    bottomBarItemList.add(
        BottomBarItem(
            title = stringResource(id = R.string.faq_nav),
            icon = painterResource(id = R.drawable.baseline_quiz_24)
        )
    )
    bottomBarItemList.add(
        BottomBarItem(
            title = stringResource(id = R.string.profile_nav),
            icon = painterResource(id = R.drawable.baseline_person_24)
        ))
    return bottomBarItemList
}