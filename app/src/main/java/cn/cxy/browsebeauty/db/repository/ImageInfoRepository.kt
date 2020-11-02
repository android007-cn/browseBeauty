package cn.cxy.browsebeauty.db.repository

import cn.cxy.browsebeauty.db.bean.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageInfoRepository : BaseRepository() {
    private val taskDao = dbInstance.imageInfoDao()
    suspend fun add(path: String,url:String) {
        withContext(Dispatchers.IO) {
            val imageInfo = ImageInfo(0, path,url)
            taskDao.add(imageInfo)
        }
    }

    suspend fun del(id: Int) {
        withContext(Dispatchers.IO) {
            val imageInfo = ImageInfo(id, "","")
            taskDao.del(imageInfo)
        }
    }
}