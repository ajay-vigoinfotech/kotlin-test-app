package com.example.replicaapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.replicaapp.R
import com.example.replicaapp.adaptor.TextAdapter

class FragmentTabThree : Fragment() {

    private var dataList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataList = it.getStringArrayList(ARG_DATA_LIST) ?: arrayListOf()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_three, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TextAdapter(dataList)

        return view
    }

    companion object {
        private const val ARG_DATA_LIST = "data_list"

        @JvmStatic
        fun newInstance(data: ArrayList<String>) =
            FragmentTabThree().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_DATA_LIST, data)
                }
            }
    }
}