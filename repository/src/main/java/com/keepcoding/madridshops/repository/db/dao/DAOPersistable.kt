package com.keepcoding.madridshops.repository.db.dao

import android.database.Cursor

interface DAOReadOperations<T> {
    fun query(id: Long, table: String): T
    fun query(table: String): List<T>
    fun queryCursor(id: Long, table: String): Cursor
}

interface DAOWriteOperations<T> {
    fun insert(element: T, table: String): Long
    fun update(id: Long, element: T, table: String): Long

    /**
     * deletes the element passed from DB
     */
    fun delete(element: T, table: String): Long

    /**
     * deletes the element with id from DB
     */
    fun delete(id: Long, table: String): Long
    fun deleteAll(table: String): Boolean
}

interface DAOPersistable<T>: DAOReadOperations<T>, DAOWriteOperations<T>

