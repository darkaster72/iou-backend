package com.example.iou.records

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EntryRepository : ReactiveMongoRepository<Entry, String> {

}
