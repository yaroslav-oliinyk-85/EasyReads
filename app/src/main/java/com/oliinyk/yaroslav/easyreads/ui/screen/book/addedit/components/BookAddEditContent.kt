package com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditEvent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.format.DateTimeFormatter

@Composable
fun BookAddEditContent(
    modifier: Modifier = Modifier,
    uiState: BookAddEditUiState,
    onClickChangeCoverImage: () -> Unit,
    onEvent: (BookAddEditEvent) -> Unit,
    onAddedDateClick: () -> Unit,
    onFinishedDateClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = Dimens.paddingHorizontalMedium)
                .padding(
                    top = Dimens.paddingTopMedium,
                    bottom = Dimens.paddingEndSmall,
                ),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(all = Dimens.paddingAllMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
            ) {
                CoverImageAndShelfButton(
                    uiState = uiState,
                    onCoverClick = onClickChangeCoverImage,
                    onEvent = onEvent,
                )
                TitleEditField(
                    uiState = uiState,
                    onEvent = onEvent,
                )
                AuthorEditField(
                    uiState = uiState,
                    onEvent = onEvent,
                )
                PagesCountEditField(
                    uiState = uiState,
                    onEvent = onEvent,
                )
                IsbnEditField(
                    uiState = uiState,
                    onEvent = onEvent,
                )
                DescriptionEditField(
                    uiState = uiState,
                    onEvent = onEvent,
                )
                AddedDateEditField(
                    uiState = uiState,
                    onAddedDateClick = onAddedDateClick,
                )
                FinishedDateEditField(
                    uiState = uiState,
                    onFinishedDateClick = onFinishedDateClick,
                )
            }
        }
        Spacer(Modifier.height(Dimens.spacerHeightExtraLarge))
    }
}

@Composable
private fun CoverImageAndShelfButton(
    uiState: BookAddEditUiState,
    onCoverClick: () -> Unit,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        BookAddEditCoverImageSection(
            stateUi = uiState,
            onCoverClick = onCoverClick,
        )

        Spacer(Modifier.height(Dimens.spacerHeightSmall))

        ShelvesButtonWithDropdownMenu(
            modifier = Modifier.width(Dimens.bookAddEditBookCoverImageSize.width),
            uiState = uiState,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun TitleEditField(
    uiState: BookAddEditUiState,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    BookEditFieldWithDropdownMenu(
        label = stringResource(R.string.book_add_edit__label__book_title_text),
        value = uiState.book.title,
        suggestions = uiState.suggestionTitles,
        hint = stringResource(R.string.book_add_edit__hint__enter_book_title_text),
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
        singleLine = false,
        onValueChange = { title -> onEvent(BookAddEditEvent.TitleChanged(title)) },
    )
}

@Composable
private fun AuthorEditField(
    uiState: BookAddEditUiState,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    BookEditFieldWithDropdownMenu(
        label = stringResource(R.string.book_add_edit__label__book_author_text),
        value = uiState.book.author,
        suggestions = uiState.suggestionAuthors,
        hint = stringResource(R.string.book_add_edit__hint__enter_book_author_text),
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
        onValueChange = { author -> onEvent(BookAddEditEvent.AuthorChanged(author)) },
    )
}

@Composable
private fun PagesCountEditField(
    uiState: BookAddEditUiState,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.book_add_edit__label__book_pages_amount_text),
        value = if (uiState.book.pagesCount != 0) uiState.book.pagesCount.toString() else "",
        hint = stringResource(R.string.book_add_edit__hint__book_pages_count_text),
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        onValueChange = { pageAmount ->
            onEvent(
                BookAddEditEvent.PageAmountChanged(
                    pageAmount.trim().take(AppConstants.BOOK_PAGE_AMOUNT_MAX_LENGTH),
                ),
            )
        },
    )
}

@Composable
private fun IsbnEditField(
    uiState: BookAddEditUiState,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.book_add_edit__label__book_isbn_text),
        value = uiState.book.isbn,
        hint = stringResource(R.string.book_add_edit__hint__book_isbn_input_text),
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        onValueChange = { isbn ->
            onEvent(
                BookAddEditEvent.IsbnChanged(
                    isbn
                        .trim()
                        .filter { it.isDigit() }
                        .take(AppConstants.BOOK_ISBN_MAX_LENGTH),
                ),
            )
        },
    )
}

@Composable
private fun DescriptionEditField(
    uiState: BookAddEditUiState,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.book_add_edit__label__book_description_text),
        value = uiState.book.description,
        hint = stringResource(R.string.book_add_edit__hint__enter_book_description_text),
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Default,
            ),
        singleLine = false,
        minLines = Dimens.descriptionMinLines,
        onValueChange = { description ->
            onEvent(
                BookAddEditEvent.DescriptionChanged(
                    description,
                ),
            )
        },
    )
}

@Composable
private fun AddedDateEditField(
    uiState: BookAddEditUiState,
    onAddedDateClick: () -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.book_add_edit__button__added_date_text),
        value =
            uiState.book.addedAt.format(
                DateTimeFormatter.ofPattern(
                    stringResource(R.string.date_format),
                ),
            ),
        hint = "",
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            AppIconButton(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "",
                enabledBorder = false,
                onClick = onAddedDateClick,
            )
        },
    )
}

@Composable
private fun FinishedDateEditField(
    uiState: BookAddEditUiState,
    onFinishedDateClick: () -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.book_add_edit__button__finished_date_text),
        value =
            uiState.book.finishedAt?.format(
                DateTimeFormatter.ofPattern(
                    stringResource(R.string.date_format),
                ),
            ) ?: "",
        hint = "",
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            AppIconButton(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "",
                enabledBorder = false,
                enabled = uiState.book.isFinished,
                onClick = onFinishedDateClick,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun BookAddEditContentPreview() {
    EasyReadsTheme {
        BookAddEditContent(
            uiState = BookAddEditUiState(),
            onClickChangeCoverImage = {},
            onEvent = {},
            onAddedDateClick = {},
            onFinishedDateClick = {},
        )
    }
}
