package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordEvent
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordUiState
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components.ReadingSessionRecordContent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components.ReadingSessionRecordTopAppBar

@Composable
fun ReadingSessionRecordScreen(
    viewModel: ReadingSessionRecordViewModel,
    onEvent: (ReadingSessionRecordEvent) -> Unit
) {
    val uiState: ReadingSessionRecordUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ReadingSessionRecordTopAppBar()
        }
    ) { paddingValues ->
        ReadingSessionRecordContent(
            stateUi = uiState,
            onEvent = onEvent,
            modifier = Modifier.padding(paddingValues)
        )
    }

}