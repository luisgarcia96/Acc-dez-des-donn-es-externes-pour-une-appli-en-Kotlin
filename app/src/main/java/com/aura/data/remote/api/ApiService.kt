package com.aura.data.remote.api

import com.aura.data.remote.model.Account
import com.aura.data.remote.model.LoginRequest
import com.aura.data.remote.model.LoginResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService(private val client: HttpClient) {

  //Base URL for the API
  private val baseUrl = "http://10.0.2.2:8080"

  /**
   * Login to the API.
   */
  suspend fun login(id: String, password: String): LoginResponse {
    return client.post("$baseUrl/login") {
      contentType(ContentType.Application.Json)
      setBody(LoginRequest(id, password))
    }.body()
  }

  /**
   * Get the user's accounts
   */
  suspend fun getAccountsById(id: String): List<Account> {
    return client.get("$baseUrl/accounts/$id").body()
  }
}