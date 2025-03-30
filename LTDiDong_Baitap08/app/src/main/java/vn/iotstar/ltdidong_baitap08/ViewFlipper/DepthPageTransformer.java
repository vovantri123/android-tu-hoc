package vn.iotstar.ltdidong_baitap08.ViewFlipper;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity, -1]: Trang ở ngoài bên trái màn hình
            view.setAlpha(0f);
        } else if (position <= 0) { // [-1, 0]: Trang hiện tại
            view.setAlpha(1f);
            view.setTranslationX(0f);
            view.setTranslationZ(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (position <= 1) { // (0,1]: Trang kế tiếp
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);
            view.setTranslationZ(-1f);

            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else { // (1,+Infinity]: Trang ở ngoài bên phải màn hình
            view.setAlpha(0f);
        }
    }
}
