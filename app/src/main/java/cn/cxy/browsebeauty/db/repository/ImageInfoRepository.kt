package cn.cxy.browsebeauty.db.repository

import cn.cxy.browsebeauty.db.bean.ImageInfo
import cn.cxy.browsebeauty.db.bean.MultiImageInfo
import cn.cxy.browsebeauty.utils.FAVORITE_SIZE_OF_ROW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageInfoRepository : BaseRepository() {
    private val imageInfoDao = dbInstance.imageInfoDao()
    suspend fun add(path: String, url: String) {
        withContext(Dispatchers.IO) {
            val imageInfo = ImageInfo(0, path, url)
            imageInfoDao.add(imageInfo)
        }
    }

    suspend fun listAsMultiImageInfo(): List<MultiImageInfo> {
        return withContext(Dispatchers.IO) {
            val result = mutableListOf<MultiImageInfo>()
            val imageInfoList = imageInfoDao.list()
            (0..imageInfoList.size / FAVORITE_SIZE_OF_ROW + 1).forEach { index1 ->
                val tempList = mutableListOf<ImageInfo>()
                if (index1 < imageInfoList.size / FAVORITE_SIZE_OF_ROW) {
                    (0 until FAVORITE_SIZE_OF_ROW).forEach { index2 ->
                        val imageInfo = imageInfoList[index1 * FAVORITE_SIZE_OF_ROW + index2]
                        tempList.add(imageInfo)
                    }
                } else {
                    (0 until imageInfoList.size - FAVORITE_SIZE_OF_ROW * index1).forEach { index2 ->
                        val imageInfo = imageInfoList[index1 * FAVORITE_SIZE_OF_ROW + index2]
                        tempList.add(imageInfo)
                    }
                }

                result.add(MultiImageInfo(tempList))
            }
            return@withContext result
        }
    }

    suspend fun list(): List<ImageInfo> {
        return withContext(Dispatchers.IO) {
            imageInfoDao.list()
        }
    }

    suspend fun queryByUrl(url: String): ImageInfo? {
        return withContext(Dispatchers.IO) {
            return@withContext imageInfoDao.queryByUrl(url)
        }
    }

    suspend fun exists(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            val result = imageInfoDao.queryByUrl(url)
            return@withContext result != null
        }
    }

    suspend fun del(id: Int) {
        withContext(Dispatchers.IO) {
            val imageInfo = ImageInfo(id, "", "")
            imageInfoDao.del(imageInfo)
        }
    }

    suspend fun del(url: String) {
        withContext(Dispatchers.IO) {
            imageInfoDao.deleteByUrl(url)
        }
    }
}