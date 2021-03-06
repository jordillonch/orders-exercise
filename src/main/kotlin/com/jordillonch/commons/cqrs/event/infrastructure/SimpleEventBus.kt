package com.jordillonch.commons.cqrs.event.infrastructure

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.commons.cqrs.event.domain.EventBus
import com.jordillonch.commons.cqrs.event.domain.EventHandler
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

// TODO: replace with other implementation like Axon Framework
class SimpleEventBus : EventBus {
    private val handlers: MutableMap<String, MutableList<EventHandler<Event>>> = mutableMapOf()

    override fun <C : Event> registerHandler(handler: EventHandler<C>) {
        @Suppress("UNCHECKED_CAST")
        handlers.getOrPut(classFrom(handler)) { mutableListOf() }
                .add(handler as EventHandler<Event>)
    }

    override fun publish(event: Event) {
        @Suppress("UNCHECKED_CAST")
        handlers[event::class.qualifiedName]
                ?.forEach { it.on(event) }
    }

    override fun publish(events: List<Event>) {
        events.forEach { publish(it) }
    }

    private fun <C : Event> classFrom(handler: EventHandler<C>) =
            handler.javaClass.kotlin
                    .declaredFunctions
                    .firstFunctionNamedOn()
                    .mapParameterTypes()
                    .first { it.isSubclassOf(Event::class) }
                    .qualifiedName!!

    private fun Collection<KFunction<*>>.firstFunctionNamedOn() = first { it.name == "on" }

    private fun KFunction<*>.mapParameterTypes() = parameters.map { it.type.jvmErasure }
}
