package com.aurosaswatraj.tasktimer

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_add_edit.*
import java.lang.RuntimeException


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TASK = "task"
private const val TAG="AddEditFragment"
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddEditFragment.OnSaveClicked] interface
 * to handle interaction events.
 * Use the [AddEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddEditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var task: Task? = null
    private var listener:OnSaveClicked?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        To get the specific type of object from the bundle.>!
        Log.d(TAG,"onCreate: Starts")
        task=arguments?.getParcelable(ARG_TASK)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView: Starts")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    //A good place to work with views after the views have been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        if(savedInstanceState==null) {
            val Task=task
            if (Task != null) {
                Log.d(TAG, "onViewCreated: Task details found , editing task ${Task.id}")
                addedit_name.setText(Task.name)
                addedit_description.setText(Task.description)
                addedit_sortorder.setText(Task.sortorder)
            } else {
//                No task so we must be adding a new task and NOT editing an existing one..!
                Log.d(TAG, "onViewCreated: No Arguments, adding new record")
            }
        }
    }

private fun saveTask(){
//    Update the Database if at least one field has changed
//    There's no need to hit the database unless this has happened.. :)
//    TO save the value in the database we will use the content provider..
//    Note: This functions updates the database if we are editing the existing task
//          else we are inserting the data into the database
    val sortOrder=if (addedit_sortorder.text.isNotEmpty()){
        Integer.parseInt(addedit_sortorder.text.toString())
    }else{
        0
    }

    val values=ContentValues()
    val Task=task
    if (Task!=null){
        Log.d(TAG,"saveTask: Updating Existing Task")
        if (addedit_name.text.toString()!=Task.name){
            values.put(TasksContract.Columns.TASK_NAME,addedit_name.text.toString())
        }
        if (addedit_description.text.toString()!=Task.description){
            values.put(TasksContract.Columns.TASK_DESCRIPTION,addedit_description.text.toString())
        }
        if (sortOrder!=Task.sortorder){
            values.put(TasksContract.Columns.TASK_SORT_ORDER,sortOrder)
        }
//        Check whether after saving anything has been changed or not..!
        if (values.size()!=0){
            Log.d(TAG,"saveTask :Updating Task")
            activity?.contentResolver?.update(TasksContract.buildUriFromId(Task.id),
            values,null,null)
        }else{
            Log.d(TAG,"saveTask: adding new task")
            if (addedit_name.text.isNotEmpty()){
                values.put(TasksContract.Columns.TASK_NAME,addedit_name.text.toString())
            }
            if (addedit_description.text.isNotEmpty()){
                values.put(TasksContract.Columns.TASK_DESCRIPTION,
                addedit_description.text.toString())
            }
            values?.put(TasksContract.Columns.TASK_SORT_ORDER,sortOrder)//defaults to zero if empty
//            Final thing to do is to save
            activity?.contentResolver?.insert(TasksContract.CONTENT_URI,values)
        }

    }
}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"onAttach: Starts")
    if (context is OnSaveClicked){
        listener=context
    }
        else{
            throw RuntimeException("$context Must implement OnSaveClicked")
    }

    }

    override fun onDetach() {
        Log.d(TAG,"onDetach: Starts")
        super.onDetach()
        listener=null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    interface OnSaveClicked{
    fun onSaveClicked()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG,"onActivityCreated Starts")
        super.onActivityCreated(savedInstanceState)
//        To provide back navigation from addEditFragment to main Activity
        if (listener is AppCompatActivity){
            val actionbar=(listener as AppCompatActivity?)?.supportActionBar
            actionbar?.setDisplayHomeAsUpEnabled(true)
        }

        addedit_save.setOnClickListener {
            saveTask()
            listener?.onSaveClicked()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param task The task to be edited or null to add the new task.
         * @return A new instance of fragment AddEditFragment.
         */

        @JvmStatic
        fun newInstance(task:Task?) =
            AddEditFragment().apply {
                arguments = Bundle().apply {
                   putParcelable(ARG_TASK,task)
                }
            }
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




}

















































//For complete Fragment Life-Cycle: https://github.com/xxv/android-lifecycle
//Also : https://docs.google.com/presentation/d/1BlPU8VZXtwbNGdOdNf7dcGCxdzIlCzC353Rt5h3z8kg/edit?usp=sharing
//fun createFrag(task:Task){
//    val args=Bundle()
//    args.putParcelable(ARG_TASK,task)
//    val fragment=AddEditFragment()
//    fragment.arguments=args
//}
//
//
//fun simpler(task:Task){
//    val fragment=AddEditFragment.newInstance(task)
//}