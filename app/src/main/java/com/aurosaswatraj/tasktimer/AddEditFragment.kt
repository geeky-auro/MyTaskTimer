package com.aurosaswatraj.tasktimer

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_add_edit.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TASK = "task"
private const val TAG = "AddEditFragment"

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
    private var listener: OnSaveClicked? = null
//    View Model variable declaration
    private val viewModel by lazy {
        // ViewModelProviders.of(activity!!).get(TaskTimerViewModel::class.java)
        ViewModelProvider(this).get(TaskTimerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        To get the specific type of object from the bundle.>!
        Log.d(TAG, "onCreate: Starts")
        task = arguments?.getParcelable(ARG_TASK)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: Starts")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    //A good place to work with views after the views have been created
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        if (savedInstanceState == null) {
            val task = task
            if (task != null) {
                Log.d(TAG, "onViewCreated: Task details found , editing task ${task.id}")
                addedit_name.setText(task.name)
                addedit_description.setText(task.description)
                addedit_sortorder.setText(task.sortorder.toString())
            } else {
//                No task so we must be adding a new task and NOT editing an existing one..!
                Log.d(TAG, "onViewCreated: No Arguments, adding new record")
            }
        }
    }


    private fun taskfromUI() : Task{

//        At the moment the saveTask function is using values from the EditText widgets in the layout.
//        Thats where we come to need taskfromUI function

//        To get the value of SortOrder
        val sorOrder=if (addedit_sortorder.text.isNotEmpty()){
            Integer.parseInt(addedit_sortorder.text.toString())
        }
        else{
            0
        }
//        we'll make our new task, assign it an ID, and then return it.
        val newTask=Task(addedit_name.text.toString(),addedit_description.text.toString(),sorOrder)
        newTask.id=task?.id?:0
//        I used the Elvis operator (?:) on the above line, to assign the value 0 if the fragment's task is null.
        return newTask
    }

    private fun saveTask(){
//        Create a newTask Object with the details to be saved, then
//        Call the viewmodel's saveTask function to save it
//        Task is now a data class , so we can compare the new details with the original task,
//        and only save if they are different.


//        We'll create our new Task object, and we'll get the values from our EditText widgets.
        val newTask=taskfromUI()
//        Then we'll check to see if it's the same task that we were given, when the fragment was created.(the task that we are editing)
//       We can compare two task instances, because a data class provides an equals function for us.
        if (newTask != task){
            Log.d(TAG,"saveTask, saving task , id is ${newTask.id}")
//            We will call the save task function in the viewmodel
            task=viewModel.saveTask(newTask)
        }
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: Starts")
        if (context is OnSaveClicked) {
            listener = context
        } else {
            throw RuntimeException("$context Must implement OnSaveClicked")
        }

    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: Starts")
        super.onDetach()
        listener = null
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

    interface OnSaveClicked {
        fun onSaveClicked()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated Starts")
        super.onActivityCreated(savedInstanceState)
//        To provide back navigation from addEditFragment to main Activity
        if (listener is AppCompatActivity) {
            val actionbar = (listener as AppCompatActivity?)?.supportActionBar
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
        fun newInstance(task: Task?) =
            AddEditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_TASK, task)
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