package com.example.imeldaproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imeldaproject.databinding.ItemTodoBinding
import com.example.imeldaproject.entity.Todo

class TodoAdapter(
    private val dataset: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: Todo) {
            binding.title.text = item.tittle    // ✅ Perbaikan: item.tittle -> item.title
            binding.description.text = item.description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CustomViewHolder,
        position: Int
    ) {
        val data = dataset[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int = dataset.size

    fun updateData(newData: List<Todo>) {
        dataset.clear()
        dataset.addAll(newData)
        notifyDataSetChanged()
    }
}

