package com.example.iou.records

import org.javamoney.moneta.Money
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.money.Monetary

@Service
class EntryService(val entryRepository: EntryRepository) {

    fun addEntry(entryRequest: EntryRequest): Mono<Unit> {
        return getUserId()
            .map { createDoubleEntry(entryRequest, it); }
            .flatMap { entryRepository.insert(it).last().map { } }
    }

    private fun createDoubleEntry(entryRequest: EntryRequest, userId: String): List<Entry> {
        val currency = Monetary.getCurrency(entryRequest.currency)
        val money = Money.of(entryRequest.amount, currency)
        val firstEntry = Entry(
            source = userId,
            destination = entryRequest.destination,
            dateTime = entryRequest.dateTime,
            amount = money,
            description = entryRequest.description,
            transactionType = entryRequest.transactionType
        )
        val secondEntry = Entry(
            source = entryRequest.destination,
            destination = userId,
            dateTime = entryRequest.dateTime,
            amount = money,
            description = entryRequest.description,
            transactionType = entryRequest.transactionType.opposite()
        )
        return listOf(firstEntry, secondEntry)
    }

    fun getEntries(): Flux<EntryResponse> {
        return getUserId()
            .flatMapMany { entryRepository.findAllBySource(it) }
            .map { it.toResponse() }
    }

    fun getEntriesForDestination(destinationId: String): Flux<EntryResponse> {
        return getUserId()
            .flatMapMany { entryRepository.findAllBySourceAndDestination(it, destinationId) }
            .map { it.toResponse() }
    }

    fun getDebtorEntries(): Flux<EntryResponse> {
        return getUserId()
            .flatMapMany { entryRepository.findAllBySourceAndTransactionType(it, TransactionType.Debit) }
            .map { it.toResponse() }
    }

    fun getCreditorEntries(): Flux<EntryResponse> {
        return getUserId()
            .flatMapMany { entryRepository.findAllBySourceAndTransactionType(it, TransactionType.Credit) }
            .map { it.toResponse() }
    }

    private fun getUserId() = ReactiveSecurityContextHolder.getContext()
        .map { it.authentication.details as String }
}

fun Entry.toResponse(): EntryResponse = EntryResponse(
    source = source,
    destination = destination,
    amount = amount.number.doubleValueExact(),
    currency = amount.currency.currencyCode,
    transactionType = transactionType,
    dateTime = dateTime,
    description = description,
    verified = verified,
)

fun IEntry.toResponse(): EntryResponse = EntryResponse(
    source = source,
    destination = destination,
    amount = amount.number.doubleValueExact(),
    currency = amount.currency.currencyCode,
    transactionType = transactionType,
    dateTime = dateTime,
    description = description,
    verified = verified,
)