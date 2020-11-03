package cn.cxy.browsebeauty.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = FavoriteAdapter()
        favoriteRv.adapter = adapter
        favoriteRv.layoutManager = LinearLayoutManager(this)
        MainScope().launch {
            adapter.setData(ImageInfoRepository.listAsMultiImageInfo())
        }
    }

    private fun getData(): List<String> {
        val dataList = ArrayList<String>()
        for (index in 0 until 100) {
            val text = " 数据$index "
            dataList.add(text)
        }
        return dataList
    }

}