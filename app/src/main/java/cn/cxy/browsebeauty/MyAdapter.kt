package cn.cxy.browsebeauty

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int) = FragmentFactory.getFragment(position)

    override fun getItemCount() = Integer.MAX_VALUE
}