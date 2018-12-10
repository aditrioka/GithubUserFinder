package id.aditrioka.githubuserfinder.api

import id.aditrioka.githubuserfinder.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun searchUsers(
    service: WebService,
    query: String,
    page: Int,
    itemsPerPage: Int,
    onSuccess: (repos: List<User>) -> Unit,
    onError: (error: String) -> Unit
) {

    val apiQuery = query

    service.getUsers(apiQuery, page, itemsPerPage).enqueue(
        object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, t: Throwable) {
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<Response>?,
                response: retrofit2.Response<Response>
            ) {
                if (response.isSuccessful) {
                    val repos = response.body()?.users ?: emptyList()
                    onSuccess(repos)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}

interface WebService {

    @GET("search/users")
    fun getUsers(@Query("q") query: String,
                 @Query("page") page: Int,
                 @Query("per_page") perPage: Int): Call<Response>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): WebService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService::class.java)
        }
    }
}