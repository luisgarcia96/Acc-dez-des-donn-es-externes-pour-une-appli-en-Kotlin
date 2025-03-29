package com.aura.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aura.databinding.ActivityLoginBinding
import com.aura.ui.home.HomeActivity
import kotlinx.coroutines.launch

/**
 * The login activity for the app.
 */
class LoginActivity : AppCompatActivity() {

  /**
   * The binding for the login layout.
   */
  private lateinit var binding: ActivityLoginBinding
  private lateinit var viewModel: LoginViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Initialize view model
    viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    //---------------------- COLLECT FLOW ---------------------- //
    // Observe loading state
    lifecycleScope.launch {
      viewModel.isLoading.collect { isLoading ->
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
      }
    }

    // Observe login result
    lifecycleScope.launch {
      viewModel.loginResult.collect { success ->
        if (success == true) {
          val intent = Intent(this@LoginActivity, HomeActivity::class.java)
          startActivity(intent)
          finish()
        }
      }
    }

    // Observe error messages
    lifecycleScope.launch {
      viewModel.errorMessage.collect { errorMsg ->
        if (errorMsg != null) {
          Toast.makeText(this@LoginActivity, errorMsg, Toast.LENGTH_SHORT).show()
        }
      }
    }

    // Observe form validity to enable or disable the login button
    lifecycleScope.launch {
      viewModel.isFormValid.collect { valid ->
        binding.login.isEnabled = valid
      }
    }

    //---------------------- UI ELEMENTS ---------------------- //
    val loginButton = binding.login
    val usernameInput = binding.identifier
    val passwordInput = binding.password

    //---------------------- EVENT LISTENERS ---------------------- //
    // Update the ViewModel whenever the text changes
    usernameInput.doOnTextChanged { text, _, _, _ ->
      viewModel.updateUsername(text.toString())
    }
    passwordInput.doOnTextChanged { text, _, _, _ ->
      viewModel.updatePassword(text.toString())
    }

    // Listen for login attempts
    loginButton.setOnClickListener {
      val username = usernameInput.text.toString()
      val password = passwordInput.text.toString()
      viewModel.login(username, password)
    }
  }
}