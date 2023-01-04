package ru.netology.hqw.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.netology.hqw.dao.PostDao
import ru.netology.hqw.repository.PostDaoImplementation

class AppDb private constructor(db: SQLiteDatabase){
    val postDao: PostDao = PostDaoImplementation(db)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this){
                instance ?: AppDb(
                    buildDatabase(context, arrayOf(PostDaoImplementation.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, DDLs: Array<String>) = DbHelper(
            context,1,"app.db", DDLs,).writableDatabase
    }
}