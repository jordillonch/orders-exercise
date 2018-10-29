package com.jordillonch.orders.context.order.acceptance

import com.jordillonch.commons.rest.RestAssuredTest.given
import com.jordillonch.commons.spring.AcceptanceTest
import com.jordillonch.orders.context.order.stub.OrderDetailLinesRestRequestStub
import com.jordillonch.orders.context.order.stub.OrderRestRequestStub
import org.junit.Test
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class OrderPostControllerTest : AcceptanceTest() {

    @Test
    fun `it should create a new order`() {
        val request = OrderRestRequestStub.random()
        given()
                .`when`()
                .body(objectMapper.writeValueAsString(request))
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(CREATED.value())
    }

    @Test
    fun `it should fail because bad line number in order detail`() {
        val request = OrderRestRequestStub
                .random(lines = listOf(OrderDetailLinesRestRequestStub.random(),
                                       OrderDetailLinesRestRequestStub.random(),
                                       OrderDetailLinesRestRequestStub.random()))
        given()
                .`when`()
                .body(objectMapper.writeValueAsString(request))
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(UNPROCESSABLE_ENTITY.value())
    }

    @Test
    fun `it should fail because duplicated order`() {
        val request = OrderRestRequestStub.random()
        given()
                .`when`()
                .body(objectMapper.writeValueAsString(request))
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(CREATED.value())
        given()
                .`when`()
                .body(objectMapper.writeValueAsString(request))
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(CONFLICT.value())
    }

    @Test
    fun `it should fails because invalid format`() {
        given()
                .`when`()
                .body("""{}""".trimMargin())
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value())
    }

    @Test
    fun `it should fails because invalid json`() {
        given()
                .`when`()
                .body("invalid json")
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value())
    }
}
