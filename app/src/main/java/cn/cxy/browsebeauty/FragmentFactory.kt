package cn.cxy.browsebeauty

object FragmentFactory {
    fun getFragment(url: String) = BaseFragment(url)
}