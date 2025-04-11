package com.aura.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TransferRequest(
  val sender: String,
  val recipient: String,
  val amount: Double
)

@Serializable
data class TransferResponse(
  val result: Boolean,
)