package id.aditrioka.githubuserfinder.api

import com.google.gson.annotations.SerializedName
import id.aditrioka.githubuserfinder.model.User

data class Response(

    @SerializedName("message")
    val message: String,

    @SerializedName("documentation_url")
    val documentationUrl: String,

    @SerializedName("items")
    val users: List<User>

)