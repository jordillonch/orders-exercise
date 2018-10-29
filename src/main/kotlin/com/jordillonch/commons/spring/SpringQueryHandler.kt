package com.jordillonch.commons.spring

import com.jordillonch.commons.cqrs.query.domain.Query
import com.jordillonch.commons.cqrs.query.domain.QueryBus
import com.jordillonch.commons.cqrs.query.domain.QueryHandler
import javax.annotation.PostConstruct
import javax.inject.Inject

abstract class SpringQueryHandler<in Q : Query, R> : QueryHandler<Q, R> {
    @Inject
    private lateinit var queryBus: QueryBus

    @PostConstruct
    private fun register() = queryBus.registerHandler(this)
}