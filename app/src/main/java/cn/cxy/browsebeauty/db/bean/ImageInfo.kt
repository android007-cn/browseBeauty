package cn.cxy.browsebeauty.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageInfo(@PrimaryKey(autoGenerate = true) val id: Int,
                     var path: String, var url:String)