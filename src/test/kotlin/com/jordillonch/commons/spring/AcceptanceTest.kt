package com.jordillonch.commons.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.jordillonch.commons.rest.RestAssuredTest
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTest {
    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @LocalServerPort
    private val port: Int = 0

    @Before
    fun setup() {
        RestAssuredTest.config(port)
    }
}