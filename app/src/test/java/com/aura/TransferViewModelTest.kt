package com.aura

import com.aura.ui.transfer.TransferViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * LoginViewModel unit tests.
 *
 */
class TransferViewModelTest
{
  private val viewModel = TransferViewModel()

  @Test
  fun testUpdateRecipient() {
    viewModel.updateRecipient("testRecipient")
    assertEquals("testRecipient", viewModel.recipient.value)
  }

  @Test
  fun testUpdateAmount() {
    viewModel.updateAmount("100.0")
    assertEquals("100.0", viewModel.amount.value)
  }

  @Test
  fun testCheckFormValidityEmptyRecipient() {
    viewModel.updateAmount("100.0")
    assertEquals(false, viewModel.isTransferButtonEnabled.value)
  }

  @Test
  fun testCheckFormValidityEmptyAmount() {
    viewModel.updateRecipient("testRecipient")
    assertEquals(false, viewModel.isTransferButtonEnabled.value)
  }

  @Test
  fun testCheckFormValidity() {
    viewModel.updateRecipient("testRecipient")
    viewModel.updateAmount("100.0")
    assertEquals(true, viewModel.isTransferButtonEnabled.value)
    }
}