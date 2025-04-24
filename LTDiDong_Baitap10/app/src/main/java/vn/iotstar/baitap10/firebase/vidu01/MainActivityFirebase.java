package vn.iotstar.baitap10.firebase.vidu01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View; // Thêm nếu chưa có
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.iotstar.baitap10.R;  

public class MainActivityFirebase extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosFireBaseAdapter videosAdapter;
    private ImageButton btnProfile;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivityFirebase";
    private int currentPage = 0; // Khởi tạo trang hiện tại
    private VideosFireBaseAdapter.MyHolder currentViewHolder = null;
    private Handler mainHandler;
    private Runnable playRunnable = null; // Runnable để trì hoãn play
    private boolean isActivityResumed = false; // Cờ kiểm tra trạng thái resume

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_firebase);

        mAuth = FirebaseAuth.getInstance();
        mainHandler = new Handler(Looper.getMainLooper());

        viewPager2 = findViewById(R.id.vpager);
        btnProfile = findViewById(R.id.btnProfile);

        if (mAuth.getCurrentUser() == null) {
            navigateToLogin();
            return;
        }
        loadUserProfile();
        setupVideoPager();

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityFirebase.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setupVideoPager() {
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference("videos");
        FirebaseRecyclerOptions<Video1Model> options =
                new FirebaseRecyclerOptions.Builder<Video1Model>()
                        // Sắp xếp theo Key để thứ tự ổn định hơn, có thể đổi nếu muốn sort khác
                        .setQuery(mDataBase.orderByKey(), Video1Model.class)
                        .build();

        // Truyền viewPager2 vào constructor của Adapter
        videosAdapter = new VideosFireBaseAdapter(options, viewPager2);

        viewPager2.setAdapter(videosAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        // Callback để quản lý phát video khi chuyển trang
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (!isActivityResumed) { // Chỉ cập nhật vị trí nếu chưa resume
                    currentPage = position;
                    return;
                }
                Log.d(TAG, "Page selected: " + position);

                if (playRunnable != null) mainHandler.removeCallbacks(playRunnable);

                // Dừng video cũ
                if (currentViewHolder != null && currentPage != position) {
                    currentViewHolder.pauseVideo();
                    Log.d(TAG, "Paused video at previous page: " + currentPage);
                }
                currentPage = position; // Cập nhật trang hiện tại

                // Lên lịch phát video mới (có thể bỏ delay nếu muốn)
                playRunnable = () -> {
                    RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
                    if (recyclerView != null) {
                        VideosFireBaseAdapter.MyHolder newViewHolder = (VideosFireBaseAdapter.MyHolder) recyclerView.findViewHolderForAdapterPosition(currentPage);
                        if (newViewHolder != null) {
                            Log.d(TAG, "Attempting to play video at new page: " + currentPage);
                            newViewHolder.playVideo();
                            currentViewHolder = newViewHolder;
                        } else {
                            Log.w(TAG, "Could not find ViewHolder for selected page: " + currentPage + " after delay. Resetting holder.");
                            currentViewHolder = null;
                        }
                    }
                    playRunnable = null;
                };
                mainHandler.post(playRunnable); // Thử không delay
                // mainHandler.postDelayed(playRunnable, 50);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (!isActivityResumed) return;

                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    if (playRunnable != null) {
                        mainHandler.removeCallbacks(playRunnable);
                        playRunnable = null;
                    }
                    if (currentViewHolder != null) {
                        currentViewHolder.pauseVideo();
                        Log.d(TAG, "Scrolling started, pausing video at page: " + currentPage);
                    }
                }
                // Tùy chọn: Phát lại video khi dừng cuộn và đang ở đúng trang
                else if (state == ViewPager2.SCROLL_STATE_IDLE && currentViewHolder != null && currentPage == viewPager2.getCurrentItem()) {
                    Log.d(TAG, "Scrolling idle, resuming video at page: " + currentPage);
                    currentViewHolder.playVideo();
                }
            }
        });

        // Không gọi startListening ở đây
    }

    // Load thông tin user (Avatar)
    private void loadUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Uri photoUrl = currentUser.getPhotoUrl();
            if (photoUrl != null) {
                Glide.with(this).load(photoUrl).placeholder(R.drawable.ic_person_pin).error(R.drawable.ic_person_pin).circleCrop().into(btnProfile);
            } else {
                DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("avatar");
                userDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String dbUrl = snapshot.getValue(String.class);
                        if (dbUrl != null && !dbUrl.isEmpty()) {
                            Glide.with(MainActivityFirebase.this).load(dbUrl).placeholder(R.drawable.ic_person_pin).error(R.drawable.ic_person_pin).circleCrop().into(btnProfile);
                        } else {
                            Glide.with(MainActivityFirebase.this).load(R.drawable.ic_person_pin).circleCrop().into(btnProfile);
                        }
                    }
                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Glide.with(MainActivityFirebase.this).load(R.drawable.ic_person_pin).circleCrop().into(btnProfile);
                    }
                });
            }
        } else {
            Glide.with(this).load(R.drawable.ic_person_pin).circleCrop().into(btnProfile);
        }
    }

    // Chuyển về màn hình Login
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginAndSignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // --- Vòng đời Activity ---

    @Override
    protected void onResume() {
        super.onResume();
        isActivityResumed = true;
        Log.d(TAG, "onResume called - Activity is resumed");

        // Trì hoãn startListening và playback
        mainHandler.post(() -> {
            if (videosAdapter != null) {
                videosAdapter.startListening();
                Log.d(TAG, "Adapter started listening post-onResume");
                // Kích hoạt playback sau khi adapter đã nghe
                mainHandler.post(() -> triggerPlaybackForCurrentPage());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityResumed = false;
        Log.d(TAG, "onPause called - Activity is paused");

        // Hủy callbacks
        mainHandler.removeCallbacksAndMessages(null);
        if (playRunnable != null) {
            mainHandler.removeCallbacks(playRunnable);
            playRunnable = null;
        }

        // Dừng video
        if (currentViewHolder != null) {
            currentViewHolder.pauseVideo();
            Log.d(TAG, "onPause - Paused video at page: " + currentPage);
        }
        // Dừng adapter
        if (videosAdapter != null) {
            videosAdapter.stopListening();
            Log.d(TAG, "Adapter stopped listening in onPause");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        // Giải phóng player
        if (currentViewHolder != null) {
            currentViewHolder.releasePlayer();
            currentViewHolder = null;
        }
        mainHandler.removeCallbacksAndMessages(null); // Dọn dẹp handler
    }

    // Hàm kích hoạt phát lại cho trang hiện tại
    private void triggerPlaybackForCurrentPage() {
        if (!isActivityResumed) return; // Chỉ chạy khi đang resume

        Log.d(TAG, "Attempting to trigger playback for current page: " + currentPage);
        RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
        if (recyclerView != null && currentPage != -1) {
            VideosFireBaseAdapter.MyHolder holderToPlay = (VideosFireBaseAdapter.MyHolder) recyclerView.findViewHolderForAdapterPosition(currentPage);
            if (holderToPlay != null) {
                // Dừng holder cũ nếu nó khác holder mới
                if (currentViewHolder != null && currentViewHolder != holderToPlay) {
                    currentViewHolder.pauseVideo();
                }
                currentViewHolder = holderToPlay; // Cập nhật holder hiện tại
                currentViewHolder.playVideo();
                Log.d(TAG, "Successfully triggered playback for page: " + currentPage);
            } else {
                // Đôi khi findViewHolderForAdapterPosition trả về null ngay sau khi resume
                // Lên lịch thử lại sau một khoảng ngắn
                Log.w(TAG, "triggerPlayback - Could not find ViewHolder for page: " + currentPage + ". Retrying soon...");
                mainHandler.postDelayed(this::triggerPlaybackForCurrentPage, 100); // Thử lại sau 100ms
                // currentViewHolder = null; // Không reset ở đây vội
            }
        } else if (currentPage == -1) {
            Log.d(TAG, "triggerPlayback - No current page selected yet (currentPage = -1).");
        }
    }

} // --- Kết thúc Class Activity ---