package cn.cxy.browsebeauty.db.repository

import cn.cxy.browsebeauty.db.bean.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageInfoRepository : BaseRepository() {
    private val imageInfoDao = dbInstance.imageInfoDao()
    suspend fun add(path: String, url: String) {
        withContext(Dispatchers.IO) {
            val imageInfo = ImageInfo(0, path, url)
            imageInfoDao.add(imageInfo)
        }
    }

    suspend fun del(id: Int) {
        withContext(Dispatchers.IO) {
            val imageInfo = ImageInfo(id, "", "")
            imageInfoDao.del(imageInfo)
        }
    }

    suspend fun list(): List<ImageInfo> {
        return withContext(Dispatchers.IO) {
            imageInfoDao.list()
        }
    }

    suspend fun exists(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            val result = imageInfoDao.queryByUrl(url)
            return@withContext result != null
        }
    }
}