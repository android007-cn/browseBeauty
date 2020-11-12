package cn.cxy.browsebeauty.db.repository

import cn.cxy.browsebeauty.db.AppDatabase

open class BaseRepository {
    val dbInstance = AppDatabase.getInstance()
}