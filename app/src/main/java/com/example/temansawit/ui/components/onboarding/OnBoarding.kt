package com.example.temansawit.ui.components.onboarding

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.temansawit.di.Preferences
import com.example.temansawit.model.OnboardingPage
import com.example.temansawit.model.pages
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingUI(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0)

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        HorizontalPager(state = pagerState, pageCount = 3) { it ->
            OnboardingPage(page = pages[it])
        }
        Row(
            Modifier
                .height(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) GreenPrimary else Color.LightGray
                if (pagerState.currentPage == iteration) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(25.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(5.dp)
                    )
                }
            }
        }
        if (pagerState.currentPage == 2) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = modifier
                        .weight(1F),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                        Preferences.setOnboarded(sharedPreferences, true)
                        navHostController.navigate("home")
                    },
                ) {
                    Text(text = "Mulai")
                }
            }
        } else {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = modifier
                        .width(136.dp),
                    border = BorderStroke(1.dp, GreenPrimary.copy(alpha = 0.2F)),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                        Preferences.setOnboarded(sharedPreferences, true)
                        navHostController.navigate("home")
                    },
                ) {
                    Text(text = "Lewati")
                }


            }
        }
    }
}

@Composable
fun OnboardingPage(
    page: OnboardingPage,
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        OnBoardingImage(image = painterResource(id = page.image), contentDescription = page.title)
        OnBoardingText(title = page.title, description = page.description)
    }
}

@Composable
fun OnBoardingImage(
    modifier: Modifier = Modifier,
    image: Painter,
    contentDescription: String
) {
    Image(
        painter = image,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(450.dp)
            .clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)),
        )
}

@Composable
fun OnBoardingText(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = Typography.h5
        )
        Spacer(modifier = modifier.padding(vertical = 12.dp))
        Text(
            text = description,
            style = Typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BtnGetStarted(
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun OnBoardingTextPreview() {
    Column {
        OnBoardingImage(
            image = painterResource(id = com.example.temansawit.R.drawable.onboarding1), contentDescription = "Bibit")
        OnBoardingText(title = "Bibit", description = "Optimalkan produksi kelapa sawit Anda dengan teknologi terbaru dalam deteksi abnormalitas pada tanaman.")
    }
}
