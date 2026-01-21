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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components.BookAddEditAppTopBar
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components.BookAddEditBottomBar
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components.BookAddEditContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddEditScreen(
    navBack: () -> Unit,
    modifier: Modifier = Modifier,
    bookId: String? = null,
    viewModel: BookAddEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        bookId?.let { id ->
            viewModel.setup(id)
        }
    }

    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.msg_warn__not_able_to_open_image_picker)

    BackHandler {
        viewModel.removeUnusedCoverImage(context.applicationContext)
        navBack()
    }

    // ----- Pick Image Launcher -----
    val pickBookCoverImageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
        ) { uri: Uri? ->
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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BookAddEditAppTopBar(
                bookTitle = stateUi.book.title,
                navBack = {
                    viewModel.removeUnusedCoverImage(context.applicationContext)
                    navBack()
                }
            )
        },
        content = { paddingValues ->
            BookAddEditContent(
                modifier = modifier.padding(paddingValues),
                stateUi = stateUi,
                onClickChangeCoverImage = { launchBookCoverImagePicker() },
                onEvent = { viewModel.onEvent(it) },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BookAddEditBottomBar(
                onClickSave = {
                    viewModel.save(context.applicationContext)
                    navBack()
                },
                onClickCancel = {
                    viewModel.removeUnusedCoverImage(
                        context.applicationContext,
                    )
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
