package cn.cxy.browsebeauty.vptrans;

import android.util.Log;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class AlphaScaleTransformer2 implements ViewPager2.PageTransformer {

    float MIN_ALPHA = 0.1f;

    @Override
    public void transformPage(View page, float position) {
        Log.e((String) page.getTag(), position + "");

        if (position < -1 || position > 1) {
            page.setAlpha(0f);
        } else {
            if (-1 <= position && position < 0) {
                page.setAlpha(1 + position - MIN_ALPHA * position);
            } else if (0 < position && position <= 1) {
                page.setAlpha(1 - position + MIN_ALPHA * position);
            } else {
                page.setAlpha(1f);
            }
        }
    }
}