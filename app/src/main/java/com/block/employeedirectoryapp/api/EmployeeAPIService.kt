package com.block.employeedirectoryapp.api

import com.block.employeedirectoryapp.data.Employees
import retrofit2.http.GET
import javax.inject.Singleton



interface EmployeeAPIService {
    @GET("employees.json")
    suspend fun getEmployees(): Employees

}

