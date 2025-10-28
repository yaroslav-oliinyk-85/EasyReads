package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordEvent
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordStateUi
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components.ReadingSessionRecordContent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components.ReadingSessionRecordTopAppBar

@Composable
fun ReadingSessionRecordScreen(
    viewModel: ReadingSessionRecordViewModel,
    onEvent: (ReadingSessionRecordEvent) -> Unit
) {
    val stateUi: ReadingSessionRecordStateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ReadingSessionRecordTopAppBar()
        }
    ) { paddingValues ->
        ReadingSessionRecordContent(
            stateUi = stateUi,
            onEvent = onEvent,
            modifier = Modifier.padding(paddingValues)
        )
    }

}