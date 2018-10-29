package com.jordillonch.orders.context.notification.email.domain.sender

import com.jordillonch.orders.context.notification.email.domain.EmailSender
import com.jordillonch.orders.context.order.domain.OrderId
import org.springframework.stereotype.Service

// TODO
@Service
class Sender(private val emailSender: EmailSender) {

    operator fun invoke(orderId: OrderId) {
        // TODO find email and compose the body
        emailSender.send()
    }
}

