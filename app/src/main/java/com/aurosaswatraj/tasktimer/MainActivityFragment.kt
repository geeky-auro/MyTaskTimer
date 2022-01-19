package com.aurosaswatraj.tasktimer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_activity.*
import java.lang.RuntimeException


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MainActivityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "MainActivityFragment"

class MainActivityFragment : Fragment(),CursorRecyclerViewAdapter.onTaskClickListener {

//    Create a ViewModel instance..
//    Describe viewModel. Remember to subscribe to it in onCreate.


    // ViewModel (Not implemented Yet)
// implementation "android.arch.lifecycle:extensions:2.4.0"
    private val viewModel by lazy {
        // ViewModelProviders.of(activity!!).get(TaskTimerViewModel::class.java)
        ViewModelProvider(this).get(TaskTimerViewModel::class.java)
    }


    //    For referring cursor adapter..
    private val mAdapter = CursorRecyclerViewAdapter(null,this)

    //    We passed null as the cursor, because we don't have one that's available yet.
//    Passing null will cause the adapter to return a view containing our instructions,
//    and that's exactly what we want to happen, when the app starts with
//    no task records.

    interface OnTaskEdit{
        fun onTaskEdit(task:Task)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView Called")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false)
    }


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach: called")
        super.onAttach(context)

        if (context !is OnTaskEdit){
            throw RuntimeException("${context.toString()} must implement onTaskEdit")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        //            to provide the new cursor, when we observe that it has changed.
//            When the cursor changes, we pass the new one to swapCursor,
//            causing the adapter to get the new data.
        viewModel.cursor.observe(this, { cursor -> mAdapter.swapCursoe(cursor)?.close() })
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        task_list.layoutManager = LinearLayoutManager(context) // <-- set up RecyclerView
        task_list.adapter = mAdapter

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: called")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onEditClick(task: Task) {
//        we'll call the activity's onTaskEdit function in onEditClick.
       (activity as OnTaskEdit?)?.onTaskEdit(task)
    }

    override fun onDeleteClick(task: Task) {
      viewModel.deleteTask(task.id)
        /**The onDeleteClick function in MainActivityFragment will then call the ViewModel's delete task function,
        to tell the ViewModel to delete the task.**/
    }

    override fun onTaskLongClick(task: Task) {
        TODO("Not yet implemented")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: called")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: called")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: called")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: called")
        super.onDetach()
    }


}