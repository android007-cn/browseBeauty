package cn.cxy.browsebeauty.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cn.cxy.browsebeauty.R
import cn.cxy.browsebeauty.db.bean.ImageInfo
import cn.cxy.browsebeauty.db.bean.MultiImageInfo
import com.bumptech.glide.Glide
import com.cxyzy.utils.ext.hide
import com.cxyzy.utils.ext.show
import kotlinx.android.synthetic.main.item_favorite_list.view.*

class FavoriteAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var mDataList = mutableListOf<MultiImageInfo>()
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mDataList[position]
        holder.itemView.iv1.hide()
        holder.itemView.iv2.hide()
        holder.itemView.iv3.hide()
        holder.itemView.iv4.hide()

        data.imageInfoList.getOrNull(0)?.let { showImage(it, holder.itemView.iv1) }
        data.imageInfoList.getOrNull(1)?.let { showImage(it, holder.itemView.iv2) }
        data.imageInfoList.getOrNull(2)?.let { showImage(it, holder.itemView.iv3) }
        data.imageInfoList.getOrNull(3)?.let { showImage(it, holder.itemView.iv4) }
    }

    private fun showImage(data: ImageInfo, view: ImageView) {
        Glide.with(mContext).load(data.path).into(view)
        view.setOnClickListener {
            mContext.startActivity(ImageActivity.buildIntent(mContext, data))
        }
        view.show()
    }

    fun setData(dataList: List<MultiImageInfo>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}