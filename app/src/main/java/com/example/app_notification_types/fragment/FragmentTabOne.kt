package com.example.app_notification_types.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_notification_types.adaptor.TextAdapter
import com.example.app_notification_types.databinding.FragmentTabOneBinding

class FragmentTabOne : Fragment() {

    private lateinit var binding: FragmentTabOneBinding

    companion object {
        fun newInstance(data: ArrayList<String>): FragmentTabOne {
            val fragment = FragmentTabOne()
            val args = Bundle()
            args.putStringArrayList("data", data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabOneBinding.inflate(inflater, container, false)

        val list = arguments?.getStringArrayList("data") ?: arrayListOf()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = TextAdapter(list)

        return binding.root
    }
}