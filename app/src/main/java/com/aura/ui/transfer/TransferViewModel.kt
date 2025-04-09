package com.aura.ui.transfer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TransferViewModel : ViewModel() {

  // StateFlow for the recipient and amount input fields
  private val _recipient = MutableStateFlow("")
  private val _amount = MutableStateFlow("")

  private val _isTransferButtonEnabled = MutableStateFlow(false)
  val isTransferButtonEnabled: MutableStateFlow<Boolean> = _isTransferButtonEnabled


  fun updateRecipient(newRecipient: String) {
    _recipient.value = newRecipient
    checkFormValidity()
  }

  fun updateAmount(newAmount: String) {
    _amount.value = newAmount
    checkFormValidity()
  }

  // Check if both fields are filled to enable the transfer button
  private fun checkFormValidity() {
    _isTransferButtonEnabled.value = _recipient.value.isNotEmpty() && _amount.value.isNotEmpty()
  }
}