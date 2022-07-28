package com.block.employeedirectoryapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.block.employeedirectoryapp.api.EmployeeAPIService
import com.block.employeedirectoryapp.data.AppDispatchers
import com.block.employeedirectoryapp.data.Employee
import com.block.employeedirectoryapp.data.Employees
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
internal class EmployeeDirectoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val employeeAPIService = mock<EmployeeAPIService>()
    private lateinit var viewModel: EmployeeDirectoryViewModel
    private val testDispatcher = AppDispatchers(
        IO = TestCoroutineDispatcher()
    )
    private val employeeArray = arrayOf(Employee("1","Name","5342324232","example@example.com","dsdfdsd","small__url","large_url","HelpDesk","FULL_TIME"))
    private lateinit var employees:Employees
    @Before
    fun setup(){
        viewModel = EmployeeDirectoryViewModel(employeeAPIService, testDispatcher)
        employees = Employees(employeeArray)
    }

    @Test
    fun testsuccessfulAPICall() = runBlocking {
        whenever(employeeAPIService.getEmployees()).thenReturn(employees)
        viewModel.getEmployees()
        Assert.assertEquals(employees.employees.toCollection(ArrayList()), viewModel.employeeData.value)
    }



}


