package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSessionRecordStatusType
import com.oliinyk.yaroslav.easyreads.domain.service.ReadTimeCounterService
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record.components.ReadingSessionRecordContent
import com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record.components.ReadingSessionRecordTopAppBar
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.util.UUID

@Composable
fun ReadingSessionRecordScreen(
    bookId: String?,
    navBack: () -> Unit,
    navToNoteList: (String) -> Unit,
    viewModel: ReadingSessionRecordViewModel = hiltViewModel(),
) {
    val uiState: ReadingSessionRecordUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isTriggeredNavTo by remember { mutableStateOf(false) }
    var openConfirmDialog by rememberSaveable { mutableStateOf(false) }

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
                },
            )
        }
    }

    ReadingSessionRecordScreen(
        uiState = uiState,
        navBack = {
            if (!isTriggeredNavTo) {
                isTriggeredNavTo = true

                openConfirmDialog = true
            }
        },
        onEvent = { event ->
            handleEvent(
                event = event,
                context = context,
                viewModel = viewModel,
                uiState = uiState,
                navBack = navBack,
                navToNoteList = {
                    it
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true

                        navToNoteList(it)
                    }
                },
            )
        },
    )

    // ----- Dialogs -----

    if (openConfirmDialog) {
        AppConfirmDialog(
            title = stringResource(R.string.reading_session_record__confirmation_dialog__title_text),
            message = stringResource(R.string.reading_session_record__confirmation_dialog__message_text),
            onConfirm = {
                context.startService(
                    Intent(context, ReadTimeCounterService::class.java).apply {
                        action = ReadTimeCounterService.Actions.STOP.toString()
                    },
                )
                viewModel.removeUnfinishedReadingSession()
                openConfirmDialog = false
                navBack()
            },
            onDismiss = {
                isTriggeredNavTo = false
                openConfirmDialog = false
            },
        )
    }
}

@Composable
internal fun ReadingSessionRecordScreen(
    uiState: ReadingSessionRecordUiState,
    navBack: () -> Unit,
    onEvent: (ReadingSessionRecordEvent) -> Unit,
) {
    BackHandler {
        navBack()
    }

    Scaffold(
        topBar = {
            ReadingSessionRecordTopAppBar(navBack = navBack)
        },
    ) { paddingValues ->
        ReadingSessionRecordContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

private fun handleEvent(
    event: ReadingSessionRecordEvent,
    context: Context,
    viewModel: ReadingSessionRecordViewModel,
    uiState: ReadingSessionRecordUiState,
    navBack: () -> Unit,
    navToNoteList: (String) -> Unit,
) {
    when (event) {
        is ReadingSessionRecordEvent.OnStartPause -> {
            viewModel.resumeOrPause { resumeOrPauseAction ->
                context.startService(
                    Intent(
                        context,
                        ReadTimeCounterService::class.java,
                    ).apply {
                        action = resumeOrPauseAction.toString()
                    },
                )
            }
        }
        is ReadingSessionRecordEvent.OnPause -> {
            viewModel.currentReadingSession?.let {
                if (it.recordStatus == ReadingSessionRecordStatusType.STARTED) {
                    context.startService(
                        Intent(
                            context,
                            ReadTimeCounterService::class.java,
                        ).apply {
                            action = ReadTimeCounterService.Actions.PAUSE.toString()
                        },
                    )
                }
            }
        }
        is ReadingSessionRecordEvent.OnShowNotes -> {
            uiState.book?.let { book ->
                navToNoteList(book.id.toString())
            }
        }
        is ReadingSessionRecordEvent.OnAddNote -> {
            viewModel.addNote(event.note)
        }
        is ReadingSessionRecordEvent.OnFinish -> {
            context.startService(
                Intent(
                    context,
                    ReadTimeCounterService::class.java,
                ).apply {
                    action = ReadTimeCounterService.Actions.STOP.toString()
                },
            )
            viewModel.save(event.readingSession)
            navBack()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionRecordScreenPreview() {
    EasyReadsTheme {
        ReadingSessionRecordScreen(
            uiState =
                ReadingSessionRecordUiState(
                    book =
                        Book(
                            title = "Title",
                            author = "Author",
                            pageCurrent = 50,
                            pagesCount = 250,
                        ),
                ),
            navBack = {},
            onEvent = {},
        )
    }
}
