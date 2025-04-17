package com.aura

import com.aura.ui.login.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * LoginViewModel unit tests.
 *
 */
class LoginViewModelTest {

private val viewModel = LoginViewModel()

  @Test
  fun testUpdateUsername() {
    viewModel.updateUsername("testUser")
    assertEquals("testUser", viewModel.username.value)
  }

  @Test
  fun testUpdatePassword() {
    viewModel.updatePassword("testPassword")
    assertEquals("testPassword", viewModel.password.value)
  }

  @Test
  fun testCheckFormValidity() {
    viewModel.updateUsername("testUser")
    viewModel.updatePassword("testPassword")
    assertEquals(true, viewModel.isFormValid.value)
  }

  @Test
  fun testCheckFormValidityEmptyUsername() {
    viewModel.updatePassword("testPassword")
    assertEquals(false, viewModel.isFormValid.value)
  }

    @Test
  fun testCheckFormValidityEmptyPassword() {
      viewModel.updateUsername("testUser")
      assertEquals(false, viewModel.isFormValid.value)
    }
}