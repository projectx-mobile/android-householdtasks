package com.projectx.householdtasks.data.example.repository

import com.projectx.auth.data.authentication.api.ExampleApi
import com.projectx.auth.data.authentication.repository.IRepository

class ExampleRepository(private val api: ExampleApi) : IRepository {
}