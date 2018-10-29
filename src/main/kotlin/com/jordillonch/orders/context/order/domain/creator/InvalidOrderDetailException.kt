package com.jordillonch.orders.context.order.domain.creator

import com.jordillonch.commons.ddd.domain.DomainException
import com.jordillonch.orders.context.order.domain.OrderDetail

class InvalidOrderDetailException(errorCode: String,
                                  errorMessage: String) : DomainException(errorCode, errorMessage) {
    companion object {
        fun badLineNumber(orderDetail: OrderDetail) =
                InvalidOrderDetailException("invalid order detail because invalid line number",
                                                                                     "Invalid order detail because invalid line number: <$orderDetail>")
    }
}
