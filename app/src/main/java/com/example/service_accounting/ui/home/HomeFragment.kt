package com.example.service_accounting.ui.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.service_accounting.databinding.FragmentHomeBinding
import com.example.service_accounting.db.*
import java.util.*

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var myDbManager: MyDbManager
    private lateinit var users: MutableList<User>
    private var _binding: FragmentHomeBinding? = null
    private var position: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        myDbManager = MyDbManager(requireContext())
        myDbManager.openDb()
        users = myDbManager.readUsers()
        binding.dropDown.adapter = SpinnerAdapterUser(requireContext(), users)
        binding.dropDown.onItemSelectedListener = this@HomeFragment
        binding.editTimeStart.setOnClickListener {
            this.view?.let { popUpDate(binding.editTimeStart, requireContext()) }
        }
        binding.editTimeEnd.setOnClickListener {
            this.view?.let { popUpDate(binding.editTimeEnd, requireContext()) }
        }
        binding.addRecord.setOnClickListener {
            this.view?.let {
                addRecord()
            }
        }
        return root
    }

    fun popUpDate(txt: TextView, context: Context) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { view, year, _month, _day ->
                var day = _day.toString()
                var month = (_month + 1).toString()
                if (_day < 10) {
                    day = "0$_day"
                }
                if (_month < 10) {
                    month = "0${_month + 1}"
                }
                val date = ("$day-$month-$year")
                val timePicker = TimePickerDialog(
                    context,
                    { view, hour, minute ->
                        var newMinute: String = minute.toString()
                        var newHour: String = hour.toString()
                        if (minute < 10) {
                            newMinute = "0$minute"
                        }
                        if (hour < 10) {
                            newHour = "0$hour"
                        }
                        val time = ("$newHour:$newMinute")
                        txt.text = date + " " + time
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.position = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


    fun addRecord() {
        try {
            val recordStart = binding.editTimeStart.text.toString()
            val recordEnd = binding.editTimeEnd.text.toString()
            val recordProduct = binding.productText.text.toString()
            if (recordProduct.isNotEmpty() && recordEnd.isNotEmpty() && recordStart.isNotEmpty()) {
                val record = Record(
                    0,
                    position,
                    binding.editTimeStart.text.toString(),
                    binding.editTimeEnd.text.toString(),
                    binding.thickNum.text.toString().toInt(),
                    binding.productText.text.toString()
                )
                val start = converTime(binding.editTimeStart.text.toString())
                val end = converTime(binding.editTimeEnd.text.toString())
                if (start.time < end.time)
                    myDbManager.insertToDb(record)
                else
                    Toast.makeText(
                        this.context,
                        "Дата начала больше даты конца!", Toast.LENGTH_SHORT
                    ).show()
            }
            else{
                println("AAAA")
                Toast.makeText(
                    this.context,
                    "Заполните все поля!", Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: java.lang.Exception) {
            Toast.makeText(
                this.context,
                "Заполните все поля", Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun converTime(time: String): Date {
        val dateWithTime = time.split(" ")
        val date = dateWithTime[0].split("-")
        val time = dateWithTime[1].split(":")
        var a = Date(
            date[2].toInt(),
            date[1].toInt(),
            date[0].toInt(),
            time[0].toInt(),
            time[1].toInt()
        )
        return a
    }
}