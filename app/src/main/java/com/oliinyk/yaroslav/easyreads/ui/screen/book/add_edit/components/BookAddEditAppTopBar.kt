package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddEditAppTopBar(
    bookTitle: String
) {
    TopAppBar(
        title = {
            Text(
                text = if (bookTitle.isBlank()) {
                    stringResource(R.string.book_add_edit__title__add_text)
                } else {
                    stringResource(R.string.book_add_edit__title__edit_text)
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}