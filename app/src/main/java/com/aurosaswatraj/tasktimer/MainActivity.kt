package com.aurosaswatraj.tasktimer

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.aurosaswatraj.tasktimer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked {

    //    Whether the activity is in 2-pane mode
//    i.e runnung in landscape, or on a tablet
    private var mTwoPane = false

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Started")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
//        Check the orientation whether landscape or potrait
        mTwoPane = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Log.d(TAG, "onCreate: Twopane is $mTwoPane")
        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if (fragment != null) {
            // There was an existing fragment to edit a task, make sure the panes are set correctly
            showEditPane()
        } else {
            task_details_container.visibility = if (mTwoPane) {
                View.INVISIBLE
            } else {
                View.GONE
            }
            mainFragment.visibility = View.VISIBLE

        }

        Log.d(TAG, "onCreate: Finished")

    }

    private fun showEditPane() {
//        There was an existing fragment to edit a task..Make sure the panes are set correctly
        task_details_container.visibility = View.VISIBLE
//      Hide the left hand pane,if in single pane view
        mainFragment.visibility = if (mTwoPane) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun removeEditPane(fragment: Fragment? = null) {
        Log.d(TAG, "removeEditPane called")

        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment)
                .commit()
        }
        // Set the visibility of the right hand pane
        task_details_container.visibility = if (mTwoPane) View.INVISIBLE else View.GONE
//        and show the left hand pane
        mainFragment.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onSaveClicked() {
        Log.d(TAG, "onSaveClicked: called")
        val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        removeEditPane(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menumain_addTask -> {
                taskEditRequest(null)
            }
            android.R.id.home -> {
                Log.d(TAG, "onOptionsItemSelected:Home button Pressed")
                val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
                removeEditPane(fragment)
            }
//            R.id.menumain_settings -> true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskEditRequest(task: Task?) {
        Log.d(TAG, "taskEditRequest: Starts")
        val newFragment = AddEditFragment.newInstance(task)
//        This scoped code segment is used to create and replace fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_details_container, newFragment)
            .commit()

        showEditPane()
        Log.d(TAG, "Exiting taskEditRequest")
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if (fragment == null || mTwoPane) {
            super.onBackPressed()
        } else {
            removeEditPane(fragment)
        }
    }

    // MainActivity Lifecycle callback events - added for logging only
    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG, "onRestoreInstanceState: called")
        super.onRestoreInstanceState(savedInstanceState)
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

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }


}


