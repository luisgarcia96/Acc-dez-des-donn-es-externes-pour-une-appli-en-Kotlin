package com.aura.ui.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.remote.api.ApiService
import com.aura.data.repository.client
import com.aura.data.remote.model.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val apiService = ApiService(client)

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    // Function to fetch accounts using the userId
    fun fetchAccounts(userId: String) {
        viewModelScope.launch {
            try {
                // Clear previous error message
                _errorMessage.value = null
                _isLoading.value = true

                val fetchedAccounts = apiService.getAccountsById(userId)
                _accounts.value = fetchedAccounts
            } catch (e: Exception) {
                // Display a user-friendly message or the exception message
                _errorMessage.value = e.message ?: "Something went wrong."
            } finally {
                _isLoading.value = false
            }
        }
    }
}

