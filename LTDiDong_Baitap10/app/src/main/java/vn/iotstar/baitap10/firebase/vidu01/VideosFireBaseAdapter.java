package vn.iotstar.baitap10.firebase.vidu01;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2; // Import ViewPager2

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoSize;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import vn.iotstar.baitap10.R; // Đảm bảo đúng R package

public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<Video1Model, VideosFireBaseAdapter.MyHolder> {

    private static final String TAG = "VideosFireBaseAdapter";
    private String currentUserId;
    private ViewPager2 viewPager; // Tham chiếu đến ViewPager2

    // Constructor nhận ViewPager2
    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions<Video1Model> options, @NonNull ViewPager2 viewPager) {
        super(options);
        this.viewPager = viewPager;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }
    }

    // --- ViewHolder class ---
    public class MyHolder extends RecyclerView.ViewHolder {
        PlayerView playerView;
        ExoPlayer player;
        ProgressBar videoProgressBar;
        TextView textVideoTitle, textVideoDescription, tvEmailVideo, txtLikeCount, txtDislikeCount;
        ImageView imPerson, favorites, dislike, imShare, imMore;
        Context context;
        ValueEventListener likeStatusListener;
        ValueEventListener dislikeStatusListener;
        DatabaseReference likesRefForListener;
        DatabaseReference dislikesRefForListener;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            // Ánh xạ view
            playerView = itemView.findViewById(R.id.playerView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites); // Like
            dislike = itemView.findViewById(R.id.dislike);    // Dislike
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
            tvEmailVideo = itemView.findViewById(R.id.tvEmailVideo);
            txtLikeCount = itemView.findViewById(R.id.txtLikeCount);
            txtDislikeCount = itemView.findViewById(R.id.txtDislikeCount);
        }

        // Hàm load avatar
        void loadUploaderAvatar(String uid) {
            if (uid == null || uid.isEmpty()) {
                Glide.with(context).load(R.drawable.ic_person_pin).circleCrop().into(imPerson);
                return;
            }
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("avatar");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String avatarUrl = snapshot.getValue(String.class);
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) { // Kiểm tra holder hợp lệ
                        if (avatarUrl != null && !avatarUrl.isEmpty()) {
                            Glide.with(context).load(avatarUrl).placeholder(R.drawable.ic_person_pin).error(R.drawable.ic_person_pin).circleCrop().into(imPerson);
                        } else {
                            Glide.with(context).load(R.drawable.ic_person_pin).circleCrop().into(imPerson);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Lỗi lấy avatar: " + error.getMessage());
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) { // Kiểm tra holder hợp lệ
                        Glide.with(context).load(R.drawable.ic_person_pin).circleCrop().into(imPerson);
                    }
                }
            });
        }

        // Cập nhật UI nút Like (Đảm bảo tên drawable đúng)
        void updateLikeButtonUI(boolean liked) {
            favorites.setImageResource(liked ? R.drawable.like_fill : R.drawable.like_outline);
        }

        // Cập nhật UI nút Dislike (Đảm bảo tên drawable đúng)
        void updateDislikeButtonUI(boolean disliked) {
            dislike.setImageResource(disliked ? R.drawable.dislike_fill : R.drawable.dislike_outline);
        }

        // Hủy các listener
        void detachListeners() {
            if (likeStatusListener != null && likesRefForListener != null) {
                likesRefForListener.removeEventListener(likeStatusListener);
            }
            if (dislikeStatusListener != null && dislikesRefForListener != null) {
                dislikesRefForListener.removeEventListener(dislikeStatusListener);
            }
            likeStatusListener = null;
            dislikeStatusListener = null;
            likesRefForListener = null;
            dislikesRefForListener = null;
        }

        // Giải phóng ExoPlayer
        void releasePlayer() {
            if (player != null) {
                player.stop();
                player.clearMediaItems();
                player.release();
                player = null;
            }
        }

        // Bắt đầu/Tiếp tục phát video
        public void playVideo() {
            if (player != null && player.getPlaybackState() == Player.STATE_ENDED) {
                // Nếu video đã kết thúc, tua về đầu trước khi phát lại
                player.seekTo(0);
            }
            if (player != null && player.getPlaybackState() != Player.STATE_READY) {
                // Chuẩn bị lại nếu chưa sẵn sàng (ví dụ sau khi pause/stop)
                player.prepare();
            }
            if (player != null) {
                player.setPlayWhenReady(true); // Yêu cầu phát
                player.play(); // Gọi play để chắc chắn
                Log.d(TAG, "playVideo() called for pos " + getBindingAdapterPosition());
            }
        }

        // Dừng phát video (Pause)
        public void pauseVideo() {
            if (player != null) {
                player.setPlayWhenReady(false); // Ngăn tự động phát khi trạng thái thay đổi
                player.pause();
                Log.d(TAG, "pauseVideo() called for pos " + getBindingAdapterPosition());
            }
        }

    } // --- Kết thúc class MyHolder ---

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull Video1Model model) {
        String videoId = getRef(position).getKey();
        if (videoId == null) {
            Log.e(TAG, "Video ID is null at initial bind for position " + position);
            // Set trạng thái mặc định và thoát
            holder.releasePlayer(); // Giải phóng player cũ nếu có
            holder.playerView.setPlayer(null);
            holder.videoProgressBar.setVisibility(View.GONE);
            holder.favorites.setEnabled(false);
            holder.dislike.setEnabled(false);
            holder.updateLikeButtonUI(false);
            holder.updateDislikeButtonUI(false);
            return;
        }
        DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference("videos").child(videoId);

        // --- Hiển thị thông tin cơ bản ---
        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDesc());
        holder.tvEmailVideo.setText(model.getEmail() != null ? model.getEmail() : "Unknown User");
        holder.txtLikeCount.setText(formatCount(model.getLikeCount()));
        holder.txtDislikeCount.setText(formatCount(model.getDislikeCount()));
        holder.loadUploaderAvatar(model.getUid());

        // --- Xử lý ExoPlayer ---
        try {
            holder.releasePlayer(); // Luôn giải phóng player cũ trước
            holder.player = new ExoPlayer.Builder(holder.context).build();
            holder.playerView.setPlayer(holder.player);

            Uri videoUri = Uri.parse(model.getUrl());
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            holder.player.setMediaItem(mediaItem);
            holder.videoProgressBar.setVisibility(View.VISIBLE);
            holder.player.prepare(); // Chỉ chuẩn bị
            holder.player.setRepeatMode(Player.REPEAT_MODE_ONE); // Lặp lại video

            holder.player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    int currentPos = holder.getBindingAdapterPosition();
                    if (currentPos == RecyclerView.NO_POSITION) return; // Bỏ qua nếu holder không hợp lệ

                    switch (playbackState) {
                        case Player.STATE_READY:
                            holder.videoProgressBar.setVisibility(View.GONE);
                            // *** KIỂM TRA VÀ PHÁT NẾU LÀ TRANG HIỆN TẠI ***
                            if (currentPos == viewPager.getCurrentItem()) {
                                holder.playVideo(); // Gọi hàm playVideo() thay vì player.play() trực tiếp
                                Log.d(TAG, "ExoPlayer state READY & playing for current page: " + currentPos);
                            } else {
                                holder.pauseVideo(); // Gọi hàm pauseVideo()
                                Log.d(TAG, "ExoPlayer state READY but paused for non-current page: " + currentPos);
                            }
                            // Điều chỉnh tỷ lệ khung hình
                            VideoSize videoSize = holder.player.getVideoSize();
                            if (videoSize.height != 0 && holder.playerView.getHeight() != 0) {
                                float videoRatio = videoSize.width / (float) videoSize.height;
                                float screenRatio = holder.playerView.getWidth() / (float) holder.playerView.getHeight();
                                float scale = videoRatio / screenRatio;
                                View surfaceView = holder.playerView.getVideoSurfaceView();
                                if(surfaceView instanceof SurfaceView){
                                    surfaceView.animate()
                                            .scaleX(scale >= 1f ? scale : 1f)
                                            .scaleY(scale < 1f ? (1f / scale) : 1f)
                                            .setDuration(0).start();
                                }
                            }
                            break;
                        case Player.STATE_BUFFERING:
                            holder.videoProgressBar.setVisibility(View.VISIBLE);
                            break;
                        default:
                            holder.videoProgressBar.setVisibility(View.GONE);
                            break;
                    }
                }
                @Override
                public void onPlayerError(@NonNull PlaybackException error) {
                    int currentPos = holder.getBindingAdapterPosition();
                    if (currentPos == RecyclerView.NO_POSITION) return;
                    Log.e(TAG, "ExoPlayer Error at pos " + currentPos + ": " + error.getMessage(), error);
                    Toast.makeText(holder.context, "Lỗi phát video", Toast.LENGTH_SHORT).show();
                    holder.videoProgressBar.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error initializing ExoPlayer for URL: " + model.getUrl(), e);
            Toast.makeText(holder.context, "Lỗi khởi tạo trình phát", Toast.LENGTH_SHORT).show();
            holder.videoProgressBar.setVisibility(View.GONE);
        }

        // --- Cập nhật trạng thái và gắn Listener cho nút Like/Dislike ---
        holder.detachListeners(); // Hủy listener cũ

        if (currentUserId != null) {
            holder.favorites.setEnabled(true);
            holder.dislike.setEnabled(true);

            holder.likesRefForListener = videoRef.child("likes").child(currentUserId);
            holder.dislikesRefForListener = videoRef.child("dislikes").child(currentUserId);

            // Listener cho LIKE
            holder.likeStatusListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int currentPos = holder.getBindingAdapterPosition();
                    if (currentPos != RecyclerView.NO_POSITION) {
                        holder.updateLikeButtonUI(snapshot.exists());
                    }
                }
                @Override public void onCancelled(@NonNull DatabaseError error) { Log.w(TAG, "Like listener cancelled", error.toException());}
            };
            holder.likesRefForListener.addValueEventListener(holder.likeStatusListener);

            // Listener cho DISLIKE
            holder.dislikeStatusListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int currentPos = holder.getBindingAdapterPosition();
                    if (currentPos != RecyclerView.NO_POSITION) {
                        holder.updateDislikeButtonUI(snapshot.exists());
                    }
                }
                @Override public void onCancelled(@NonNull DatabaseError error) { Log.w(TAG, "Dislike listener cancelled", error.toException());}
            };
            holder.dislikesRefForListener.addValueEventListener(holder.dislikeStatusListener);

        } else {
            // Chưa đăng nhập
            holder.favorites.setEnabled(false);
            holder.dislike.setEnabled(false);
            holder.updateLikeButtonUI(false);
            holder.updateDislikeButtonUI(false);
        }

        // --- Xử lý Click ---
        holder.favorites.setOnClickListener(v -> {
            if (currentUserId == null) { Toast.makeText(holder.context,"Vui lòng đăng nhập",Toast.LENGTH_SHORT).show(); return; }
            int currentPos = holder.getBindingAdapterPosition();
            if(currentPos != RecyclerView.NO_POSITION){
                String currentVideoId = getRef(currentPos).getKey();
                if(currentVideoId != null){
                    processLikeDislike(FirebaseDatabase.getInstance().getReference("videos").child(currentVideoId), currentUserId, true);
                } else { Log.e(TAG, "Like click: videoId is null at pos "+currentPos);}
            } else { Log.e(TAG, "Like click: holder position is NO_POSITION");}
        });

        holder.dislike.setOnClickListener(v -> {
            if (currentUserId == null) { Toast.makeText(holder.context,"Vui lòng đăng nhập",Toast.LENGTH_SHORT).show(); return; }
            int currentPos = holder.getBindingAdapterPosition();
            if(currentPos != RecyclerView.NO_POSITION){
                String currentVideoId = getRef(currentPos).getKey();
                if(currentVideoId != null){
                    processLikeDislike(FirebaseDatabase.getInstance().getReference("videos").child(currentVideoId), currentUserId, false);
                } else { Log.e(TAG, "Dislike click: videoId is null at pos "+currentPos);}
            } else { Log.e(TAG, "Dislike click: holder position is NO_POSITION");}
        });
    } // --- Kết thúc onBindViewHolder ---

    // Hàm xử lý Transaction cho Like/Dislike
    private void processLikeDislike(DatabaseReference postRef, String userId, boolean isLikeOperation) {
        postRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Video1Model currentVideo = mutableData.getValue(Video1Model.class);
                if (currentVideo == null) return Transaction.abort();
                MutableData likesNode = mutableData.child("likes");
                MutableData dislikesNode = mutableData.child("dislikes");
                MutableData likeCountNode = mutableData.child("likeCount");
                MutableData dislikeCountNode = mutableData.child("dislikeCount");
                long currentLikes = likeCountNode.getValue(Long.class) != null ? likeCountNode.getValue(Long.class) : 0L;
                long currentDislikes = dislikeCountNode.getValue(Long.class) != null ? dislikeCountNode.getValue(Long.class) : 0L;
                boolean userHasLiked = likesNode.hasChild(userId);
                boolean userHasDisliked = dislikesNode.hasChild(userId);

                if (isLikeOperation) { // Like
                    if (userHasLiked) { // Bỏ like
                        currentLikes--; likesNode.child(userId).setValue(null);
                    } else { // Thêm like
                        currentLikes++; likesNode.child(userId).setValue(true);
                        if (userHasDisliked) { // Bỏ dislike cũ
                            currentDislikes--; dislikesNode.child(userId).setValue(null);
                        }
                    }
                } else { // Dislike
                    if (userHasDisliked) { // Bỏ dislike
                        currentDislikes--; dislikesNode.child(userId).setValue(null);
                    } else { // Thêm dislike
                        currentDislikes++; dislikesNode.child(userId).setValue(true);
                        if (userHasLiked) { // Bỏ like cũ
                            currentLikes--; likesNode.child(userId).setValue(null);
                        }
                    }
                }
                likeCountNode.setValue(Math.max(0, currentLikes));
                dislikeCountNode.setValue(Math.max(0, currentDislikes));
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot snapshot) {
                if (error != null) Log.e(TAG, "Transaction failed: " + error.getMessage());
                // Không cần log thành công/thất bại ở đây, trừ khi debug
            }
        });
    }

    // Hàm hủy listener và giải phóng player khi view bị recycle
    @Override
    public void onViewRecycled(@NonNull MyHolder holder) {
        super.onViewRecycled(holder);
        holder.detachListeners(); // Hủy DB listeners
        holder.releasePlayer();  // Giải phóng ExoPlayer
    }

    // Hàm formatCount
    private String formatCount(long count) {
        if (count < 1000) return String.valueOf(count);
        else if (count < 1_000_000) return String.format(Locale.getDefault(), "%.1fK", count / 1000.0).replace(".0K", "K");
        else return String.format(Locale.getDefault(), "%.1fM", count / 1_000_000.0).replace(".0M", "M");
    }
}