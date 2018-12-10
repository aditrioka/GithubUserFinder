package id.aditrioka.githubuserfinder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.aditrioka.githubuserfinder.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun usersDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it}
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java, "Github.db")
                    .build()
    }
}