package vn.iotstar.ltdidong_baitap08.BaiTap;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ltdidong_baitap08.R;
import vn.iotstar.ltdidong_baitap08.ViewFlipper.ImagesViewPageAdapter;

public class BaitapActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<ImagesSlider> imagesList;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baitap);

        viewPager = findViewById(R.id.viewpage);
        circleIndicator = findViewById(R.id.circle_indicator);

        fetchImages();
    }

    private void fetchImages() {
        ApiService apiService = RetrofitClient.getInstance();
        apiService.getImages("1").enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    imagesList = response.body().getResult();
                    Log.d("API_RESPONSE", "Số ảnh nhận được: " + imagesList.size());

                    if (!imagesList.isEmpty()) {
                        ImageSliderAdapter adapter = new ImageSliderAdapter(BaitapActivity.this, imagesList);
                        viewPager.setAdapter(adapter);
                        circleIndicator.setViewPager(viewPager);

                        autoSlideImages();
                    } else {
                        Log.e("API_RESPONSE", "Không có ảnh nào để hiển thị!");
                    }
                } else {
                    Log.e("API_RESPONSE", "Lỗi response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to load images: " + t.getMessage());
            }
        });
    }

    private void autoSlideImages() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem() == imagesList.size() - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
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