package cn.cxy.browsebeauty.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.bean.SelectImageInfo
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class FavoriteListActivity : AppCompatActivity(), SelectionModeCallback {
    private val mAdapter = FavoriteAdapter(this)
    private val mSelectImageInfoList = mutableListOf<SelectImageInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        favoriteRv.adapter = mAdapter
        favoriteRv.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        MainScope().launch {
            val imageInfoList = ImageInfoRepository.list()
            imageInfoList.forEach {
                mSelectImageInfoList.add(SelectImageInfo.fromImageInfo(it))
            }
            mAdapter.setData(mSelectImageInfoList)
        }
    }

    override fun onEnterSelectionMode(touchedItemPosition: Int) {
        mSelectImageInfoList.forEach {
            it.isSelected = false
        }
        mSelectImageInfoList[touchedItemPosition].isSelected = true
        mAdapter.notifyDataSetChanged()
    }
}

interface SelectionModeCallback {
    fun onEnterSelectionMode(touchedItemPosition: Int)
}
