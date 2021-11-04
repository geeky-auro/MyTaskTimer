package com.aurosaswatraj.tasktimer

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object TasksContract{
//    We are going to use internal access modifier so that outside apps cannot access the tables
    internal const val TABLE_NAME="Tasks"

//    Tasks fields
/** The URI to ACCESS THE TASK TABLE*/
const val CONTENT_TYPE="vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
const val CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
val CONTENT_URI:Uri= Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)
object Columns{
//    WE can use hard coded values for "_id" but google provides BaseColumns._ID... for that...!
    const val ID=BaseColumns._ID
    const val TASK_NAME="Name"
    const val TASK_DESCRIPTION="Description"
    const val TASK_SORT_ORDER="SortOrder"
    }

    fun getId(uri:Uri):Long{
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id:Long):Uri{
        return ContentUris.withAppendedId(CONTENT_URI,id)
    }
}