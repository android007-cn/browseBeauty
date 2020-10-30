package cn.cxy.browsebeauty

object FragmentFactory {
    fun getFragment(position: Int) = BaseFragment(position)
}