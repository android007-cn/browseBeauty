package cn.cxy.browsebeauty

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.cxy.browsebeauty.favorite.FavoriteActivity
import kotlinx.android.synthetic.main.mine_part_favorite.*

class MineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        favoriteLayout.setOnClickListener {
            startActivity(Intent(context, FavoriteActivity::class.java))
        }
    }
}