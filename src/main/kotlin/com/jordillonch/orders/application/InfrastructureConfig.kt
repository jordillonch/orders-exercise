package com.jordillonch.orders.application

import com.jordillonch.commons.cqrs.command.domain.CommandBus
import com.jordillonch.commons.cqrs.command.infrastructure.SimpleCommandBus
import com.jordillonch.commons.cqrs.event.domain.EventBus
import com.jordillonch.commons.cqrs.event.infrastructure.SimpleEventBus
import com.jordillonch.commons.cqrs.query.domain.QueryBus
import com.jordillonch.commons.cqrs.query.infrastructure.SimpleQueryBus
import com.jordillonch.commons.event_sourcing.domain.EventStore
import com.jordillonch.commons.event_sourcing.infrastructure.InMemoryEventStoreWithPublishToEventBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InfrastructureConfig {

    @Bean
    fun cqrsCommandBus(): CommandBus = SimpleCommandBus()

    @Bean
    fun cqrsQueryBus(): QueryBus = SimpleQueryBus()

    @Bean
    fun cqrsEventBus(): EventBus = SimpleEventBus()

    @Bean
    fun eventStore(eventBus: EventBus): EventStore = InMemoryEventStoreWithPublishToEventBus(eventBus)
}