package cn.cxy.browsebeauty.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cn.cxy.browsebeauty.db.bean.ImageInfo

@Dao
interface ImageInfoDao {
    @Query("SELECT * FROM ImageInfo")
    fun getTaskList(): DataSource.Factory<Int, ImageInfo>

    @Insert
    fun add(imageInfo: ImageInfo)

    @Insert
    fun add(imageInfos: List<ImageInfo>)

    @Delete
    fun del(imageInfo: ImageInfo)
}