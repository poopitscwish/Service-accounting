package com.example.service_accounting.db

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.service_accounting.R

class SpinnerAdapterUser(private val context: Context, private val list: MutableList<User>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position].fio
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rootView : View = LayoutInflater.from(context)
            .inflate(R.layout.spinner_right_aligned,parent,false)
        val idTxt : TextView  = rootView.findViewById(R.id.id)
        val fioTxt: TextView = rootView.findViewById(R.id.fioSpiner)
        idTxt.text = list[position].id.toString()
        fioTxt.text = list[position].fio
        return rootView
    }
}

