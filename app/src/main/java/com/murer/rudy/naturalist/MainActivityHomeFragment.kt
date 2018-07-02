package com.murer.rudy.naturalist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import com.murer.rudy.naturalist.HomeFragment.TimePickerFragment
import com.murer.rudy.naturalist.HomeFragment.TimePickerFragment.OnDateReceiveCallBack






class HomeFragment : Fragment() {

    private lateinit var model: HomeFragmentModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!this::model.isInitialized) {
            model = HomeFragmentModel.newInstance()
        }

        fillDateTime()
        mtime.setOnClickListener {
            showTimePickerDialog()
        }
        mdate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun fillDateTime() {
        fillCurrentTime()
        fillCurrentDate()
    }

    private fun fillCurrentTime(){
        mtime.setText(model.getCurrentTime())
    }

    fun showTimePickerDialog() {
        val newFragment = TimePickerFragment()
        newFragment.show(fragmentManager, "timePicker")
    }

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        private var mListener: TimePickerFragment.OnDateReceiveCallBack? = null

        interface OnDateReceiveCallBack {
            fun onDateReceive(hourOfDay: Int,minute: Int)
        }

        override fun onAttach(context: Context?) {
            super.onAttach(context)


            try {
                mListener = context as TimePickerFragment.OnDateReceiveCallBack?
            } catch (e: ClassCastException) {
                throw ClassCastException(context?.toString() + " must implement OnDateSetListener")
            }

        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            return TimePickerDialog(activity, this, hour, minute,
                    DateFormat.is24HourFormat(activity))
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            mListener?.onDateReceive(hourOfDay,minute)
        }
    }

    private fun fillCurrentDate(){
        mdate.setText(model.getCurrentDate())
    }

    private fun showDatePickerDialog() {

    }


}


