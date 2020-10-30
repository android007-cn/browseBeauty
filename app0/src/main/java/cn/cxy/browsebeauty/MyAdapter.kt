package cn.cxy.browsebeauty

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(
    fragmentActivity: FragmentActivity,
    var urlList: MutableList<String>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int) = BaseFragment(urlList[position])

    override fun getItemCount() = urlList.size
}