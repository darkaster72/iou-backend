package com.example.iou.records

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "entries")
class Entry(
    val source: String,
    val destination: String,
    val amount: BigDecimal,
    val currency: String,
    val transactionType: TransactionType,
    val dateTime: LocalDateTime,
    val description: String,
) {
    @Id
    lateinit var id: String;
    var verified: Boolean = false;
}