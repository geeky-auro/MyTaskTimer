package com.aurosaswatraj.tasktimer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.IllegalStateException


//Basic Database class for the application
/**The only class that should use [AppProvider]..!*/

private const val TAG="AppDatabase"
private const val DATABASE_NAME="TaskTimer.db"
private const val DATABASE_VERSION=3
internal class AppDatabase private constructor(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){

    init {
        Log.d(TAG,"AppDatabase: initializing")
    }

//    To Create Database
    override fun onCreate(db: SQLiteDatabase) {
//       CREATE TABLE Tasks(_id INTEGER PRIMARY KEY NOT NULL,NAME TEXT NOT NULL,Description TEXT,Sortorder INTEGER);
        Log.d(TAG,"onCreate: Starts")
    val sSQL="""CREATE TABLE ${TasksContract.TABLE_NAME}(
        ${TasksContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL,
        ${TasksContract.Columns.TASK_NAME} TEXT NOT NULL,
        ${TasksContract.Columns.TASK_DESCRIPTION} TEXT,
        ${TasksContract.Columns.TASK_SORT_ORDER} INTEGER);
    """.replaceIndent(" ")

    Log.d(TAG,sSQL)
    db.execSQL(sSQL)
    }

//    To upgrade database from one version to another..!
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG,"onUpgrade : Starts")
    when(oldVersion){
        1->{
//            Upgrade logic from Version 1
        }
        else->{
            throw IllegalStateException("onUpgrade() with unknown version:$newVersion")
        }
    }
    }

//Next Step is to create a singleton
//    Singleton class only allows a single instance to be create
//    To make a class singleton mark the constructor as private

    companion object :SingletonHolder<AppDatabase,Context>(::AppDatabase)

//    Instead of companion object using singleton in it we have  created a separate singleton class to deal with..!
//    companion object{
//        @Volatile
//        private var instance:AppDatabase?=null
//
//        fun getInstance(context:Context):AppDatabase= instance?: synchronized(this)
//        {
//                 instance ?:AppDatabase(context).also { instance=it }
//        }
//    }

//    Next Step,is to use a content provider!
}