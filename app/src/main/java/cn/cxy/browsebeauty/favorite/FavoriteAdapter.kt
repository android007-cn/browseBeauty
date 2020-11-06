package cn.cxy.browsebeauty.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.bean.SelectImageInfo
import com.bumptech.glide.Glide
import com.cxyzy.utils.ext.show
import kotlinx.android.synthetic.main.item_favorite_list.view.*

class FavoriteAdapter(var selectionModeCallback: SelectionModeCallback) :
    RecyclerView.Adapter<ViewHolder>() {
    private var mDataList = mutableListOf<SelectImageInfo>()
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mDataList[position]
        holder.itemView.iv.visibility = INVISIBLE
        showImage(data, holder.itemView.iv, position)
        showImageSelectCheckbox(holder, data, position)
        holder.itemView.iv.setOnLongClickListener {
            selectionModeCallback.onEnterSelectionMode(position)
            true
        }
    }

    private fun showImage(data: SelectImageInfo, view: ImageView, positionInImageList: Int) {
        Glide.with(mContext).load(data.path).centerCrop().into(view)
        view.setOnClickListener {
            mContext.startActivity(
                ImageActivity.buildIntent(
                    mContext,
                    data.toImageInfo(),
                    positionInImageList
                )
            )
        }
        view.show()
    }

    private fun showImageSelectCheckbox(
        holder: RecyclerView.ViewHolder,
        data: SelectImageInfo,
        position: Int
    ) {
        val imageSelectCheckbox = holder.itemView.imageSelectCheckbox
        if (data.isSelected != null) {
            imageSelectCheckbox.isChecked = data.isSelected!!
            imageSelectCheckbox.show()
        } else {
            imageSelectCheckbox.show(false)
        }
        imageSelectCheckbox.setOnCheckedChangeListener { _, isChecked ->
            selectionModeCallback.onChecked(position, isChecked)
        }
    }

    fun setData(dataList: MutableList<SelectImageInfo>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}