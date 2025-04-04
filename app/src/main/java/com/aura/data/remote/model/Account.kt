package com.aura.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
  val id: String,
  val main: Boolean,
  val balance: Float
)
