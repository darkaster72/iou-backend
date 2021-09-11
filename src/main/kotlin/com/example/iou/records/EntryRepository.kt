package com.example.iou.records

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface EntryRepository : ReactiveMongoRepository<Entry, String> {
    fun findAllBySource(source: String): Flux<IEntry>
}
