package com.muthuraj.moshi.gson.interop.retrofit.converter

/**
 * Created by Muthuraj on 15/07/22.
 */
data class GsonModel(val name: GsonName, val age: Int)

data class GsonName(val firstName: String, val lastName: String)