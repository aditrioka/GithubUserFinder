package id.aditrioka.githubuserfinder.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.aditrioka.githubuserfinder.R
import id.aditrioka.githubuserfinder.model.User

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.nameTextView)
    private val avatar: ImageView = view.findViewById(R.id.avatarImageView)

    private var user: User? = null

    fun bind(user: User?, context: Context) {
        if (user == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)

        } else {
            showUserData(user, context)
        }
    }

    private fun showUserData(user: User, context: Context) {
        this.user = user
        name.text = user.login
        Glide.with(context).load(user.avatarUrl).into(avatar)
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }
    }
}
