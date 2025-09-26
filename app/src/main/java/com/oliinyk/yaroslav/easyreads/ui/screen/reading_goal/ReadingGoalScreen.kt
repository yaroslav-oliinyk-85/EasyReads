package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.oliinyk.yaroslav.easyreads.presentation.reading_goal.ReadingGoalViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.FinishedBooksCard
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingGoalCard
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingSummaryCard
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingGoalScreen(
    modifier: Modifier = Modifier,
    viewModel: ReadingGoalViewModel,
    onBookClick: (Book) -> Unit,
    onChangeGoalClick: () -> Unit
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()
    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.reading_goal__toolbar__title_text,
                            SimpleDateFormat(
                                stringResource(R.string.date_year_format),
                                Locale.getDefault()
                            ).format(Date())
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = Dimens.paddingHorizontalMedium,
                        vertical = Dimens.paddingVerticalSmall
                    )
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ReadingGoalCard(
                    modifier = modifier,
                    stateUi = stateUi,
                    onChangeGoalClick = onChangeGoalClick
                )
                Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
                ReadingSummaryCard(
                    modifier = modifier,
                    stateUi = stateUi
                )
                Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
                FinishedBooksCard(
                    modifier = modifier,
                    books = stateUi.books,
                    onBookClick = onBookClick
                )
            }
        }
    )
}
