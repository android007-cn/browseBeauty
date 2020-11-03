package cn.cxy.browsebeauty

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(fragmentActivity: FragmentActivity, var urlList: MutableList<String>) :
    FragmentStateAdapter(fragmentActivity) {
    private var fragments = mutableListOf<ImageFragment>()

    override fun createFragment(position: Int): ImageFragment {
        val fragment = ImageFragment(urlList[position])
        fragments.add(fragment)
        return fragment
    }

    override fun getItemCount() = urlList.size

    fun getFragment(index: Int) = fragments[index]
}