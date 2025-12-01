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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditEvent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditStateUi
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookAddEditContent(
    modifier: Modifier = Modifier,
    stateUi: BookAddEditStateUi,
    onClickChangeCoverImage: () -> Unit,
    onEvent: (BookAddEditEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.paddingHorizontalMedium)
            .padding(
                top = Dimens.paddingTopMedium,
                bottom = Dimens.paddingEndSmall
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = Dimens.paddingAllMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BookAddEditCoverImageSection(
                        stateUi = stateUi,
                        onCoverClick = onClickChangeCoverImage
                    )

                    Spacer(Modifier.height(Dimens.spacerHeightSmall))

                    ShelvesButtonWithDropdownMenu(
                        modifier = Modifier.width(Dimens.bookAddEditBookCoverImageSize.width),
                        stateUi = stateUi,
                        onEvent = onEvent
                    )
                }

                // Title
                BookEditFieldWithDropdownMenu(
                    label = stringResource(R.string.book_add_edit__label__book_title_text),
                    value = stateUi.book.title,
                    suggestions = stateUi.suggestionTitles,
                    hint = stringResource(R.string.book_add_edit__hint__enter_book_title_text),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = false,
                    onValueChange = { title -> onEvent(BookAddEditEvent.TitleChanged(title)) }
                )

                // Author
                BookEditFieldWithDropdownMenu(
                    label = stringResource(R.string.book_add_edit__label__book_author_text),
                    value = stateUi.book.author,
                    suggestions = stateUi.suggestionAuthors,
                    hint = stringResource(R.string.book_add_edit__hint__enter_book_author_text),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { author -> onEvent(BookAddEditEvent.AuthorChanged(author)) }
                )

                // Page Amount
                AppEditField(
                    label = stringResource(R.string.book_add_edit__label__book_pages_amount_text),
                    value = if (stateUi.book.pageAmount != 0) stateUi.book.pageAmount.toString() else "",
                    hint = stringResource(R.string.book_add_edit__hint__book_pages_count_text),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { pageAmount ->
                        onEvent(
                            BookAddEditEvent.PageAmountChanged(
                                pageAmount.trim().take(AppConstants.BOOK_PAGE_AMOUNT_MAX_LENGTH)
                            )
                        )
                    }
                )

                // ISBN
                AppEditField(
                    label = stringResource(R.string.book_add_edit__label__book_isbn_text),
                    value = stateUi.book.isbn,
                    hint = stringResource(R.string.book_add_edit__hint__book_isbn_input_text),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { isbn ->
                        onEvent(
                            BookAddEditEvent.IsbnChanged(
                                isbn.trim().take(AppConstants.BOOK_ISBN_MAX_LENGTH)
                            )
                        )
                    }
                )

                // Description
                val descriptionMinLines = 5
                AppEditField(
                    label = stringResource(R.string.book_add_edit__label__book_description_text),
                    value = stateUi.book.description,
                    hint = stringResource(R.string.book_add_edit__hint__enter_book_description_text),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = false,
                    minLines = descriptionMinLines,
                    onValueChange = { description ->
                        onEvent(
                            BookAddEditEvent.DescriptionChanged(
                                description
                            )
                        )
                    }
                )
            }
        }
    }
}
