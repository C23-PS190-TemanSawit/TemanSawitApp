package com.example.temansawit.ui.components.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temansawit.ui.components.home.chart.rememberChartStyle
import com.example.temansawit.ui.components.home.chart.rememberMarker
import com.example.temansawit.ui.screen.home.ChartViewModel
import com.example.temansawit.ui.theme.GreenPrimary
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
private fun ComposeChart1(chartEntryModelProducer: ChartEntryModelProducer) {
    val marker = rememberMarker()
    val chartEntryModel = entryModelOf(400000, 120000, 80000, 63234)
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        Chart(
            chart = lineChart(persistentMarkers = remember(marker) { mapOf(PERSISTENT_MARKER_X to marker) }),
            model = chartEntryModel,
            startAxis = startAxis(),
            bottomAxis = bottomAxis(guideline = null),
            marker = marker,
        )
//        Chart(
//            chart = lineChart(persistentMarkers = remember(marker) { mapOf(PERSISTENT_MARKER_X to marker) }),
//            chartModelProducer = chartEntryModelProducer,
//            startAxis = startAxis(),
//            bottomAxis = bottomAxis(guideline = null),
//            marker = marker,
//        )
    }
}

@Composable
internal fun Chart(viewModel: ChartViewModel = viewModel()) {
    Column{
        ChartItems(viewModel)
    }
}

@Composable
private fun ChartItems( viewModel: ChartViewModel) {
    CardItem { ComposeChart1(viewModel.customStepChartEntryModelProducer) }
}

@Composable
private fun CardItem(content: @Composable () -> Unit) {
        Card(shape = MaterialTheme.shapes.large) {
            Box(Modifier.padding(padding)) {
                content()
            }
        }
}

private val padding = 16.dp

private val COLOR_1_CODE = GreenPrimary
private const val PERSISTENT_MARKER_X = 10f

private val color1 = COLOR_1_CODE
private val chartColors = listOf(color1)

