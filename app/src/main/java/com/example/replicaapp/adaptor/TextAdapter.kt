package com.example.replicaapp.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.replicaapp.databinding.ItemTextBinding

class TextAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    inner class TextViewHolder(val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.binding.textView.text = dataList[position]
    }

    override fun getItemCount(): Int = dataList.size
}