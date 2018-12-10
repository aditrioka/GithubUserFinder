package id.aditrioka.githubuserfinder.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.aditrioka.githubuserfinder.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<User>)

    @Query("SELECT * FROM  users WHERE (login LIKE :queryString) ")
    fun usersByName(queryString: String): DataSource.Factory<Int, User>

}
