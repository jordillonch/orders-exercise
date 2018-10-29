package com.jordillonch.orders.context.order.adapter.controller.create

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.jordillonch.commons.cqrs.command.domain.CommandBus
import com.jordillonch.commons.event_sourcing.domain.DuplicateAggregateException
import com.jordillonch.orders.context.order.domain.creator.InvalidOrderDetailException
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
class OrderPostController(private val commandBus: CommandBus) {

    @PostMapping("/orders")
    fun put(@RequestBody @Valid request: OrderRestRequest): ResponseEntity<Unit> {
        commandBus.handle(request.toCommand())

        return ResponseEntity.created(URI("/orders/${request.order.id}")).build()
    }

    @ExceptionHandler(InvalidOrderDetailException::class)
    @ResponseStatus(code = UNPROCESSABLE_ENTITY)
    fun futureTransactionExceptionHandler(exception: InvalidOrderDetailException) {
    }

    @ExceptionHandler(DuplicateAggregateException::class)
    @ResponseStatus(code = CONFLICT)
    fun futureTransactionExceptionHandler(exception: DuplicateAggregateException) {
    }

    @ExceptionHandler(InvalidFormatException::class)
    @ResponseStatus(code = UNPROCESSABLE_ENTITY)
    fun invalidFormatExceptionHandler(exception: InvalidFormatException) {
    }
}