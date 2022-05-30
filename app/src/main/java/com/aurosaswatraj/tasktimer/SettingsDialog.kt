package com.aurosaswatraj.tasktimer

import androidx.appcompat.app.AppCompatDialogFragment
import java.util.*


private const val TAG = "SettingsDialog"

const val SETTINGS_FIRST_DAY_OF_WEEK = "FirstDay"
const val SETTINGS_IGNORE_LESS_THAN = "IgnoreLessThan"
const val SETTINGS_DEFAULT_IGNORE_LESS_THAN = 0

//                              0  1  2   3   4   5   6   7   8   9   10  11  12  13   14   15   16   17   18   19   20   21   22   23    24
private val deltas = intArrayOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 120, 180, 240, 300, 360, 420, 480, 540, 600, 900, 1800, 2700)


class SettingsDialog:AppCompatDialogFragment() {

    private val defaultFirstDayOfWeek = GregorianCalendar(Locale.getDefault()).firstDayOfWeek
    private var firstDay = defaultFirstDayOfWeek
    private var ignoreLessThan = SETTINGS_DEFAULT_IGNORE_LESS_THAN


}