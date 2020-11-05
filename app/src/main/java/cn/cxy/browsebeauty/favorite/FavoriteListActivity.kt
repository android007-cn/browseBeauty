package cn.cxy.browsebeauty.favorite

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class FavoriteListActivity : AppCompatActivity() {
    private val adapter = FavoriteAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        favoriteRv.adapter = adapter
        favoriteRv.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        MainScope().launch {
            adapter.setData(ImageInfoRepository.list())
        }
    }
}