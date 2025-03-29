package com.aura.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

  // Holds the latest text for each field.
  val usernameFlow = MutableStateFlow("")
  val passwordFlow = MutableStateFlow("")

  // Instead of using combine(...), we do a single Boolean Flow
  val isFormValid = MutableStateFlow(false)

  val loginResult: MutableStateFlow<Boolean?> = MutableStateFlow(null)
  val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

  fun updateUsername(newUsername: String) {
    usernameFlow.value = newUsername
    checkFormValidity()
  }

  fun updatePassword(newPassword: String) {
    passwordFlow.value = newPassword
    checkFormValidity()
  }

  // Called every time the user updates username or password
  private fun checkFormValidity() {
    isFormValid.value =
      usernameFlow.value.isNotEmpty() &&
              passwordFlow.value.isNotEmpty()
  }

  fun login(username: String, password: String) {
    isLoading.value = true
    errorMessage.value = null

    if (username.isEmpty() || password.isEmpty()) {
      errorMessage.value = "Username and password cannot be empty"
      isLoading.value = false
      return
    }

    loginResult.value = true
    isLoading.value = false
  }
}