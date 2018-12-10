package id.aditrioka.githubuserfinder

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import id.aditrioka.githubuserfinder.api.WebService
import id.aditrioka.githubuserfinder.data.UserRepository
import id.aditrioka.githubuserfinder.db.UserDatabase
import id.aditrioka.githubuserfinder.db.UserLocalCache
import id.aditrioka.githubuserfinder.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection {

    private fun provideCache(context: Context): UserLocalCache {
        val database = UserDatabase.getInstance(context)
        return UserLocalCache(database.usersDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): UserRepository {
        return UserRepository(WebService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }
}