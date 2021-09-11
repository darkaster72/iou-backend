package com.example.iou.records

import org.javamoney.moneta.Money
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.money.Monetary

@Service
class EntryService(val entryRepository: EntryRepository) {

    fun addEntry(entryRequest: EntryRequest): Mono<Unit> {
        return ReactiveSecurityContextHolder.getContext()
            .map { createDoubleEntry(entryRequest, it.authentication.details as String); }
            .flatMap { entryRepository.insert(it).last().map { } }
    }

    private fun createDoubleEntry(entryRequest: EntryRequest, userId: String): List<Entry> {
        val currency = Monetary.getCurrency(entryRequest.currency);
        val money = Money.of(entryRequest.amount, currency)
        val firstEntry = Entry(
            source = userId,
            destination = entryRequest.destination,
            dateTime = entryRequest.dateTime,
            amount = money.numberStripped,
            currency = money.currency.currencyCode,
            description = entryRequest.description,
            transactionType = entryRequest.transactionType
        )
        val secondEntry = Entry(
            source = entryRequest.destination,
            destination = userId,
            dateTime = entryRequest.dateTime,
            amount = money.numberStripped,
            currency = money.currency.currencyCode,
            description = entryRequest.description,
            transactionType = entryRequest.transactionType.opposite()
        )
        return listOf(firstEntry, secondEntry);
    }

}
