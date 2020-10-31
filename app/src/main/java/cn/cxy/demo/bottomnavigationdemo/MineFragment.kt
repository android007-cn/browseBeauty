package cn.cxy.demo.bottomnavigationdemo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class MineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tv = TextView(context)
        tv.text = "第二页"
        tv.gravity = Gravity.CENTER
        return tv
    }
}