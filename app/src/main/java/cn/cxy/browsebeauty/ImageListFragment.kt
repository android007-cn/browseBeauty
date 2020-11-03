package cn.cxy.browsebeauty

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import cn.cxy.browsebeauty.utils.ImageUtil.saveBitmap
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class ImageListFragment : Fragment() {
    private var urlList = mutableListOf<String>()
    private val imageInfoRepository = ImageInfoRepository()
    private var myAdapter: MyAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        queryData()
        //设置上下滑动
        vp2.orientation = ViewPager2.ORIENTATION_VERTICAL
        setListeners()
    }

    private fun setListeners() {
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateFavoriteIv(position)
            }
        })
        favoriteIv.setOnClickListener {
            favoriteIv.setColorFilter(Color.parseColor("#FF0000"))
            val imageFragment = myAdapter?.getFragment(vp2.currentItem)
            imageFragment?.let {
                val image = it.getImage()
                if (context != null && image != null) {
                    val filePath = saveBitmap(context!!, image)
                    GlobalScope.launch {
                        imageInfoRepository.add(filePath, it.url)
                    }
                }
            }
        }
    }

    private fun updateFavoriteIv(position: Int) {
        GlobalScope.launch {
            val fragment = getFragment(position)
            fragment?.let {
                setFavoriteIvSelected(imageInfoRepository.exists(it.url))
            }
        }
    }

    private fun setFavoriteIvSelected(isSelected: Boolean) {
        var color = "#000000"
        if (!isSelected) {
            color = "#FF0000"
        }
        favoriteIv.setColorFilter(Color.parseColor(color))
    }

    private fun getFragment(position: Int) = myAdapter?.getFragment(position)

    private fun queryData() {
        val networkService = getNetworkService()
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { networkService.query() }
            result.split("\n").forEach { urlList.add(it) }
            activity?.let {
                myAdapter = MyAdapter(it, urlList)
                vp2.adapter = myAdapter
            }
        }
    }

    private fun getNetworkService(): NetworkService {
        val okHttpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gitee.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        return retrofit.create(NetworkService::class.java)
    }
}