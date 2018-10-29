package com.jordillonch.commons.event_sourcing.domain

interface AggregateId {
    fun id(): String
}