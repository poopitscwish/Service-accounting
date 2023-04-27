package com.example.service_accounting.db

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.service_accounting.R
import com.example.service_accounting.ui.home.HomeFragment
import com.example.service_accounting.ui.slideshow.SlideshowFragment

class CustomAdapterRecord(
    private val context: Context,
    private val dataSet: MutableList<Record>,
    private val users: MutableList<User>
) :
    RecyclerView.Adapter<CustomAdapterRecord.ViewHolder>(), AdapterView.OnItemSelectedListener {

    var positionSpinner: Int = 0

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listRecord: ConstraintLayout
        val recordID: TextView
        val itemFioID: TextView
        val recordProduct: TextView
        val recordThick: TextView
        val recordStart: TextView
        val recordEnd: TextView


        init {
            // Define click listener for the ViewHolder's View
            recordID = view.findViewById(R.id.recordID)
            itemFioID = view.findViewById(R.id.recordFIO)
            recordProduct = view.findViewById(R.id.recordProduct)
            recordThick = view.findViewById(R.id.recordThick)
            recordStart = view.findViewById(R.id.recordStart)
            recordEnd = view.findViewById(R.id.recordEnd)
            listRecord = view.findViewById(R.id.listRecord)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recrod_recycle_view, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.recordID.text = dataSet[position].ID.toString()
        viewHolder.itemFioID.text = users[dataSet[position].FIO_ID].fio
        viewHolder.recordProduct.text = dataSet[position].PRODUCT
        viewHolder.recordThick.text = dataSet[position].THICK.toString()
        viewHolder.recordStart.text = dataSet[position].START
        viewHolder.recordEnd.text = dataSet[position].END
        viewHolder.listRecord.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Редактировать запись под номером ${dataSet[position].ID}")
            val dialogLayout = inflate(context, R.layout.edit_record_dialog, null)
            builder.setView(dialogLayout)


            dialogLayout.findViewById<Spinner>(R.id.FIO).adapter =
                SpinnerAdapterUser(context, users)
            dialogLayout.findViewById<Spinner>(R.id.FIO).onItemSelectedListener =
                this@CustomAdapterRecord

            dialogLayout.findViewById<TextView>(R.id.begin).text = dataSet[position].START
            dialogLayout.findViewById<TextView>(R.id.end).text = dataSet[position].END
            dialogLayout.findViewById<EditText>(R.id.editThick)
                .setText(dataSet[position].THICK.toString())
            dialogLayout.findViewById<EditText>(R.id.editProduct)
                .setText(dataSet[position].PRODUCT)

            dialogLayout.findViewById<TextView>(R.id.begin).setOnClickListener {
                HomeFragment().popUpDate(dialogLayout.findViewById(R.id.begin), context)
            }
            dialogLayout.findViewById<TextView>(R.id.end).setOnClickListener {
                HomeFragment().popUpDate(dialogLayout.findViewById(R.id.end), context)
            }

            builder.setNegativeButton("cancel") { _, i ->
                Toast.makeText(
                    this.context,
                    "Отмена!", Toast.LENGTH_SHORT
                ).show()
            }
            builder.setNeutralButton("удалить"){_,i->
                val myDbManager = MyDbManager(context)
                myDbManager.openDb()
                myDbManager.deleteRecord(dataSet[position].ID-1)
                refresh(myDbManager.readRecords())
                Toast.makeText(
                    this.context,
                    "Изменено!", Toast.LENGTH_SHORT
                ).show()

            }
            builder.setPositiveButton("OK") { _, i ->
                try {
                    val recordFIOID = positionSpinner
                    val recordStart =
                        dialogLayout.findViewById<TextView>(R.id.begin).text.toString()
                    val recordEnd = dialogLayout.findViewById<TextView>(R.id.end).text.toString()
                    val recordThick =
                        dialogLayout.findViewById<EditText>(R.id.editThick).text.toString().toInt()
                    val recordProduct =
                        dialogLayout.findViewById<EditText>(R.id.editProduct).text.toString()
                    if (recordProduct.isNotEmpty() && recordEnd.isNotEmpty() && recordStart.isNotEmpty()){
                    val record = Record(
                        position,
                        recordFIOID,
                        recordStart,
                        recordEnd,
                        recordThick,
                        recordProduct
                    )
                    if (HomeFragment().converTime(record.END) > HomeFragment().converTime(record.START)) {
                        val myDbManager = MyDbManager(context)
                        myDbManager.openDb()
                        myDbManager.updateRecord(record)
                        refresh(myDbManager.readRecords())
                        Toast.makeText(
                            this.context,
                            "Изменено!", Toast.LENGTH_SHORT
                        ).show()
                    } else
                        Toast.makeText(
                            this.context,
                            "Дата начала больше даты конца", Toast.LENGTH_SHORT
                        ).show()
                }
                else{
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
        builder.show()
    }

}


// Return the size of your dataset (invoked by the layout manager)
override fun getItemCount() = dataSet.size

fun refresh(newData: MutableList<Record>) {
    dataSet.clear()
    dataSet.addAll(newData)
    notifyDataSetChanged()
}

override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    this.positionSpinner = position
}

override fun onNothingSelected(parent: AdapterView<*>?) {
}

}