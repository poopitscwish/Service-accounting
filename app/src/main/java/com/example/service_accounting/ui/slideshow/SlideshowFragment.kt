package com.example.service_accounting.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service_accounting.databinding.FragmentSlideshowBinding
import com.example.service_accounting.db.CustomAdapter
import com.example.service_accounting.db.CustomAdapterRecord
import com.example.service_accounting.db.MyDbManager

class SlideshowFragment : Fragment() {
    private lateinit var myDbManager: MyDbManager
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        myDbManager = MyDbManager(requireContext())
        readUsers()
        return root
    }

    fun readUsers(init: Boolean = true) {
        myDbManager.openDb()
        if (init) {
            binding.recycleRecord.adapter = CustomAdapterRecord(requireContext(),myDbManager.readRecords(),myDbManager.readUsers())
            binding.recycleRecord.layoutManager = GridLayoutManager(requireContext(),1)
        } else {
            val adapter = binding.recycleRecord.adapter as CustomAdapterRecord
            adapter.refresh(myDbManager.readRecords())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}