package com.example.iou.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate


@Configuration
class MongoDbConfig {
    @Bean
    fun reactiveMongoClient(): MongoClient? {
        return MongoClients.create("mongodb://localhost")
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient()!!, "test")
    }
}