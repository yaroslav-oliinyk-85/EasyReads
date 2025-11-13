package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.list

import android.text.format.DateFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.list.ReadingSessionListViewModel
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.add_edit_dialog.ReadingSessionAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.list.components.ReadingSessionListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.list.components.ReadingSessionListTopAppBar
import java.util.UUID

@Composable
fun ReadingSessionListScreen(
    bookId: String?,
    viewModel: ReadingSessionListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        bookId?.let { id ->
            viewModel.setup(UUID.fromString(id))
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var addOrEditReadingSessionState: ReadingSession? by remember { mutableStateOf(null) }
    var removeReadingSessionState: ReadingSession? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            ReadingSessionListTopAppBar(
                sessionsCount = uiState.readingSessions.size
            )
        }
    ) { paddingValues ->
        ReadingSessionListContent(
            readingSessions = uiState.readingSessions,
            onClickedEdit = { addOrEditReadingSessionState = it},
            onClickedRemove = { removeReadingSessionState = it },
            modifier = Modifier.padding(paddingValues)
        )
    }

    // ----- Dialogs -----

    addOrEditReadingSessionState?.let { readingSession ->
        ReadingSessionAddEditDialog(
            readingSession = readingSession,
            onSave = {
                viewModel.save(it)
                addOrEditReadingSessionState = null
            },
            onDismissRequest = { addOrEditReadingSessionState = null }
        )
    }

    removeReadingSessionState?.let { readingSession ->
        AppConfirmDialog(
            title = stringResource(
                R.string.reading_session_list__confirmation_dialog__title_text
            ),
            message = stringResource(
                R.string.reading_session_list__confirmation_dialog__message_text,
                DateFormat.format(
                    stringResource(R.string.date_and_time_format),
                    readingSession.startedDate
                ).toString(),
                readingSession.readPages,
                readingSession.startPage,
                readingSession.endPage
            ),
            onConfirm = {
                viewModel.remove(readingSession)
                removeReadingSessionState = null
            },
            onDismiss = { removeReadingSessionState = null }
        )
    }
}