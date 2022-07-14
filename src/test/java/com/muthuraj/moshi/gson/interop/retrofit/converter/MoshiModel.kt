package com.muthuraj.moshi.gson.interop.retrofit.converter

import com.squareup.moshi.JsonClass

/**
 * Created by Muthuraj on 15/07/22.
 */
@JsonClass(generateAdapter = true)
data class MoshiModel(val name: MoshiName, val age: Int)

@JsonClass(generateAdapter = true)
data class MoshiName(val firstName: String, val lastName: String)