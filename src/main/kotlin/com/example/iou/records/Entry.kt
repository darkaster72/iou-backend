package com.example.iou.records

import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

interface IEntry {
    val id: String
    val source: String
    val destination: String
    val amount: Money
    val transactionType: TransactionType
    val dateTime: LocalDateTime
    val description: String
    var verified: Boolean
}

@Document(collection = "entries")
class Entry(
    override val source: String,
    override val destination: String,
    override val amount: Money,
    override val transactionType: TransactionType,
    override val dateTime: LocalDateTime,
    override val description: String,
) : IEntry {
    @Id
    override lateinit var id: String;
    override var verified: Boolean = false;
}