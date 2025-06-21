package com.example.replicaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.replicaapp.R
import com.example.replicaapp.model.Entry

class EntryAdapter(
    private val entries: List<Entry>,
    private val onEditClick: (Entry, Int) -> Unit,
    private val onDeleteClick: (Entry) -> Unit
) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)

        val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.tvHeading.text = entry.name
        holder.tvAmount.text = entry.amount

        holder.ivEdit.setOnClickListener { onEditClick(entry, position) }
        holder.ivDelete.setOnClickListener { onDeleteClick(entry) }
    }

    override fun getItemCount(): Int = entries.size
}