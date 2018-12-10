package id.aditrioka.githubuserfinder.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.aditrioka.githubuserfinder.model.User

class UserSearchResult (
    val data: LiveData<PagedList<User>>,
    val networkErrors: LiveData<String>
)
