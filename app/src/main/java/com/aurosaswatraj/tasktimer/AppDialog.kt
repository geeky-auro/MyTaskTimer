package com.aurosaswatraj.tasktimer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialogFragment


private const val TAG = "AppDialog"

const val DIALOG_ID = "id"
const val DIALOG_MESSAGE = "message"
const val DIALOG_POSITIVE_RID = "positive_rid"
const val DIALOG_NEGATIVE_RID = "negative_rid"


class AppDialog:AppCompatDialogFragment() {

    private var dialogEvents: DialogEvents? = null

    internal interface DialogEvents {
        fun onPositiveDialogResult(dialogId: Int, args: Bundle)
//        fun onNegativeDialogResult(dialogId: Int, args: Bundle)
//        fun onDialogCancelled(dialogId: Int)
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach called: context is $context")
        super.onAttach(context)

        dialogEvents=try {
            parentFragment as DialogEvents
        }
        catch (e:TypeCastException){
            try {
                // No parent fragment, so call back the Activity instead
                context as DialogEvents
            }
            catch (e: ClassCastException) {
                // Activity doesn't implement the interface
                throw ClassCastException("Activity $context must implement AppDialog.DialogEvents interface")
            }
        }

        catch (e: ClassCastException) {
            // Parent fragment doesn't implement the interface
            throw ClassCastException("Fragment $parentFragment must implement AppDialog.DialogEvents interface")
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog called")
        return super.onCreateDialog(savedInstanceState)

    }

}