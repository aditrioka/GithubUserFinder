package id.aditrioka.githubuserfinder.db

import androidx.paging.DataSource
import id.aditrioka.githubuserfinder.model.User
import java.util.concurrent.Executor

class UserLocalCache(
    private val userDao: UserDao,
    private val ioExecutor: Executor
) {

    fun insert(users: List<User>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            userDao.insert(users)
            insertFinished()
        }
    }

    fun userByName(name: String): DataSource.Factory<Int, User> {
        val query = "%${name.replace(' ', '%')}%"
        return userDao.usersByName(query)
    }
}
