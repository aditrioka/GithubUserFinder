package id.aditrioka.githubuserfinder.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import id.aditrioka.githubuserfinder.api.WebService
import id.aditrioka.githubuserfinder.api.searchUsers
import id.aditrioka.githubuserfinder.db.UserLocalCache
import id.aditrioka.githubuserfinder.model.User

class UserBoundaryCallback(
    private val query: String,
    private val service: WebService,
    private val cache: UserLocalCache
) : PagedList.BoundaryCallback<User>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 100
    }

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchUsers(service, query, lastRequestedPage,
            NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }
}