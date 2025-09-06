package com.bank.transfer.presentation.viewmodel


import app.cash.turbine.test
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.presentation.navigation.TransferTypeNavigationEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class TransferTypeViewModelTest {
    private lateinit var viewModel: TransferTypeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TransferTypeViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onDomesticTypeSelected emits NavigateToPayment with DOMESTIC type`() = runTest {
        viewModel.navigationEvent.test {
            viewModel.onDomesticTypeSelected()

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(
                TransferTypeNavigationEvent
                    .NavigateToPayment(TransferType.DOMESTIC),
                awaitItem()
            )

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onInternationalTypeSelected emits NavigateToPayment with INTERNATIONAL type`() = runTest {
        viewModel.navigationEvent.test {
            viewModel.onInternationalTypeSelected()

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(
                TransferTypeNavigationEvent
                    .NavigateToPayment(TransferType.INTERNATIONAL),
                awaitItem()
            )

            cancelAndConsumeRemainingEvents()
        }
    }
}
