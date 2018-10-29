package com.jordillonch.orders.application

import com.jordillonch.commons.event_sourcing.domain.AggregateLifeCycle
import com.jordillonch.commons.event_sourcing.domain.EventStore
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class InfrastructureBootstrap(private val eventStore: EventStore) {
    @PostConstruct
    fun initAggregateLifeCycle() {
        AggregateLifeCycle.init(eventStore)
    }
}