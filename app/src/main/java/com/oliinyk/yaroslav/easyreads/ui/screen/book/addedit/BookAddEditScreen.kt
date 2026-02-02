package com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.components.AppDatePickerDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components.BookAddEditAppTopBar
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components.BookAddEditBottomBar
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components.BookAddEditContent
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun BookAddEditScreen(
    navBack: () -> Unit,
    bookId: String? = null,
    viewModel: BookAddEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        bookId?.let { id ->
            viewModel.setup(id)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.msg_warn__not_able_to_open_image_picker)

    var isTriggeredNavTo by remember { mutableStateOf(false) }
    var openConfirmDialog by rememberSaveable { mutableStateOf(false) }
    var showAddedDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    var showFinishedDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val datePickerState =
        rememberDatePickerState(
            selectableDates =
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                        utcTimeMillis <= System.currentTimeMillis()
                },
        )

    BackHandler {
        openConfirmDialog = true
    }

    // ----- Pick Image Launcher -----
    val pickBookCoverImageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
        ) { uri: Uri? ->
            isTriggeredNavTo = false

            if (uri != null) {
                coroutineScope.launch {
                    viewModel.updateCoverImage(
                        context.applicationContext,
                        uri,
                    )
                }
            }
        }

    fun launchBookCoverImagePicker() {
        val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        val intent = pickBookCoverImageLauncher.contract.createIntent(context, request)

        if (canResolveIntent(context, intent)) {
            pickBookCoverImageLauncher.launch(request)
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    snackbarMessage,
                )
            }
        }
    }

    BookAddEditScreen(
        uiState = uiState,
        bookId = bookId,
        snackbarHostState = snackbarHostState,
        navBack = {
            openConfirmDialog = true
        },
        onEvent = { viewModel.onEvent(it) },
        onAddedDateClick = {
            showAddedDatePickerDialog = true
        },
        onFinishedDateClick = {
            showFinishedDatePickerDialog = true
        },
        onClickChangeCoverImage = {
            if (!isTriggeredNavTo) {
                isTriggeredNavTo = true

                launchBookCoverImagePicker()
            }
        },
        onClickSave = {
            if (!isTriggeredNavTo) {
                isTriggeredNavTo = true

                viewModel.save(context.applicationContext)
                navBack()
            }
        },
    )

    // ----- Dialogs -----

    if (showAddedDatePickerDialog) {
        AppDatePickerDialog(
            datePickerState = datePickerState,
            onDateSelected = { selectedDate ->
                viewModel.updateStateUi {
                    it.copy(book = it.book.copy(addedAt = LocalDateTime.of(selectedDate, LocalTime.now())))
                }
            },
            onDismissRequest = { showAddedDatePickerDialog = false },
        )
    }

    if (showFinishedDatePickerDialog) {
        AppDatePickerDialog(
            datePickerState = datePickerState,
            onDateSelected = { selectedDate ->
                viewModel.updateStateUi {
                    it.copy(book = it.book.copy(finishedAt = LocalDateTime.of(selectedDate, LocalTime.now())))
                }
            },
            onDismissRequest = { showFinishedDatePickerDialog = false },
        )
    }

    if (openConfirmDialog) {
        AppConfirmDialog(
            title = stringResource(R.string.book_add_edit__confirmation_dialog__title_text),
            message = stringResource(R.string.book_add_edit__confirmation_dialog__message_text),
            onConfirm = {
                if (!isTriggeredNavTo) {
                    isTriggeredNavTo = true

                    viewModel.removeUnusedCoverImage(context.applicationContext)
                    openConfirmDialog = false
                    navBack()
                }
            },
            onDismiss = {
                isTriggeredNavTo = false
                openConfirmDialog = false
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddEditScreen(
    uiState: BookAddEditUiState,
    bookId: String? = null,
    snackbarHostState: SnackbarHostState,
    navBack: () -> Unit,
    onEvent: (BookAddEditEvent) -> Unit,
    onAddedDateClick: () -> Unit,
    onFinishedDateClick: () -> Unit,
    onClickChangeCoverImage: () -> Unit,
    onClickSave: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BookAddEditAppTopBar(
                isAdding = bookId == null,
                navBack = {
                    navBack()
                },
            )
        },
        content = { paddingValues ->
            BookAddEditContent(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onClickChangeCoverImage = onClickChangeCoverImage,
                onEvent = onEvent,
                onAddedDateClick = onAddedDateClick,
                onFinishedDateClick = onFinishedDateClick,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BookAddEditBottomBar(
                onClickSave = {
                    onClickSave()
                },
                onClickCancel = {
                    navBack()
                },
            )
        },
    )
}

private fun canResolveIntent(
    context: Context,
    intent: Intent,
): Boolean {
    val packageManager: PackageManager = context.packageManager
    val resolvedActivity: ResolveInfo? =
        packageManager.resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY,
        )
    return resolvedActivity != null
}

@Preview
@Composable
private fun BookAddEditScreenPreview() {
    EasyReadsTheme {
        BookAddEditScreen(
            uiState = BookAddEditUiState(book = Book(shelf = BookShelvesType.FINISHED)),
            snackbarHostState = SnackbarHostState(),
            navBack = {},
            onEvent = {},
            onAddedDateClick = {},
            onFinishedDateClick = {},
            onClickChangeCoverImage = {},
            onClickSave = {},
        )
    }
}

@Preview
@Composable
private fun BookAddEditScreenWantToReadPreview() {
    EasyReadsTheme {
        BookAddEditScreen(
            uiState = BookAddEditUiState(),
            snackbarHostState = SnackbarHostState(),
            navBack = {},
            onEvent = {},
            onAddedDateClick = {},
            onFinishedDateClick = {},
            onClickChangeCoverImage = {},
            onClickSave = {},
        )
    }
}
