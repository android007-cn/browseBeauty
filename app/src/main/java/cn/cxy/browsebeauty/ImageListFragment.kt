package cn.cxy.browsebeauty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
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
        favoriteIv.setOnClickListener {
            ( vp2.currentItem as ImageFragment).test()
            GlobalScope.launch {
                imageInfoRepository.add("", "")
            }
        }
    }

    private fun queryData() {
        val networkService = getNetworkService()
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { networkService.query() }
            result.split("\n").forEach { urlList.add(it) }
            vp2.adapter = activity?.let {
                MyAdapter(
                    it,
                    urlList
                )
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