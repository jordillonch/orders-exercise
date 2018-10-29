package com.jordillonch.commons.spring

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.commons.cqrs.event.domain.EventBus
import com.jordillonch.commons.cqrs.event.domain.EventHandler
import javax.annotation.PostConstruct
import javax.inject.Inject

abstract class SpringEventHandler<in E : Event> : EventHandler<E> {
    @Inject
    private lateinit var eventBus: EventBus

    @PostConstruct
    private fun register() = eventBus.registerHandler(this)
}