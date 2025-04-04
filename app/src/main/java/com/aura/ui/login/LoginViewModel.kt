package com.aura.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.remote.api.ApiService
import com.aura.data.repository.client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
  
  private val apiService = ApiService(
    client = client
  )

  //-------------- State Flow ------------------//
  val username = MutableStateFlow("")
  val password = MutableStateFlow("")

  val isFormValid = MutableStateFlow(false)

  val loginResult: MutableStateFlow<Boolean?> = MutableStateFlow(null)
  val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

  fun updateUsername(newUsername: String) {
    username.value = newUsername
    checkFormValidity()
  }

  fun updatePassword(newPassword: String) {
    password.value = newPassword
    checkFormValidity()
  }

  // Called every time the user updates username or password
  private fun checkFormValidity() {
    isFormValid.value =
      username.value.isNotEmpty() &&
              password.value.isNotEmpty()
  }

  fun login(username: String, password: String) {
    viewModelScope.launch {
      try {
        // Show loading indicator
        isLoading.value = true
        errorMessage.value = null

        // Make the API call
        val response = apiService.login(username, password)

        if (response.granted) {
          loginResult.value = true
        } else {
          loginResult.value = false
          errorMessage.value = "Wrong Credentials"
        }
      } catch (e: Exception) {
        Log.e("LoginViewModel", "Login failed", e)
        errorMessage.value = "Login failed: ${e.message}"
        loginResult.value = false
      } finally {
        // Hide loading indicator
        isLoading.value = false
      }
    }
  }
}