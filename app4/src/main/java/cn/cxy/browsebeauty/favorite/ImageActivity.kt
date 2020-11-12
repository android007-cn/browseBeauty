package cn.cxy.browsebeauty.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.bean.ImageInfo
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import cn.cxy.browsebeauty.utils.EXTRA_IMAGE_INFO
import cn.cxy.browsebeauty.utils.EXTRA_IMAGE_INFO_POSITION
import cn.cxy.browsebeauty.vptrans.AlphaScaleTransformer
import cn.cxy.browsebeauty.vptrans.GooglePageTransformer
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ImageActivity : AppCompatActivity() {
    private var mPositionInList = 0

    companion object {
        fun buildIntent(context: Context, imageInfo: ImageInfo, positionInImageList: Int): Intent {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_INFO, imageInfo)
            intent.putExtra(EXTRA_IMAGE_INFO_POSITION, positionInImageList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val imageInfo = intent.getParcelableExtra<ImageInfo>(EXTRA_IMAGE_INFO)
        mPositionInList = intent.getIntExtra(EXTRA_IMAGE_INFO_POSITION, 0)
        initViews(imageInfo)
    }

    private fun initViews(imageInfo: ImageInfo?) {
        vp.setPageTransformer(true, AlphaScaleTransformer())
        MainScope().launch {
            vp.adapter =
                ImageListAdapter(this@ImageActivity, ImageInfoRepository.list().toMutableList())
            vp.currentItem = mPositionInList
        }
    }
}

