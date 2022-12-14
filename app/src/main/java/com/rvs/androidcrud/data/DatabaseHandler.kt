package com.rvs.androidcrud.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.rvs.androidcrud.model.*

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,
                                                           DATABASE_NAME,
                                                    null,
                                                           DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createDocAppointmentTable = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
        KEY_APPOINTMENT_PLACE + " TEXT," +
        KEY_APPOINTMENT_DATE + " TEXT," +
        KEY_APPOINTMENT_TIME + " TEXT," +
        KEY_APPOINTMENT_DOCTOR + " TEXT," +
        KEY_APPOINTMENT_COMMENTS + " TEXT" + ");"

        db?.execSQL(createDocAppointmentTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun createDocAppointment(docAppointment: DocAppointment) {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_APPOINTMENT_PLACE, docAppointment.appPlace)
        values.put(KEY_APPOINTMENT_DATE, docAppointment.appDate)
        values.put(KEY_APPOINTMENT_TIME, docAppointment.appTime)
        values.put(KEY_APPOINTMENT_DOCTOR, docAppointment.appDoctor)
        values.put(KEY_APPOINTMENT_COMMENTS, docAppointment.appComments)

        var insert = db.insert(TABLE_NAME, null, values)
        Log.d("*** DATA INSERTED", "SUCCESS $insert")
        db.close()
    }

    @SuppressLint("Range")
    fun readDocAppointment(id: Int): DocAppointment {
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db.query(TABLE_NAME,
                                        arrayOf(KEY_ID,
                                                KEY_APPOINTMENT_PLACE,
                                                KEY_APPOINTMENT_DATE,
                                                KEY_APPOINTMENT_TIME,
                                                KEY_APPOINTMENT_DOCTOR,
                                                KEY_APPOINTMENT_COMMENTS),
                                        KEY_ID + "=?",
                                        arrayOf(id.toString()),
                                        null, null, null, null)

        if (cursor!=null) {
            cursor.moveToFirst()
        }

        var docAppointment =DocAppointment()

        docAppointment.appId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        docAppointment.appPlace = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_PLACE))
        docAppointment.appDate = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_DATE))
        docAppointment.appTime = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_TIME))
        docAppointment.appDoctor = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_DOCTOR))
        docAppointment.appComments = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_COMMENTS))

        return docAppointment
    }

    @SuppressLint("Range")
    fun readMultipleDocAppointments(): ArrayList<DocAppointment>{
        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<DocAppointment> = ArrayList()

        //Select all doctor appointments from table
        var selectAll = "SELECT * FROM " + TABLE_NAME

        var cursor: Cursor = db.rawQuery(selectAll, null)

        //loop through all doctor appointments
        if (cursor.moveToFirst()) {
            do {
                var docAppointment = DocAppointment()

                docAppointment.appId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                docAppointment.appPlace = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_PLACE))
                docAppointment.appDate = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_DATE))
                docAppointment.appTime = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_TIME))
                docAppointment.appDoctor = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_DOCTOR))
                docAppointment.appComments = cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_COMMENTS))
                list.add(docAppointment)

            }while (cursor.moveToNext())
        }
        return list
    }

    fun updateDocAppointment(docAppointment: DocAppointment): Int {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_APPOINTMENT_PLACE, docAppointment.appPlace)
        values.put(KEY_APPOINTMENT_DATE, docAppointment.appDate)
        values.put(KEY_APPOINTMENT_TIME, docAppointment.appTime)
        values.put(KEY_APPOINTMENT_DOCTOR, docAppointment.appDoctor)
        values.put(KEY_APPOINTMENT_COMMENTS, docAppointment.appComments)

        //update a row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(docAppointment.appId.toString()))
    }


    fun deleteDocAppointment(id: Int) {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))
        db.close()
    }

    fun getDocAppointmentCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(countQuery, null)
        return cursor.count
    }

}