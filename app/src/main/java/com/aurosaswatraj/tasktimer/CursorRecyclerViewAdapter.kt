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


//A base interface for all view holders supporting Android Extensions-style view access.
class TaskViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer{
//    By using an interface We can notify the fragment/activity that a button is tapped
//    MainActivityFragment will implement the functions in the interface,
//    and we can be sure that it contains the functions that we're going to call.
        fun bind(task:Task,listener :CursorRecyclerViewAdapter.onTaskClickListener){
//           we can pass a reference to it, to our ViewHolder.
//           The ViewHolder will then call the appropriate functions on the listener,
//           and it will do that in its onClick functions.
            tli_name.text=task.name
            tli_description.text=task.description
            tli_edit.visibility=View.VISIBLE
            tli_delete.visibility=View.VISIBLE

            tli_edit.setOnClickListener{
                listener.onEditClick(task)
            }

            tli_delete.setOnClickListener{
                listener.onDeleteClick(task)
            }
//            The onLongClickListener has to return true if it has handled the tap
            containerView.setOnLongClickListener{
                listener.onTaskLongClick(task)
                true
            }
        }
//We'll pass in the task, because ultimately we'll want to provide the task details
//        to whatever is listening for the buttons to be tapped.
    }


class CursorRecyclerViewAdapter(private var cursor: Cursor?,private val listener:onTaskClickListener) :
    RecyclerView.Adapter<TaskViewHolder>() {

    interface onTaskClickListener{
        fun onEditClick(task:Task)
        fun onDeleteClick(task:Task)
        fun onTaskLongClick(task:Task)
//      Now that we've defined the interface, we can pass in a reference to something that implements that interface,
//      so that the adapter knows what to call.(Adding in the primary Constructor)
    }

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

//            holder.tli_name.text = task.name
//            holder.tli_description.text = task.description
//            holder.tli_edit.visibility = View.VISIBLE
//            holder.tli_delete.visibility = View.VISIBLE
                /**Replace these lines with holder.bind(task)*/
//            We could set our onClickListeners for the buttons in this onBindViewHolder function,
//            Not Recommended..!
//            Another approach, and the one we're going to use here, is to give our view holder a bind function.
            holder.bind(task,listener)
        }

    }

    override fun getItemCount(): Int {

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