package com.aura.ui.transfer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.remote.api.ApiService
import com.aura.data.repository.client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TransferViewModel : ViewModel() {

  private val apiService = ApiService(
    client = client
  )

  //-------------- State Flow ------------------//
  private val _recipient = MutableStateFlow("")
  val recipient: MutableStateFlow<String> = _recipient

  private val _amount = MutableStateFlow("")
  val amount: MutableStateFlow<String> = _amount

  private val _isTransferButtonEnabled = MutableStateFlow(false)
  val isTransferButtonEnabled: MutableStateFlow<Boolean> = _isTransferButtonEnabled

  private val _isLoading = MutableStateFlow(false)
  val isLoading: MutableStateFlow<Boolean> = _isLoading

  private val _transferResult: MutableStateFlow<Boolean?> = MutableStateFlow(null)
  val transferResult: MutableStateFlow<Boolean?> = _transferResult

  private val _errorMessage = MutableStateFlow<String?>(null)
  val errorMessage: MutableStateFlow<String?> = _errorMessage


  fun updateRecipient(newRecipient: String) {
    _recipient.value = newRecipient
    checkFormValidity()
  }

  fun updateAmount(newAmount: String) {
    _amount.value = newAmount
    checkFormValidity()
  }

  fun clearError() {
    _errorMessage.value = null
  }

  //Transfer function
  fun transfer(senderId: String, recipientId: String, amount: Double) {
    Log.d("TransferViewModel", "Transferring $amount from $senderId to $recipientId")
    viewModelScope.launch {
      try {
        // Show loading indicator
        _isLoading.value = true
        _errorMessage.value = null

        //If the amount is negative, show an error message
        if (amount <= 0) {
          _errorMessage.value = "Amount cannot be negative or zero"
          return@launch
        }

        //If recipient is same as sender, show an error message
        if (recipientId == senderId) {
          _errorMessage.value = "You cannot execute a transfer to this same account"
          return@launch
        }

        // Make the API call
        val response = apiService.transfer(senderId, recipientId, amount)
        if (!response.result) {
          _transferResult.value = false
          _errorMessage.value = "Transfer failed: Check your balance"
          return@launch
        }
        _transferResult.value = response.result

        // Handle the response
      } catch (e: Exception) {
        _errorMessage.value = "Transfer failed: ${e.message}"
      } finally {
        // Hide loading indicator
        _isLoading.value = false
      }
    }
  }

  // Check if both fields are filled to enable the transfer button
  private fun checkFormValidity() {
    _isTransferButtonEnabled.value = _recipient.value.isNotEmpty() && _amount.value.isNotEmpty()
  }
}