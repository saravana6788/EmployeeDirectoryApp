package com.block.employeedirectoryapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.block.employeedirectoryapp.api.EmployeeAPIService
import com.block.employeedirectoryapp.data.AppDispatchers
import com.block.employeedirectoryapp.data.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
open class EmployeeDirectoryViewModel @Inject constructor(private val employeeAPIService: EmployeeAPIService,
private val appDispatchers: AppDispatchers) : ViewModel() {


    companion object {
        private const val TAG = "EmployeeViewModel"
    }


    private val _employeeData = MutableLiveData<ArrayList<Employee>>()

    val employeeData: LiveData<ArrayList<Employee>>
        get() = _employeeData

    private var employeeList = ArrayList<Employee>()

    private val _progress = MutableLiveData<Boolean>()

    val progress: LiveData<Boolean>
        get() = _progress


    init {
        getEmployees()
    }

    fun getEmployees() {
        viewModelScope.launch {
            try {
                _progress.value = true
                val employees =  withContext(appDispatchers.IO) {employeeAPIService.getEmployees()}
                employeeList = employees.employees.toCollection(ArrayList())
                _employeeData.value = employeeList
                _progress.value = false
            } catch (exception: Exception) {
                Log.e(TAG, "Exception occurred while fetching the Employees Data")
                _progress.value = false
            }

        }
    }


    fun sortEmployees(sortBy: SortBy) {
        _employeeData.value = when (sortBy) {
            SortBy.NAME -> employeeList.sortedBy { employee -> employee.fullName }
            SortBy.TEAM -> employeeList.sortedBy { employee -> employee.team }
            else -> employeeList
        }.toCollection(ArrayList())
    }
}