//
//private fun testUpdatetwo() {
////        val values = ContentValues().apply {
////            put(TasksContract.Columns.TASK_SORT_ORDER, 99)
////            put(TasksContract.Columns.TASK_DESCRIPTION, "Record Content Provider Videos")
////
////        }
////        val selection=TasksContract.Columns.TASK_SORT_ORDER+" =2"
////        val rowsAffected = contentResolver.update(TasksContract.CONTENT_URI, values,selection,null)
////        Log.d(TAG,"Number of Rows Affected is $rowsAffected")
//
//    /** OR*/
//
//    val values=ContentValues().apply {
//        put(TasksContract.Columns.TASK_SORT_ORDER, 999)
//        put(TasksContract.Columns.TASK_DESCRIPTION, "Deleting Records")
//    }
//
//    val selection=TasksContract.Columns.TASK_SORT_ORDER+" =?"
//    val selectionArgs= arrayOf("99")
//
//    val rowsAffected=contentResolver.update(TasksContract.CONTENT_URI,
//        values,
//        selection,selectionArgs)
//
//    Log.d(TAG,"Number of Rows Affected is $rowsAffected")
//
////        Probable Output would be:-
//    /**2021-11-06 23:35:12.672 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: Number of Rows Affected is 3
//    2021-11-06 23:35:12.695 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: ******************************
//    2021-11-06 23:35:12.695 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:1. Name:TaskTimer. Description:Tasktimer app creation. Sort Order:null
//    2021-11-06 23:35:12.696 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:3. Name:Android kotlin. Description:Android Kotlin Course. Sort Order:0
//    2021-11-06 23:35:12.698 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:2. Name:ANDROID JAVA. Description:Record Content Provider Videos. Sort Order:99
//    2021-11-06 23:35:12.698 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:4. Name:Content Provider. Description:Record Content Provider Videos. Sort Order:99
//    2021-11-06 23:35:12.698 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:5. Name:Content Provider. Description:Record Content Provider Videos. Sort Order:99
//    2021-11-06 23:35:12.699 2645-2645/com.aurosaswatraj.tasktimer D/MainActivity: ******************************
//    2021-11-07 00:02:32.869 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: Number of Rows Affected is 3
//    2021-11-07 00:02:32.871 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: ******************************
//    2021-11-07 00:02:32.871 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:1. Name:TaskTimer. Description:Tasktimer app creation. Sort Order:null
//    2021-11-07 00:02:32.871 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:3. Name:Android kotlin. Description:Android Kotlin Course. Sort Order:0
//    2021-11-07 00:02:32.871 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:2. Name:ANDROID JAVA. Description:Deleting Records. Sort Order:999
//    2021-11-07 00:02:32.871 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:4. Name:Content Provider. Description:Deleting Records. Sort Order:999
//    2021-11-07 00:02:32.871 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: onCreate:reading data ID:5. Name:Content Provider. Description:Deleting Records. Sort Order:999
//    2021-11-07 00:02:32.874 2845-2845/com.aurosaswatraj.tasktimer D/MainActivity: *******************************/
//
//
//}
//
//
//private fun testDelete() {
//
//
//
//    val tasksUri=TasksContract.buildUriFromId(2)
//    val rowsAffected = contentResolver.delete(tasksUri, null,null)
//    Log.d(TAG,"Number of Rows Deleted is $rowsAffected")
//
//
//}
//
//private fun testDeletetwo() {
////        val values = ContentValues().apply {
////            put(TasksContract.Columns.TASK_SORT_ORDER, 99)
////            put(TasksContract.Columns.TASK_DESCRIPTION, "Record Content Provider Videos")
////
////        }
////        val selection=TasksContract.Columns.TASK_SORT_ORDER+" =2"
////        val rowsAffected = contentResolver.update(TasksContract.CONTENT_URI, values,selection,null)
////        Log.d(TAG,"Number of Rows Affected is $rowsAffected")
//
//    /** OR*/
//
//
//
//    val selection=TasksContract.Columns.TASK_DESCRIPTION+" =?"
//    val selectionArgs= arrayOf("Deleting Records")
//
//    val rowsAffected=contentResolver.delete(TasksContract.CONTENT_URI,
//        selection,
//        selectionArgs)
//
//    Log.d(TAG,"Number of Rows Deleted is $rowsAffected")
//
//
//
//
//}
//
//
//private fun testUpdate() {
//    val values = ContentValues().apply {
//        put(TasksContract.Columns.TASK_NAME, "Content Provider")
//        put(TasksContract.Columns.TASK_DESCRIPTION, "Record Content Provider Videos")
//
//    }
//    val tasksUri=TasksContract.buildUriFromId(5)
//    val rowsAffected = contentResolver.update(tasksUri, values,null,null)
//    Log.d(TAG,"Number of Rows Affected is $rowsAffected")
//
//
//}
//
//private fun testInsert() {
//    val values = ContentValues().apply {
//        put(TasksContract.Columns.TASK_NAME, "New Task 1")
//        put(TasksContract.Columns.TASK_DESCRIPTION, "Description 1")
//        put(TasksContract.Columns.TASK_SORT_ORDER, 2)
//    }
//
//    val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
//    Log.d(TAG, "New row id (in uri) is $uri")
//    Log.d(TAG, "id (in uri) is ${uri?.let { TasksContract.getId(it) }}")
//}