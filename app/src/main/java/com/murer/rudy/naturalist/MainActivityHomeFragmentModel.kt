package com.murer.rudy.naturalist

import java.util.*

interface HomeFragmentModel {

    companion object {
        fun newInstance(): HomeFragmentModelImpl =
                HomeFragmentModelImpl(
                        // add param here
                )

    }

    // add functions here
    fun getCurrentTime(): String

    fun getCurrentDate(): String


}
// add data class here

class HomeFragmentModelImpl(
        // add Managers here
) : HomeFragmentModel {


    override fun getCurrentTime(): String {
        val c = Calendar.getInstance()
        return StringBuilder().append(c.get(Calendar.HOUR_OF_DAY)).append(":").append(c.get(Calendar.MINUTE)).toString()
    }

    override fun getCurrentDate(): String {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH) + 1 // month is 0 to 11
        val year = c.get(Calendar.YEAR)

        var dayToDisplay = ""
        var monthToDisplay = ""

        if (day < 10) {
            dayToDisplay += "0" + day.toString()
        }

        if (month < 10) {
            monthToDisplay += "0" + month.toString()
        }

        return StringBuilder().append(dayToDisplay).append("/").append(monthToDisplay).append("/").append(year).toString()
    }




}