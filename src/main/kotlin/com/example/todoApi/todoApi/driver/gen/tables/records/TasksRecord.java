/*
 * This file is generated by jOOQ.
 */
package com.example.todoApi.todoApi.driver.gen.tables.records;


import com.example.todoApi.todoApi.driver.gen.enums.Status;
import com.example.todoApi.todoApi.driver.gen.tables.Tasks;

import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TasksRecord extends UpdatableRecordImpl<TasksRecord> implements Record6<UUID, String, Status, String, String, Timestamp> {

    private static final long serialVersionUID = -1537318425;

    /**
     * Setter for <code>public.tasks.task_id</code>.
     */
    public void setTaskId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.tasks.task_id</code>.
     */
    public UUID getTaskId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.tasks.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.tasks.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.tasks.status</code>.
     */
    public void setStatus(Status value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.tasks.status</code>.
     */
    public Status getStatus() {
        return (Status) get(2);
    }

    /**
     * Setter for <code>public.tasks.discription</code>.
     */
    public void setDiscription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.tasks.discription</code>.
     */
    public String getDiscription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.tasks.created_by</code>.
     */
    public void setCreatedBy(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.tasks.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.tasks.created_at</code>.
     */
    public void setCreatedAt(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.tasks.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, String, Status, String, String, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<UUID, String, Status, String, String, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Tasks.TASKS.TASK_ID;
    }

    @Override
    public Field<String> field2() {
        return Tasks.TASKS.NAME;
    }

    @Override
    public Field<Status> field3() {
        return Tasks.TASKS.STATUS;
    }

    @Override
    public Field<String> field4() {
        return Tasks.TASKS.DISCRIPTION;
    }

    @Override
    public Field<String> field5() {
        return Tasks.TASKS.CREATED_BY;
    }

    @Override
    public Field<Timestamp> field6() {
        return Tasks.TASKS.CREATED_AT;
    }

    @Override
    public UUID component1() {
        return getTaskId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public Status component3() {
        return getStatus();
    }

    @Override
    public String component4() {
        return getDiscription();
    }

    @Override
    public String component5() {
        return getCreatedBy();
    }

    @Override
    public Timestamp component6() {
        return getCreatedAt();
    }

    @Override
    public UUID value1() {
        return getTaskId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public Status value3() {
        return getStatus();
    }

    @Override
    public String value4() {
        return getDiscription();
    }

    @Override
    public String value5() {
        return getCreatedBy();
    }

    @Override
    public Timestamp value6() {
        return getCreatedAt();
    }

    @Override
    public TasksRecord value1(UUID value) {
        setTaskId(value);
        return this;
    }

    @Override
    public TasksRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public TasksRecord value3(Status value) {
        setStatus(value);
        return this;
    }

    @Override
    public TasksRecord value4(String value) {
        setDiscription(value);
        return this;
    }

    @Override
    public TasksRecord value5(String value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    public TasksRecord value6(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public TasksRecord values(UUID value1, String value2, Status value3, String value4, String value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TasksRecord
     */
    public TasksRecord() {
        super(Tasks.TASKS);
    }

    /**
     * Create a detached, initialised TasksRecord
     */
    public TasksRecord(UUID taskId, String name, Status status, String discription, String createdBy, Timestamp createdAt) {
        super(Tasks.TASKS);

        set(0, taskId);
        set(1, name);
        set(2, status);
        set(3, discription);
        set(4, createdBy);
        set(5, createdAt);
    }
}