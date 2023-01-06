package ru.netology.hqw.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.hqw.dao.PostDao
import ru.netology.hqw.dto.Post

class PostDaoImplementation(private val db: SQLiteDatabase): PostDao {

    companion object {
        val DDL = """
            CREATE TABLE ${PostColumns.TABLE}(
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_REPLIED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_REPLIES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO} TEXT NOT NULL DEFAULT "https://www.youtube.com/",
            ${PostColumns.COLUMN_VIEWS} INTEGER NOT NULL DEFAULT 0
            );
        """.trimIndent()
    }

    object PostColumns{
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_REPLIED_BY_ME = "repliedByMe"
        const val COLUMN_REPLIES = "replies"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_VIEWS = "views"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKED_BY_ME,
            COLUMN_LIKES,
            COLUMN_REPLIED_BY_ME,
            COLUMN_REPLIES,
            COLUMN_VIDEO,
            COLUMN_VIEWS
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use{
            while (it.moveToNext()){
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun likeById(Id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END,
                likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END
            WHERE id = ?;
            """.trimIndent(), arrayOf(Id)
        )
    }

    override fun replyById(Id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                repliedByMe = CASE WHEN repliedByMe THEN 0 ELSE 1 END,
                replies = replies + CASE WHEN repliedByMe THEN -1 ELSE 1 END
            WHERE id = ?;
            """.trimIndent(), arrayOf(Id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            if(post.id != 0L){
                put(PostColumns.COLUMN_ID,post.id)
            }
            put(PostColumns.COLUMN_AUTHOR,"Me")
            put(PostColumns.COLUMN_CONTENT,post.content)
            put(PostColumns.COLUMN_PUBLISHED, "now")
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use{
            it.moveToNext()
            return map(it)
        }
    }

    private fun map(cursor: Cursor): Post{
        with(cursor){
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                repliedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_REPLIED_BY_ME)) != 0,
                replies = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_REPLIES)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
                views = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWS))
            )
        }
    }
}