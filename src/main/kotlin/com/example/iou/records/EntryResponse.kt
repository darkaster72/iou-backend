package com.example.iou.records

import java.time.LocalDateTime

data class EntryResponse(
    val source: String,
    val destination: String,
    val amount: Number,
    val currency: String,
    val transactionType: TransactionType,
    val dateTime: LocalDateTime,
    val description: String,
    val verified: Boolean,
)