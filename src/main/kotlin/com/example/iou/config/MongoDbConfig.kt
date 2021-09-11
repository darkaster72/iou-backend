package com.example.iou.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.javamoney.moneta.Money
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.CustomConversions
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions


@Configuration
class MongoDbConfig {
    @Bean
    fun reactiveMongoClient(): MongoClient? {
        return MongoClients.create("mongodb://localhost")
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        val reactiveMongoTemplate = ReactiveMongoTemplate(reactiveMongoClient()!!, "test")
        val conv = reactiveMongoTemplate.converter as MappingMongoConverter;
        // tell mongodb to use the custom converters
        conv.setCustomConversions(mongoCustomConversions())
        conv.afterPropertiesSet()
        return reactiveMongoTemplate
    }

    fun mongoCustomConversions(): CustomConversions {
        val converters: MutableList<Converter<*, *>?> = ArrayList()
        converters.add(MoneyReadConverter())
        converters.add(MoneyWriteConverter())
        return MongoCustomConversions(converters)
    }
}

@ReadingConverter
class MoneyReadConverter : Converter<String, Money?> {
    override fun convert(moneyString: String): Money? = Money.parse(moneyString)
}

@WritingConverter
class MoneyWriteConverter : Converter<Money, String> {
    override fun convert(money: Money): String = money.toString()
}