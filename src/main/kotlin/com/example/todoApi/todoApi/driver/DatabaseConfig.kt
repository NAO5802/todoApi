package com.example.todoApi.todoApi.driver

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import java.sql.Connection
import java.sql.DriverManager

class DatabaseConfig() {
    @Autowired
    lateinit var environment: Environment

    private val url = environment.getProperty("spring.datasource.url")
    private val username = environment.getProperty("spring.datasource.username")
    private val password = environment.getProperty("spring.datasource.password")

    fun getConnection(): Connection = DriverManager.getConnection(url, username, password)

}