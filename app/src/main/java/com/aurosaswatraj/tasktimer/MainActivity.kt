package com.aurosaswatraj.tasktimer

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.ui.AppBarConfiguration


import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.aurosaswatraj.tasktimer.databinding.ActivityMainBinding

private const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        val projection= arrayOf(TasksContract.Columns.TASK_NAME,TasksContract.Columns.TASK_SORT_ORDER)
        val sortOrder=TasksContract.Columns.TASK_SORT_ORDER
        val cursor=contentResolver.query(TasksContract.CONTENT_URI,projection,null,null,sortOrder)

        Log.d(TAG,"******************************")

        cursor?.use {
            while (it.moveToNext())
            {
//                Cycle through all records
                with(cursor){
//                    val id=getLong(0)
                    val name=getString(0)
//                    val description=getString(2)
                    val sortOrder=getString(1)
                    val result="Name:$name sort order:$sortOrder"
                    Log.d(TAG,"onCreate:reading data $result")

                }
            }
        }

        Log.d(TAG,"******************************")




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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}