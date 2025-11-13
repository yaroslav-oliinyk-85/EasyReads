package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSessionRecordStatusType
import com.oliinyk.yaroslav.easyreads.domain.service.ReadTimeCounterService
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordEvent
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordUiState
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components.ReadingSessionRecordContent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components.ReadingSessionRecordTopAppBar
import java.util.UUID

@Composable
fun ReadingSessionRecordScreen(
    bookId: String?,
    navBack: () -> Unit,
    navToNoteList: (String) -> Unit,
    viewModel: ReadingSessionRecordViewModel = hiltViewModel()
) {
    val uiState: ReadingSessionRecordUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        bookId?.let { id ->
            viewModel.setup(UUID.fromString(id))
        }
    }
    LaunchedEffect(uiState.book) {
        uiState.book?.let { book ->
            context.startService(
                Intent(context, ReadTimeCounterService::class.java).apply {
                    action = ReadTimeCounterService.Actions.START.toString()
                    putExtra("bookId", book.id.toString())
                    putExtra("bookTitle", book.title)
                    putExtra("pageCurrent", book.pageCurrent)
                }
            )
        }
    }

    BackHandler {
        context.startService(
            Intent(context, ReadTimeCounterService::class.java).apply {
                action = ReadTimeCounterService.Actions.STOP.toString()
            }
        )
        viewModel.removeUnfinishedReadingSession()
        navBack()
    }

    Scaffold(
        topBar = {
            ReadingSessionRecordTopAppBar()
        }
    ) { paddingValues ->
        ReadingSessionRecordContent(
            stateUi = uiState,
            onEvent = { event ->
                when (event) {
                    ReadingSessionRecordEvent.OnStartPause -> {
                        viewModel.resumeOrPause { resumeOrPauseAction ->
                            context.startService(
                                Intent(context, ReadTimeCounterService::class.java).apply {
                                    action = resumeOrPauseAction.toString()
                                }
                            )
                        }
                    }

                    ReadingSessionRecordEvent.OnPause -> {
                        viewModel.currentReadingSession?.let {
                            if (it.recordStatus == ReadingSessionRecordStatusType.STARTED) {
                                context.startService(
                                    Intent(
                                        context,
                                        ReadTimeCounterService::class.java
                                    ).apply {
                                        action = ReadTimeCounterService.Actions.PAUSE.toString()
                                    }
                                )
                            }
                        }
                    }

                    ReadingSessionRecordEvent.OnShowNotes -> {
                        uiState.book?.let { book ->
                            navToNoteList(book.id.toString())
                        }
                    }

                    is ReadingSessionRecordEvent.OnAddNote -> {
                        viewModel.addNote(event.note)
                    }

                    is ReadingSessionRecordEvent.OnFinish -> {
                        context.startService(
                            Intent(context, ReadTimeCounterService::class.java).apply {
                                action = ReadTimeCounterService.Actions.STOP.toString()
                            }
                        )
                        viewModel.save(event.readingSession)
                        navBack()
                    }
                }
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}
