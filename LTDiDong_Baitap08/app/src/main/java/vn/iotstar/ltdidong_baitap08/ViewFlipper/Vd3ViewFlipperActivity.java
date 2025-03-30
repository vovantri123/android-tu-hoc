package vn.iotstar.ltdidong_baitap08.ViewFlipper;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import vn.iotstar.ltdidong_baitap08.R;

public class Vd3ViewFlipperActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Images> imagesList1;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vd3_view_flipper);

        // Ánh xạ viewPager2
        viewPager2 = findViewById(R.id.viewpage2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);

        imagesList1 = getListImages();
        ImagesViewPager2Adapter adapter1 = new ImagesViewPager2Adapter(imagesList1);
        viewPager2.setAdapter(adapter1);

        // Liên kết viewPager2 và indicator3
        circleIndicator3.setViewPager(viewPager2);

        // Auto-run
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() == imagesList1.size() - 1) {
                    viewPager2.setCurrentItem(0);
                } else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
                handler.postDelayed(runnable, 3000);
            }
        };

        handler.postDelayed(runnable, 3000);

        // Lắng nghe viewPager2 chuyển trang
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        // ViewPager2 transformers
        viewPager2.setPageTransformer(new DepthPageTransformer());
    }

    private List<Images> getListImages() {
        List<Images> list = new ArrayList<>();
        list.add(new Images(R.drawable.quangcao));
        list.add(new Images(R.drawable.caphe));
        list.add(new Images(R.drawable.pizza));
        list.add(new Images(R.drawable.themoingon));
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}
