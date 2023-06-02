package com.example.temansawit.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.util.RandomEntriesGenerator
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class ChartViewModel : ViewModel() {

    private val generator = RandomEntriesGenerator(
        xRange = 0..GENERATOR_X_RANGE_TOP,
        yRange = GENERATOR_Y_RANGE_BOTTOM..GENERATOR_Y_RANGE_TOP,
    )

    private val customStepGenerator = RandomEntriesGenerator(
        xRange = IntProgression.fromClosedRange(rangeStart = 0, rangeEnd = GENERATOR_X_RANGE_TOP, step = 2),
        yRange = GENERATOR_Y_RANGE_BOTTOM..GENERATOR_Y_RANGE_TOP,
    )

    internal val chartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

    internal val customStepChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

    internal val multiDataSetChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

    internal val composedChartEntryModelProducer: ComposedChartEntryModelProducer<ChartEntryModel> =
        multiDataSetChartEntryModelProducer + chartEntryModelProducer

    init {
        viewModelScope.launch {
            while (currentCoroutineContext().isActive) {
                chartEntryModelProducer.setEntries(generator.generateRandomEntries())
                multiDataSetChartEntryModelProducer.setEntries(
                    entries = List(size = MULTI_ENTRIES_COMBINED) {
                        generator.generateRandomEntries()
                    },
                )
                customStepChartEntryModelProducer.setEntries(customStepGenerator.generateRandomEntries())
                delay(UPDATE_FREQUENCY)
            }
        }
    }

    private companion object {
        const val MULTI_ENTRIES_COMBINED = 3
        const val GENERATOR_X_RANGE_TOP = 96
        const val GENERATOR_Y_RANGE_BOTTOM = 2
        const val GENERATOR_Y_RANGE_TOP = 20
        const val UPDATE_FREQUENCY = 2000L
    }
}
