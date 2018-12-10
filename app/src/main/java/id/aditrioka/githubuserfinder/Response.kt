package id.aditrioka.githubuserfinder

import com.google.gson.annotations.SerializedName

data class Response(

    @SerializedName("message")
    val message: String,

    @SerializedName("documentation_url")
    val documentationUrl: String,

    @SerializedName("items")
    val users: MutableList<User>

)