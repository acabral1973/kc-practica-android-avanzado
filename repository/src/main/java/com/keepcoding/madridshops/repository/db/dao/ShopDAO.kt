package com.keepcoding.madridshops.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.keepcoding.madridshops.repository.db.DBConstants
import com.keepcoding.madridshops.repository.db.DBHelper
import com.keepcoding.madridshops.repository.model.DataEntity

class ShopDAO
(
        val dbHelper: DBHelper
): DAOPersistable<DataEntity>
{
    private val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase


    override fun insert(element: DataEntity, table: String): Long {
        var id: Long = 0

        id = dbReadWriteConnection.insert(table, null, contentValues(element))

        return id
    }

    fun contentValues(dataEntity: DataEntity): ContentValues {
        val content = ContentValues()

        content.put(DBConstants.KEY_SHOP_ID_JSON, dataEntity.id)
        content.put(DBConstants.KEY_SHOP_NAME , dataEntity.name)
        content.put(DBConstants.KEY_SHOP_DESCRIPTION , dataEntity.description_en)
        content.put(DBConstants.KEY_SHOP_LATITUDE, dataEntity.gps_lat)
        content.put(DBConstants.KEY_SHOP_LONGITUDE, dataEntity.gps_lon)
        content.put(DBConstants.KEY_SHOP_IMAGE_URL , dataEntity.img)
        content.put(DBConstants.KEY_SHOP_LOGO_IMAGE_URL , dataEntity.logo_img)
        content.put(DBConstants.KEY_SHOP_ADDRESS , dataEntity.address)
        content.put(DBConstants.KEY_SHOP_OPENING_HOURS , dataEntity.opening_hours_en)

        return content
    }

    override fun delete(element: DataEntity, table: String): Long {
        if (element.databaseId < 1) {
            return 0
        }

        return delete(element.databaseId, table)
    }

    override fun delete(id: Long, table: String): Long {
        return dbReadWriteConnection.delete(
                table,
                DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                arrayOf(id.toString())
        ).toLong()
    }

    override fun deleteAll(table: String): Boolean {
        return dbReadWriteConnection.delete(
                table,
                null,
                null
        ).toLong() > 0
    }

    override fun query(id: Long, table: String): DataEntity {
        val cursor = queryCursor(id, table)

        cursor.moveToFirst()

        return entityFromCursor(cursor)!!
    }

    override fun query(table: String): List<DataEntity> {
        val queryResult = ArrayList<DataEntity>()

        val cursor = dbReadOnlyConnection.query(
                table,
                DBConstants.ALL_COLUMNS,
                null,
                null,
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID)

        while (cursor.moveToNext()) {
            val se = entityFromCursor(cursor)
            queryResult.add(se!!)
        }

        return queryResult
    }

    fun entityFromCursor(cursor: Cursor): DataEntity? {
        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        return DataEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_ADDRESS)))
    }

    override fun queryCursor(id: Long, table: String): Cursor {
        val cursor = dbReadOnlyConnection.query(table,
                DBConstants.ALL_COLUMNS,
                DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                arrayOf(id.toString()),
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID
        )
        return cursor
    }

    override fun update(id: Long, element: DataEntity, table: String): Long {
        val numberOfRecordsUpdated = dbReadWriteConnection.update(
                table,
                contentValues(element)
                , DBConstants.KEY_SHOP_DATABASE_ID + " = ?", arrayOf(id.toString()))
        return numberOfRecordsUpdated.toLong()
    }
}