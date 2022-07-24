package com.example.todoApi.todoApi.driver

import com.example.todoApi.todoApi.gateway.TaskRecord
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.util.*


@Component
class TaskDbDriver(private val dsl: DSLContext = DSL.using(DatabaseConfig().getConnection(), SQLDialect.POSTGRES)) {

    fun create(taskRecord: TaskRecord) : UUID = TODO()
    fun findById(taskId: UUID): TaskRecord = TODO()
}