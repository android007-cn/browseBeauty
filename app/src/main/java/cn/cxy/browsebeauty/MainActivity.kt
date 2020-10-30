package cn.cxy.browsebeauty

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vp2 = findViewById<View>(R.id.vp2) as ViewPager2
        //设置上下滑动
        vp2.orientation = ViewPager2.ORIENTATION_VERTICAL
        vp2.adapter = MyAdapter(this)
    }
}