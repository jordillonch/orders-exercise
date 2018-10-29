package com.jordillonch.commons.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import io.restassured.RestAssured
import io.restassured.config.DecoderConfig
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig.logConfig
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RedirectConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.config.SSLConfig
import io.restassured.filter.log.LogDetail
import io.restassured.parsing.Parser
import io.restassured.specification.RequestSpecification

object RestAssuredTest {
    fun config(serverPort: Int) {
        RestAssured.port = serverPort
        RestAssured.defaultParser = Parser.JSON
        RestAssured.config = RestAssuredConfig.config()
                .redirect(RedirectConfig.redirectConfig().followRedirects(false))
                .encoderConfig(
                        EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
                .decoderConfig(DecoderConfig.decoderConfig().noContentDecoders())
                .objectMapperConfig(
                        ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory { cls, charset ->
                            ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                        })
                .sslConfig(SSLConfig().relaxedHTTPSValidation())
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
    }

    fun given(): RequestSpecification {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
    }
}
