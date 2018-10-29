package com.jordillonch.commons.faker

import com.github.javafaker.Faker as JavaFaker

object Faker {
    private val faker = JavaFaker()

    fun instance() = faker
}