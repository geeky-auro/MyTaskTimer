package com.aurosaswatraj.tasktimer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Task(val name:String,val description:String,val sortorder:Int) : Parcelable {
    var id:Long=0
}

/**A Parcelable is the Android implementation of the Java Serializable. It assumes a certain structure and way of processing it. This way a Parcelable can be processed relatively fast, compared to the standard Java serialization.
//
//To allow your custom object to be parsed to another component they need to implement the android.os.Parcelable interface. It must also provide a static final method called CREATOR which must implement the Parcelable.Creator interface.
//
//The code you have written will be your model class.
//
//You can use Parcelable in Activity like :
//
//intent.putExtra("student", new Student("1")); //size which you are storing
//
//And to get this object :
//
//Bundle data = getIntent().getExtras();
//Student student = (Student) data.getParcelable("student");
//
//Here Student is a model class name. replace this with yours.
//
//In simple terms Parcelable is used to send a whole object of a model class to another page.
//
//In your code this is in the model and it is storing int value size to Parcelable object to send and retrieve in other activity.*/
/*Reference :
//
//Tutorial 1:https://www.vogella.com/tutorials/AndroidParcelable/article.html
//
//Tutorial 2:https://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-parcelable
//
//Tutorial 3:https://dzone.com/articles/using-android-parcel*/

/**TO make pacelize active use this in gradle plugins ( id 'kotlin-android-extensions')*/

// About Data Class: https://kotlinlang.org/docs/data-classes.html

//Note there is a downside of using data class that it may include 18 more methods which could increase up number method size #note to use it when required,if not avoid using data class instead use a normal class