package cn.cxy.browsebeauty.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.cxy.browsebeauty.App
import cn.cxy.browsebeauty.db.bean.ImageInfo
import cn.cxy.browsebeauty.db.dao.ImageInfoDao


@Database(entities = [ImageInfo::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun imageInfoDao(): ImageInfoDao

    companion object {
        val DATABASE_NAME = "beauty"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(App.context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
