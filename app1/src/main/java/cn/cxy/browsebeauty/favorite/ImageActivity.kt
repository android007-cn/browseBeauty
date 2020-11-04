package cn.cxy.browsebeauty.favorite

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.bean.ImageInfo
import cn.cxy.browsebeauty.utils.EXTRA_IMAGE_INFO
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    companion object {
        fun buildIntent(context: Context, imageInfo: ImageInfo): Intent {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_INFO, imageInfo)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val imageInfo = intent.getParcelableExtra<ImageInfo>(EXTRA_IMAGE_INFO)
        photoView.setBackgroundColor(Color.parseColor("#000000"))
        imageInfo?.let {
            Glide.with(this).load(it.path).into(photoView)
        }
    }
}