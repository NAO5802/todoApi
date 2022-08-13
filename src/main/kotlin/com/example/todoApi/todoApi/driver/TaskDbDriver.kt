package com.example.todoApi.todoApi.driver

import com.example.todoApi.todoApi.domain.TaskOrderKey
import com.example.todoApi.todoApi.driver.gen.Tables.TASKS
import com.example.todoApi.todoApi.driver.gen.tables.records.TasksRecord
import com.example.todoApi.todoApi.gateway.TaskRecord
import com.example.todoApi.todoApi.gateway.TaskUpdateRecord
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.SortField
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.util.*


@Component
class TaskDbDriver(private val dsl: DSLContext = DSL.using(DatabaseConfig().getConnection(), SQLDialect.POSTGRES)) {

    fun create(record: TaskRecord): UUID? =
        dsl.insertInto(TASKS)
            .set(TASKS.TASK_ID, record.id)
            .set(TASKS.NAME, record.name)
            .set(TASKS.STATUS, record.status)
            .set(TASKS.DISCRIPTION, record.description)
            .set(TASKS.CREATED_BY, record.createdBy)
            .set(TASKS.CREATED_AT, record.createdAt)
            .returningResult(TASKS.TASK_ID)
            .fetchOne()
            ?.getValue(TASKS.TASK_ID)


    fun findById(taskId: UUID): TaskRecord? =
        dsl.selectFrom(TASKS)
            .where(TASKS.TASK_ID.eq(taskId))
            .fetchOne()
            ?.toTaskRecord()

    fun delete(taskId: UUID): UUID? =
        dsl.deleteFrom(TASKS)
            .where(TASKS.TASK_ID.eq(taskId))
            .returningResult(TASKS.TASK_ID)
            .fetchOne()
            ?.getValue(TASKS.TASK_ID)

    fun findAllWithSorted(orderKey: TaskOrderKey): List<TaskRecord> =
        dsl.selectFrom(TASKS)
            .orderBy(getTaskSortField(orderKey))
            .fetch()
            .map { it.toTaskRecord() }

    fun update(record: TaskUpdateRecord): UUID? =
        dsl.update(TASKS)
            .set(TASKS.NAME, record.name)
            .set(TASKS.STATUS, record.status)
            .set(TASKS.DISCRIPTION, record.description)
            .where(TASKS.TASK_ID.eq(record.id))
            .returningResult(TASKS.TASK_ID)
            .fetchOne()
            ?.getValue(TASKS.TASK_ID)


    private fun getTaskSortField(orderKey: TaskOrderKey): SortField<Timestamp> =
        when(orderKey){
            TaskOrderKey.CREATED_AT -> TASKS.CREATED_AT.desc()
        }

}

// jooqの自動生成レコードをTaskRecordに変換する
fun TasksRecord.toTaskRecord(): TaskRecord =
    TaskRecord(
        this.get(TASKS.TASK_ID),
        this.get(TASKS.NAME),
        this.get(TASKS.STATUS),
        this.get(TASKS.DISCRIPTION),
        this.get(TASKS.CREATED_BY),
        this.get(TASKS.CREATED_AT),
    )