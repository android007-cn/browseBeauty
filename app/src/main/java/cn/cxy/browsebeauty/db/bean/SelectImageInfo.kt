package cn.cxy.browsebeauty.db.bean

class SelectImageInfo(
    var id: Int,
    var path: String, var url: String,
    var isSelected: Boolean?
) {
    companion object {
        fun fromImageInfo(imageInfo: ImageInfo) =
            SelectImageInfo(imageInfo.id, imageInfo.path, imageInfo.url, null)
    }

    fun toImageInfo() = ImageInfo(id, path, url)
}