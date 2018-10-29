package com.jordillonch.commons.cqrs.query.domain

interface Query

interface QueryHandler<in Q : Query, R> {
    fun on(query: Q): R
}

interface QueryBus {
    fun <Q : Query> registerHandler(handler: QueryHandler<Q, *>)
    fun <R> ask(query: Query): R
}

class NoQueryHandlerFoundException : RuntimeException()