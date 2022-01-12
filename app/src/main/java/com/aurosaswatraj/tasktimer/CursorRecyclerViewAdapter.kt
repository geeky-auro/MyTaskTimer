package com.aurosaswatraj.tasktimer

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_item.*
import java.lang.IllegalStateException

private const val TAG="CursorRecyclerViewAdapt"

class TaskViewHolder(override val containerView:View):
    RecyclerView.ViewHolder(containerView),LayoutContainer{

}

class CursorRecyclerViewAdapter(private var cursor: Cursor?) :RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG,"onCreateViewHolder STARTS: new View Requested.!")
        val view=LayoutInflater.from(parent.context).inflate(R.layout.task_list_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d(TAG,"onBindViewHolder starts.!")
        val cursor=cursor //avoid smartcasts

//        check whether the cursor is null i.e it has no records available
        if (cursor==null || cursor.count==0){
            Log.d(TAG,"onBindViewHolder:providing instructions")
            holder.tli_name.text = "Instructions"
            holder.tli_description.text ="Use the add button (+) in the toolbar above to create new tasks." +
                    "\n\nTasks with lower sort orders will be placed higher up the list." +
                    "Tasks with the same sort order will be sorted alphabetically." +
                    "\n\nTapping a task will start the timer for that task (and will stop the timer for any previous task that was being timed)." +
                    "\n\nEach task has Edit and Delete buttons if you want to change the details or remove the task."
            holder.tli_edit.visibility=View.GONE
            holder.tli_delete.visibility=View.GONE

        }
        else{
//            THere is some data /records available.>!
            if (!cursor.moveToPosition(position)){
                throw IllegalStateException("Couldn't move to the position $position")
            }
//            Create a task object from the data in the cursor
            val task=Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER))
            )
//            Remeber that ID isn't set in the constructor
            task.id=cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))

            holder.tli_name.text=task.name
            holder.tli_description.text=task.description
            holder.tli_edit.visibility=View.VISIBLE
            holder.tli_delete.visibility=View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}