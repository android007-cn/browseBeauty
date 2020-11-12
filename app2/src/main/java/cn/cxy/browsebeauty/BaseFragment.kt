package cn.cxy.browsebeauty

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class BaseFragment(var url: String) : Fragment() {
    private lateinit var mImageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView = PhotoView(context)
        imageView.setBackgroundColor(Color.parseColor("#000000"))
        mImageView = imageView
        Glide.with(this).load(url).into(imageView)
        return imageView
    }
}