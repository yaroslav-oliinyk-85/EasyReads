package com.oliinyk.yaroslav.easyreads.ui.screen.my_library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelveType
import com.oliinyk.yaroslav.easyreads.presentation.my_library.MyLibraryViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components.ReadingGoalProgressCard
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components.ShelvesCard
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: MyLibraryViewModel,
    onReadingGoalClick: () -> Unit,
    onShelfClick: (BookShelveType) -> Unit,
    onSeeAllClick: () -> Unit,
    onAddBookClick: (Book) -> Unit
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.my_library__toolbar__title_text))
                },
                actions = {
                    IconButton(onClick = { onAddBookClick(Book()) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.menu_item__my_library__add_text),
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
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(
                        horizontal = Dimens.MyLibraryScreen.columnPaddingHorizontal,
                        vertical = Dimens.MyLibraryScreen.columnPaddingVertical),
                verticalArrangement = Arrangement.spacedBy(
                    Dimens.MyLibraryScreen.lazyColumnVerticalArrangementSpace
                )
            ) {
                item {
                    ReadingGoalProgressCard(
                        modifier = modifier,
                        stateUi = stateUi,
                        isVisibleChangeGoalButton = false,
                        onClick = onReadingGoalClick
                    )
                }
                item {
                    ShelvesCard(
                        modifier = modifier,
                        stateUi = stateUi,
                        onShelfClick = onShelfClick,
                        onSeeAllClick = onSeeAllClick
                    )
                }
            }
        }
    )
}
