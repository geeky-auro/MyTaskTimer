package com.aurosaswatraj.tasktimer

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_item.*

private const val TAG = "CursorRecyclerViewAdapt"

class TaskViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer

class CursorRecyclerViewAdapter(private var cursor: Cursor?) :
    RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG, "onCreateViewHolder STARTS: new View Requested.!")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder starts.!")
        val cursor = cursor //avoid smartcasts

//        check whether the cursor is null i.e it has no records available
        if (cursor == null || cursor.count == 0) {
            Log.d(TAG, "onBindViewHolder:providing instructions")
            holder.tli_name.setText(R.string.instructions_headings)
            holder.tli_description.setText(R.string.instructions)
            holder.tli_edit.visibility = View.GONE
            holder.tli_delete.visibility = View.GONE

        } else {
//            THere is some data /records available.>!
            if (!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move to the position $position")
            }
//            Create a task object from the data in the cursor
            val task = Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER))
            )
//            Remember that ID isn't set in the constructor
            task.id = cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))

            holder.tli_name.text = task.name
            holder.tli_description.text = task.description
            holder.tli_edit.visibility = View.VISIBLE
            holder.tli_delete.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: starts")
        val cursor = cursor
        val count = if (cursor == null || cursor.count == 0) {
            1 //because we populate single viewholder with instructions
        } else {
            cursor.count
        }
        Log.d(TAG, "Returning $count")
        return count
    }


    /**
     *
     * Swap in a new cursor,returning the old cursor
     * The returned old cursor is *not* closed
     *
     * @param newcursor The new cursor to be used
     * @return Reurns the previously set Cursors, or null if there wasn't
     * one.
     * If the given new Cursor is the same instance as the previous set
     * Cursor, null is returned.
     * */

    fun swapCursoe(newCursor: Cursor?): Cursor? {
//        This function should be called whenever the cursor that the adapters using is changed
        if (newCursor === cursor) {
            return null
        }
        val numItems = itemCount
        val oldCursor = cursor
        cursor = newCursor
        if (newCursor != null) {
//            notify the observers about the new cursor
            notifyDataSetChanged()
        } else {
//            notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, numItems)
        }
        return oldCursor
    }

}