package com.block.employeedirectoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.block.employeedirectoryapp.data.Employee
import com.block.employeedirectoryapp.databinding.EmployeeCardViewBinding

class EmployeeTileAdapter :
    ListAdapter<Employee, EmployeeTileAdapter.ViewHolder>(EmployeeDiffUtilCallBack()) {
    class ViewHolder(private val binding: EmployeeCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employeeData: Employee) {
            binding.employee = employeeData
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployeeCardViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class EmployeeDiffUtilCallBack : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }

}