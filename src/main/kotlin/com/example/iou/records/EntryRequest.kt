package com.example.iou.records

import java.time.LocalDateTime

data class EntryRequest(
    val destination: String,
    val amount: Number,
    val currency: String,
    val transactionType: TransactionType,
    val dateTime: LocalDateTime,
    val description: String,
);
