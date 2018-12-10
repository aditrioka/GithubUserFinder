package id.aditrioka.githubuserfinder.data

import androidx.paging.LivePagedListBuilder
import id.aditrioka.githubuserfinder.api.WebService
import id.aditrioka.githubuserfinder.db.UserLocalCache
import id.aditrioka.githubuserfinder.model.UserSearchResult

class UserRepository(
    private val service: WebService,
    private val cache: UserLocalCache
) {

    fun search(query: String): UserSearchResult {
        val dataSourceFactory = cache.userByName(query)

        val boundaryCallback = UserBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory,
            DATABASE_PAGE_SIZE
        )
            .setBoundaryCallback(boundaryCallback)
            .build()

        return UserSearchResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 100
    }
}