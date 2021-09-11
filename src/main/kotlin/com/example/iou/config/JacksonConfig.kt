package com.example.iou.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.jackson.datatype.money.MoneyModule


@Configuration
class JacksonConfig {
    @Bean
    fun moneyModule(): MoneyModule? { // TODO: return Module
        return MoneyModule().withMoney()
    }
}