package cn.cxy.browsebeauty

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class BaseFragment(var index: Int) : Fragment() {
    private var baseUrl = "https://gitee.com/cxyzy1/select-beauty/raw/master/images/image"
    private val suffix = ".jpg"
    private lateinit var mImageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView = ImageView(context)
        imageView.setBackgroundColor(Color.parseColor("#000000"))
        mImageView = imageView
        val url = baseUrl + (index + 1) + suffix
        Glide.with(this).load(url).into(imageView)
        return imageView
    }

}