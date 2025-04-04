package com.aura.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aura.R
import com.aura.databinding.ActivityHomeBinding
import com.aura.ui.login.LoginActivity
import com.aura.ui.transfer.TransferActivity
import kotlinx.coroutines.launch

/**
 * The home activity for the app.
 */
class HomeActivity : AppCompatActivity()
{

  /**
   * The binding for the home layout.
   */
  private lateinit var binding: ActivityHomeBinding

  /**
   * The view model for the home activity.
   */
  private lateinit var homeViewModel: HomeViewModel

  /**
   * A callback for the result of starting the TransferActivity.
   */
  private val startTransferActivityForResult =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
      //TODO
    }

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    //UserId passed from the login screen
    val userId = intent.getStringExtra("userId") ?: return

    binding = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val balance = binding.balance
    val transfer = binding.transfer

    //Initialize the ViewModel
    homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

    // Fetch accounts with the userId
    homeViewModel.fetchAccounts(userId)

    // Collect and observe the accounts
    lifecycleScope.launch {
        homeViewModel.accounts.collect { accounts ->
            if (accounts.isNotEmpty()) {
              val mainAccount = accounts.find { it.main }
              if (mainAccount != null) {
                balance.text = "${mainAccount.balance}€"
              }
            } else {
                balance.text = "No accounts available"
            }
        }
    }

    // Collect and observe the loading state
// Collect and observe the loading state
    lifecycleScope.launch {
      homeViewModel.isLoading.collect { loading ->
        binding.loading.visibility = if (loading) View.VISIBLE else View.GONE

        // Hide the balance text (or “No accounts available”) while loading
        binding.title.visibility = if (loading) View.GONE else View.VISIBLE
        binding.balance.visibility = if (loading) View.GONE else View.VISIBLE
        binding.transfer.visibility = if (loading) View.GONE else View.VISIBLE
      }
    }

    // Collect and observe the errorMessage
    lifecycleScope.launch {
      homeViewModel.errorMessage.collect { errorMsg ->
        if (errorMsg != null) {
          binding.title.visibility = View.GONE
          binding.balance.visibility = View.GONE
          binding.errorMessage.text = errorMsg
          binding.errorMessage.visibility = View.VISIBLE
          binding.buttonRetry.visibility = View.VISIBLE
        } else {
          binding.errorMessage.visibility = View.GONE
          binding.buttonRetry.visibility = View.GONE
        }
      }
    }

// Handle the retry button click
    binding.buttonRetry.setOnClickListener {
      homeViewModel.fetchAccounts(userId)
    }

    // Removed hard-coded balance text
    transfer.setOnClickListener {
      startTransferActivityForResult.launch(Intent(this@HomeActivity, TransferActivity::class.java))
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean
  {
    menuInflater.inflate(R.menu.home_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean
  {
    return when (item.itemId)
    {
      R.id.disconnect ->
      {
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        finish()
        true
      }
      else            -> super.onOptionsItemSelected(item)
    }
  }

}
