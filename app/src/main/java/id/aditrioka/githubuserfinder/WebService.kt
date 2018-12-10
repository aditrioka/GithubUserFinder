package id.aditrioka.githubuserfinder

import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET("search/users")
    fun getUsers(@Query("q") query: String,
                 @Query("page") page: Int,
                 @Query("per_page") perPage: Int)
}