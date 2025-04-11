package com.aura.ui.transfer

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aura.databinding.ActivityTransferBinding
import kotlinx.coroutines.launch

/**
 * The transfer activity for the app.
 */
class TransferActivity : AppCompatActivity()
{
  /**
   * The binding for the transfer layout.
   */
  private lateinit var binding: ActivityTransferBinding

  /**
   * The view model for the transfer activity.
   */
  private lateinit var transferViewModel: TransferViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Set up the binding
    binding = ActivityTransferBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Initialize the ViewModel
    transferViewModel = ViewModelProvider(this)[TransferViewModel::class.java]

    // Get the different elements of the screen
    val recipient = binding.recipient
    val amount = binding.amount
    val transferButton = binding.transfer
    val loading = binding.loading

    // Listen for changes in the recipient and amount fields
    recipient.addTextChangedListener { text ->
      transferViewModel.updateRecipient(text.toString())
    }
    amount.addTextChangedListener { text ->
      transferViewModel.updateAmount(text.toString())
    }

    //Collect and observe the isLoading flow
    lifecycleScope.launch {
      transferViewModel.isLoading.collect { isLoading ->
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
      }
    }

    // Collect and observe the isTransferButtonEnabled flow
    lifecycleScope.launch {
      transferViewModel.isTransferButtonEnabled.collect { isEnabled ->
        transferButton.isEnabled = isEnabled
      }
    }

    // Collect and observe errorMessage flow
    lifecycleScope.launch {
      transferViewModel.errorMessage.collect { errorMessage ->
        if (errorMessage != null) {
          Toast.makeText(this@TransferActivity, errorMessage, Toast.LENGTH_SHORT).show()
          transferViewModel.clearError()  // Clear after displaying the toast.
        }
      }
    }



    transferButton.setOnClickListener {
      Log.d("TransferActivity", intent.getStringExtra("userId").toString())
      transferViewModel.transfer(
        senderId = intent.getStringExtra("userId") ?: "",
        recipientId = recipient.text.toString(),
        amount = amount.text.toString().toDouble()
      )

      lifecycleScope.launch {
        transferViewModel.transferResult.collect { success ->
          if (success == true) {
            setResult(Activity.RESULT_OK)
            finish()
          }
        }
      }
    }
  }
}
