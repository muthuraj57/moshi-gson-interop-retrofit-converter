package com.muthuraj.moshi.gson.interop.retrofit.converter

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.jaredsburrows.retrofit2.adapter.synchronous.SynchronousCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.http.GET

/**
 * Created by Muthuraj on 15/07/22.
 */
class MoshiGsonInteropConverterFactoryTest {

    @Test
    fun moshiTest() {
        val apiService = createRetrofit("/moshiModel/") {
            setBody(
                """
                {
                  "name": {
                     "firstName": "John",
                      "lastName": "Doe"
                   },
                   "age": 25
                }
                """.trimIndent()
            )
        }
        val actual = apiService.getMoshiModel()
        val expected = MoshiModel(name = MoshiName(firstName = "John", lastName = "Doe"), age = 25)
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun gsonTest() {
        val apiService = createRetrofit("/gsonModel/") {
            setBody(
                """
                {
                  "name": {
                     "firstName": "John",
                      "lastName": "Doe"
                   },
                   "age": 25
                }
                """.trimIndent()
            )
        }
        val actual = apiService.getGsonModel()
        val expected = GsonModel(name = GsonName(firstName = "John", lastName = "Doe"), age = 25)
        assertThat(expected).isEqualTo(actual)
    }

    private interface ApiService {
        @GET("/moshiModel")
        fun getMoshiModel(): MoshiModel

        @GET("/gsonModel")
        fun getGsonModel(): GsonModel
    }

    private val server = MockWebServer()

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun createRetrofit(url: String, block: MockResponse.() -> MockResponse): ApiService {
        server.enqueue(MockResponse().block())
        server.start()

        val moshi = Moshi.Builder().build()
        val gson = Gson()

        return Retrofit.Builder()
            .baseUrl(server.url(url))
            .client(OkHttpClient.Builder().build())
            .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
            .addConverterFactory(MoshiGsonInteropConverterFactory(moshi, gson))
            .build()
            .create(ApiService::class.java)
    }
}