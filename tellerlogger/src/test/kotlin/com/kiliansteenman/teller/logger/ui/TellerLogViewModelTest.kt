package com.kiliansteenman.teller.logger.ui

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.ui.overview.OverViewState
import com.kiliansteenman.teller.logger.ui.overview.TellerLogViewModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
internal class TellerLogViewModelTest {

    private val testLog = TellerLog(
        framework = "hello",
        type = "screenview",
        title = "title",
        params = mapOf("key" to "value")
    )

    private fun viewModel(repository: FakeTellerLogRepository = FakeTellerLogRepository()): TellerLogViewModel =
        TellerLogViewModel(repository)

    @Test
    fun `loading state is emitted`() = runTest {
        viewModel().state.test {
            assertEquals(OverViewState.Loading, awaitItem())
        }
    }

    @Test
    fun `when there are logs, then logs are displayed`() = runTest {
        val viewModel = viewModel(FakeTellerLogRepository(listOf(testLog)))
        viewModel.state.test {
            awaitLoadingState()

            assertEquals(listOf(testLog), awaitContentStateItem().logs)
        }
    }

    @Test
    fun `when there are NO logs, then empty state is displayed`() = runTest {
        val viewModel = viewModel(FakeTellerLogRepository(emptyList()))

        viewModel.state.test {
            awaitLoadingState()

            awaitErrorStateItem()
        }
    }

    @Nested
    @DisplayName("when clear logs is pressed")
    inner class ClearLogsTest {

        @Test
        fun `then empty state is displayed`() = runTest {
            val viewModel = viewModel(FakeTellerLogRepository(listOf(testLog)))

            viewModel.state.test {
                awaitLoadingState()

                assertEquals(listOf(testLog), awaitContentStateItem().logs)

                viewModel.onClearLogClicked()

                awaitErrorStateItem()
            }
        }

        @Test
        fun `then logs are cleared from repository`() {
            val repository = FakeTellerLogRepository(emptyList())
            val viewModel = viewModel(repository)

            viewModel.onClearLogClicked()

            assertEquals(1, repository.clearCount)
        }
    }

    @Nested
    @DisplayName("when searching")
    inner class SearchTest {

        private lateinit var viewModel: TellerLogViewModel

        @BeforeEach
        fun beforeEach() {
            viewModel = viewModel(
                FakeTellerLogRepository(
                    listOf(
                        testLog.copy(params = mapOf("position" to "first item")),
                        testLog.copy(params = mapOf("position" to "second item")),
                        testLog.copy(params = mapOf("position" to "third item"))
                    )
                )
            )
        }

        @Test
        fun `then results are filtered`() = runTest {
            viewModel.state.test {
                awaitLoadingState()
                assertEquals(3, awaitContentStateItem().logs.size)

                viewModel.onQueryChanged("second")

                assertEquals(1, awaitContentStateItem().logs.size)
            }
        }

        @Test
        fun `and query is null, then all logs are returned`() = runTest {
            viewModel.state.test {
                awaitLoadingState()
                assertEquals(3, awaitContentStateItem().logs.size)

                viewModel.onQueryChanged("second")
                assertEquals(1, awaitContentStateItem().logs.size)

                viewModel.onQueryChanged(null)
                assertEquals(3, awaitContentStateItem().logs.size)
            }
        }
    }

    private suspend fun FlowTurbine<OverViewState>.awaitLoadingState(): OverViewState.Loading {
        val state = awaitItem()
        assertTrue { state is OverViewState.Loading }
        return state as OverViewState.Loading
    }

    private suspend fun FlowTurbine<OverViewState>.awaitContentStateItem(): OverViewState.Content {
        val contentState = awaitItem()
        assertTrue { contentState is OverViewState.Content }
        return contentState as OverViewState.Content
    }

    private suspend fun FlowTurbine<OverViewState>.awaitErrorStateItem(): OverViewState.Error {
        val contentState = awaitItem()
        assertTrue { contentState is OverViewState.Error }
        return contentState as OverViewState.Error
    }
}