package com.example.iou.records

enum class TransactionType(type: String) {
    Debit("Debit"),
    Credit("Credit");

    fun opposite(): TransactionType {
        return if (this == Debit) Credit
        else Debit;
    }
}
