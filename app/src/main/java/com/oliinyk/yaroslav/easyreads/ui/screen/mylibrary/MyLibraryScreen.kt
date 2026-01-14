package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.BookSorting
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingGoalRepository
import com.oliinyk.yaroslav.easyreads.ui.components.AppFloatingActionButton
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components.MyLibraryContent
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components.MyLibraryTopAppBar
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components.ReadingGoalChangeDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Composable
fun MyLibraryScreen(
    navToBookAdd: () -> Unit,
    navToReadingGoal: () -> Unit,
    navToBookListByShelvesType: (String) -> Unit,
    navToBookList: () -> Unit,
    navToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyLibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var openChangeGoalDialog by rememberSaveable { mutableStateOf(false) }
    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyLibraryTopAppBar(
                onSettingsClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToSettings()
                    }
                },
            )
        },
        floatingActionButton = {
            if (uiState.totalBooksCount > 0) {
                AppFloatingActionButton(
                    onClick = {
                        navToBookAdd()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.menu_item__add_text),
                        )
                    },
                )
            }
        },
        content = { paddingValues ->
            MyLibraryContent(
                uiState = uiState,
                onAddBookClick = navToBookAdd,
                onReadingGoalClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToReadingGoal()
                    }
                },
                onChangeGoalClicked = {
                    openChangeGoalDialog = true
                },
                onShelfClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookListByShelvesType(it.toString())
                    }
                },
                onSeeAllClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookList()
                    }
                },
                modifier = Modifier.padding(paddingValues),
            )
        },
    )

    // ----- Dialogs -----

    if (openChangeGoalDialog) {
        ReadingGoalChangeDialog(
            readingGoal = uiState.readingGoal,
            onDismissRequest = {
                openChangeGoalDialog = false
            },
            onSave = {
                viewModel.updateReadingGoal(it)
            },
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MyLibraryScreenPreview() {
    EasyReadsTheme {
        MyLibraryScreen(
            viewModel =
                MyLibraryViewModel(
                    bookRepository =
                        object : BookRepository {
                            override fun getAllSorted(bookSorting: BookSorting): Flow<List<Book>> {
                                TODO("Not yet implemented")
                            }

                            override fun getByShelveSorted(
                                bookShelvesType: BookShelvesType,
                                bookSorting: BookSorting,
                            ): Flow<List<Book>> {
                                TODO("Not yet implemented")
                            }

                            override suspend fun getAll(): List<Book> {
                                TODO("Not yet implemented")
                            }

                            override fun getAllAsFlow(): Flow<List<Book>> {
                                TODO("Not yet implemented")
                            }

                            override fun getById(id: UUID): Flow<Book?> {
                                TODO("Not yet implemented")
                            }

                            override fun getAuthors(): Flow<List<String>> {
                                TODO("Not yet implemented")
                            }

                            override fun save(book: Book) {
                                TODO("Not yet implemented")
                            }

                            override fun saveAll(books: List<Book>) {
                                TODO("Not yet implemented")
                            }

                            override fun update(book: Book) {
                                TODO("Not yet implemented")
                            }

                            override fun remove(book: Book) {
                                TODO("Not yet implemented")
                            }
                        },
                    readingGoalRepository =
                        object : ReadingGoalRepository {
                            override suspend fun getAll(): List<ReadingGoal> {
                                TODO("Not yet implemented")
                            }

                            override fun getByYear(year: Int): Flow<ReadingGoal?> {
                                TODO("Not yet implemented")
                            }

                            override fun save(readingGoal: ReadingGoal) {
                                TODO("Not yet implemented")
                            }

                            override fun saveAll(readingGoals: List<ReadingGoal>) {
                                TODO("Not yet implemented")
                            }

                            override fun update(readingGoal: ReadingGoal) {
                                TODO("Not yet implemented")
                            }
                        },
                ),
            navToBookAdd = { },
            navToReadingGoal = { },
            navToBookListByShelvesType = { },
            navToBookList = { },
            navToSettings = { },
        )
    }
}
