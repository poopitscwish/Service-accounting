package com.example.service_accounting.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.service_accounting.databinding.FragmentGalleryBinding
import com.example.service_accounting.db.CustomAdapter
import com.example.service_accounting.db.MyDbManager
import com.example.service_accounting.db.SQL
import com.example.service_accounting.db.User

class GalleryFragment : Fragment() {
    private lateinit var myDbManager: MyDbManager
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button.setOnClickListener {
            this.view?.let {  addUser(binding.fioField.text.toString()) }
        }
        myDbManager = MyDbManager(requireContext())
        readUsers()
        return root
    }

    private fun readUsers(init: Boolean = true) {
        myDbManager.openDb()
        if (init) {
            binding.recyclerView.adapter = CustomAdapter(myDbManager.readUsers())
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            val adapter = binding.recyclerView.adapter as CustomAdapter
            adapter.refresh(myDbManager.readUsers())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addUser(fio : String) {
        val newUser = User(0, fio)
        myDbManager.insertToDb(newUser)
        readUsers(false)

    }
}