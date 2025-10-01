package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.presentation.book.add_edit.BookAddEditEvent
import com.oliinyk.yaroslav.easyreads.presentation.book.add_edit.BookAddEditViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components.BookAddEditScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddEditScreen(
    modifier: Modifier = Modifier,
    viewModel: BookAddEditViewModel,
    onEvent: (BookAddEditEvent) -> Unit,
    onCoverClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                actions = {
                    IconButton(
                        onClick = { onSaveClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = stringResource(R.string.menu_item__book_edit__save_text),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            BookAddEditScreenContent(
                modifier = modifier.padding(paddingValues),
                stateUi = stateUi,
                onCoverClick = onCoverClick,
                onEvent = onEvent
            )
        }
    )
}
