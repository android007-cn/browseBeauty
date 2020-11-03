package cn.cxy.browsebeauty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import cn.cxy.browsebeauty.utils.ImageUtil.deleteFile
import cn.cxy.browsebeauty.utils.ImageUtil.saveBitmap
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.coroutines.*
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
        favoriteIcon.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                val imageFragment = myAdapter?.getFragment(vp2.currentItem)
                imageFragment?.let {
                    if (isChecked) {
                        addImageToFavorite(it)
                    } else {
                        delImageFromFavorite(it)
                    }
                }
            }
        })
    }

    private fun delImageFromFavorite(it: ImageFragment) {
        MainScope().launch {
            val imageInfo = imageInfoRepository.queryByUrl(it.url)
            imageInfo?.let {
                deleteFile(it.path)
                imageInfoRepository.del(it.url)
            }
        }
    }

    private fun addImageToFavorite(it: ImageFragment) {
        val image = it.getImage()
        if (context != null && image != null) {
            MainScope().launch(Dispatchers.IO) {
                val filePath = saveBitmap(context!!, image)
                imageInfoRepository.add(filePath, it.url)
            }
        }
    }

    private fun updateFavoriteIv(position: Int) {
        getFragment(position)?.let {
            MainScope().launch {
                favoriteIcon.isChecked = imageInfoRepository.exists(it.url)
            }
        }
    }

    private fun getFragment(position: Int) = myAdapter?.getFragment(position)

    private fun queryData() {
        val networkService = getNetworkService()
        MainScope().launch(Dispatchers.Main) {
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