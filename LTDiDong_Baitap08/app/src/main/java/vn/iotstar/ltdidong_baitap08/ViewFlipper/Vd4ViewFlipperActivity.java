package vn.iotstar.ltdidong_baitap08.ViewFlipper;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import vn.iotstar.ltdidong_baitap08.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;

public class Vd4ViewFlipperActivity extends AppCompatActivity {

    private SliderView sliderView;
    private ArrayList<Integer> arrayList;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vd4_view_flipper);

        sliderView = findViewById(R.id.imageSlider);
        arrayList = new ArrayList<>();
        arrayList.add(R.drawable.caphe);
        arrayList.add(R.drawable.pizza);
        arrayList.add(R.drawable.quangcao);
        arrayList.add(R.drawable.themoingon);

        sliderAdapter = new SliderAdapter(this, arrayList);
        sliderView.setSliderAdapter(sliderAdapter);

        // Cấu hình slider
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.parseColor("#FF0000")); // red
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5);
        sliderView.startAutoCycle();
    }
}
