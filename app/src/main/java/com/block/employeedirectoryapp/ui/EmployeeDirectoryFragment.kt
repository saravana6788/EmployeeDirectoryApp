package com.block.employeedirectoryapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.block.employeedirectoryapp.R
import com.block.employeedirectoryapp.adapter.EmployeeTileAdapter
import com.block.employeedirectoryapp.databinding.EmployeeDirectoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class EmployeeDirectoryFragment : Fragment() {

    private lateinit var viewModel: EmployeeDirectoryViewModel
    private lateinit var binding: EmployeeDirectoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeDirectoryFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[EmployeeDirectoryViewModel::class.java]
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.employeeData.observe(viewLifecycleOwner) { it ->

            binding.apply {
                employeesCards.apply {
                    val employeeTileAdapter = EmployeeTileAdapter()
                    employeeTileAdapter.submitList(it)
                    adapter = employeeTileAdapter
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getEmployees()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.sortEmployees(
            when (item.itemId) {
                R.id.sort_by_name -> SortBy.NAME
                R.id.sort_by_team -> SortBy.TEAM
                else -> {
                    SortBy.UNSORTED
                }
            }
        )
        return super.onOptionsItemSelected(item)
    }

}

enum class SortBy {
    NAME,
    TEAM,
    UNSORTED
}