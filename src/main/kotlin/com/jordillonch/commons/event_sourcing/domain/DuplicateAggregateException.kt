package com.jordillonch.commons.event_sourcing.domain

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.commons.ddd.domain.DomainException

class DuplicateAggregateException(errorCode: String, errorMessage: String) : DomainException(errorCode, errorMessage) {
    companion object {
        fun appendingFirstEvent(aggregate: Aggregate, event: Event) =
                DuplicateAggregateException("aggregate already exists", "Aggregate already exists <$aggregate>")
    }
}