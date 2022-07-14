package com.muthuraj.moshi.gson.interop.retrofit.converter

import com.google.gson.Gson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

/**
 * Created by Muthuraj on 14/07/21.
 */
class MoshiGsonInteropConverterFactory(private val moshi: Moshi, private val gson: Gson) :
    Converter.Factory() {

    private val moshiConverter by lazy { MoshiConverterFactory.create(moshi) }
    private val gsonConverter by lazy { GsonConverterFactory.create(gson) }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type.isMoshiType()) {
            return moshiConverter.responseBodyConverter(type, annotations, retrofit)
        }
        return gsonConverter.responseBodyConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (type.isMoshiType()) {
            return moshiConverter.requestBodyConverter(
                type,
                parameterAnnotations,
                methodAnnotations,
                retrofit
            )
        }
        return gsonConverter.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    private fun Type.isMoshiType(): Boolean {
        return (this as? Class<Any>)?.annotations?.any { it is JsonClass } == true
    }
}