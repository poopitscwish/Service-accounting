package com.example.service_accounting.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.service_accounting.R

class CustomAdapter(private val dataSet: MutableList<User>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemId: TextView
        val itemFio: TextView

        init {
            // Define click listener for the ViewHolder's View
            itemId = view.findViewById(R.id.itemId)
            itemFio = view.findViewById(R.id.ItemFio)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_recycle_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.itemId.text = dataSet[position].id.toString()
        viewHolder.itemFio.text = dataSet[position].fio
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun refresh(newData: MutableList<User>){
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

}