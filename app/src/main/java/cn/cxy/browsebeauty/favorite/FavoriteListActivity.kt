package cn.cxy.browsebeauty.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.bean.SelectImageInfo
import cn.cxy.browsebeauty.db.repository.ImageInfoRepository
import cn.cxy.browsebeauty.utils.ImageUtil
import com.cxyzy.utils.ext.hide
import com.cxyzy.utils.ext.show
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class FavoriteListActivity : AppCompatActivity(), SelectionModeCallback {
    private val mAdapter = FavoriteAdapter(this)
    private val mSelectImageInfoList = mutableListOf<SelectImageInfo>()
    private var isInSelectionMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        initRecyclerView()
        initListeners()
    }

    private fun initRecyclerView() {
        favoriteRv.adapter = mAdapter
        favoriteRv.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
    }

    private fun initListeners() {
        favoriteIcon.setOnClickListener {
            MainScope().launch {
                mSelectImageInfoList.filter { it.isSelected == true }
                    .forEach {
                        ImageUtil.deleteFile(it.path)
                        ImageInfoRepository.del(it.url)
                    }
                mAdapter.setData(mSelectImageInfoList)
                mAdapter.notifyDataSetChanged()
            }
        }
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
        isInSelectionMode = true
        mSelectImageInfoList.forEach { it.isSelected = false }
        mSelectImageInfoList[touchedItemPosition].isSelected = true
        mAdapter.notifyDataSetChanged()
        imageBottomBar.show()
    }

    override fun onChecked(touchedItemPosition: Int, isChecked: Boolean) {
        mSelectImageInfoList[touchedItemPosition].isSelected = isChecked
    }

    private fun onExitSelectionMode() {
        isInSelectionMode = false
        mSelectImageInfoList.forEach { it.isSelected = null }
        mAdapter.notifyDataSetChanged()
        imageBottomBar.hide()
    }

    override fun onBackPressed() {
        if (isInSelectionMode) {
            onExitSelectionMode()
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}

interface SelectionModeCallback {
    fun onEnterSelectionMode(touchedItemPosition: Int)
    fun onChecked(touchedItemPosition: Int, isChecked: Boolean)
}