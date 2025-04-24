package vn.iotstar.baitap10.firebase.vidu01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // Import cho nút Back
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.iotstar.baitap10.R;

public class ProfileActivity extends AppCompatActivity {

    // Khai báo các View elements
    ImageView imPerson;
    TextView tvEmail, videoCount;
    Button btnUploadVideoProfile;
    EditText etAvatarUrl;
    Button btnUpdateAvatarUrl;
    ImageButton btnBackProfile; // Nút Back

    // Firebase và các biến khác
    FirebaseUser user;
    ProgressDialog progressDialog;
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các View elements từ layout
        imPerson = findViewById(R.id.imgAvatar);
        tvEmail = findViewById(R.id.txtEmail);
        videoCount = findViewById(R.id.txtVideoCount);
        btnUploadVideoProfile = findViewById(R.id.btnUploadVideoProfile);
        etAvatarUrl = findViewById(R.id.etAvatarUrl);
        btnUpdateAvatarUrl = findViewById(R.id.btnUpdateAvatarUrl);
        btnBackProfile = findViewById(R.id.btnBackProfile); // Ánh xạ nút Back

        // Khởi tạo ProgressDialog và lấy thông tin User hiện tại
        progressDialog = new ProgressDialog(ProfileActivity.this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Load thông tin ban đầu của người dùng
        if (user != null) {
            loadUserProfile();
            countVideo();
        } else {
            // Xử lý trường hợp không có người dùng đăng nhập (ví dụ: quay về Login)
            Toast.makeText(this, "Người dùng chưa đăng nhập!", Toast.LENGTH_LONG).show();
            // Intent intent = new Intent(this, LoginActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(intent);
            finish(); // Đóng activity này lại
            return; // Ngăn không chạy code tiếp theo
        }

        // --- Thiết lập Listener cho các nút ---

        // Sự kiện click nút Upload Video
        btnUploadVideoProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UploadActivity.class);
            startActivity(intent);
        });

        // Sự kiện click nút Cập nhật URL Avatar
        btnUpdateAvatarUrl.setOnClickListener(v -> {
            String imageUrl = etAvatarUrl.getText().toString().trim();
            if (isValidUrl(imageUrl)) {
                // Hiển thị dialog chờ
                progressDialog.setMessage("Đang cập nhật avatar...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                // Gọi hàm cập nhật
                updateFirebaseProfilePhoto(imageUrl);
            } else {
                Toast.makeText(ProfileActivity.this, "Vui lòng nhập URL hình ảnh hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        // Sự kiện click nút Back
        btnBackProfile.setOnClickListener(v -> {
            // Kết thúc Activity hiện tại để quay lại Activity trước đó (MainActivityFirebase)
            finish();
        });
    }

    // Hàm load thông tin người dùng (Email và Avatar)
    private void loadUserProfile() {
        // Đảm bảo user không null (đã kiểm tra ở onCreate nhưng kiểm tra lại cho chắc)
        if (user == null) return;

        // Hiển thị Email
        String email = user.getEmail();
        tvEmail.setText(email != null ? email : "N/A");

        // Load Avatar: Ưu tiên từ Firebase Auth, sau đó đến Realtime DB, cuối cùng là ảnh mặc định
        Uri photoUrl = user.getPhotoUrl();
        if (photoUrl != null) {
            Log.d(TAG, "Loading avatar from Firebase Auth URL: " + photoUrl.toString());
            displayAvatar(photoUrl.toString());
        } else {
            // Thử lấy từ Realtime Database
            DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("avatar");
            userDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String dbAvatarUrl = snapshot.getValue(String.class);
                    if (dbAvatarUrl != null && !dbAvatarUrl.isEmpty()) {
                        Log.d(TAG, "Loading avatar from Realtime DB URL: " + dbAvatarUrl);
                        displayAvatar(dbAvatarUrl);
                    } else {
                        Log.d(TAG, "No avatar URL found in Auth or DB, loading default.");
                        displayAvatar(null); // Hiển thị ảnh mặc định
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Error reading avatar from DB", error.toException());
                    displayAvatar(null); // Hiển thị ảnh mặc định nếu lỗi
                }
            });
        }
    }

    // Hàm hiển thị Avatar bằng Glide (tách ra để tái sử dụng)
    private void displayAvatar(@Nullable String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(ProfileActivity.this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_person_pin) // Ảnh chờ
                    .error(R.drawable.ic_person_pin)     // Ảnh lỗi
                    .circleCrop()                        // Hiển thị ảnh tròn
                    .into(imPerson);
        } else {
            // Load ảnh mặc định
            Glide.with(ProfileActivity.this)
                    .load(R.drawable.ic_person_pin)
                    .circleCrop()
                    .into(imPerson);
        }
    }


    // Hàm cập nhật avatar vào Firebase Auth VÀ Realtime DB từ URL
    private void updateFirebaseProfilePhoto(String photoUrl) {
        if (user == null) {
            Log.w(TAG, "updateFirebaseProfilePhoto: User is null.");
            if (progressDialog.isShowing()) progressDialog.dismiss();
            Toast.makeText(this, "Lỗi: Không tìm thấy người dùng.", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri photoUri = Uri.parse(photoUrl); // Chuyển String URL thành Uri
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(photoUri)
                .build();

        // Bước 1: Cập nhật trên Firebase Authentication
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated successfully in Firebase Auth.");

                        // Bước 2: Cập nhật trên Realtime Database
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                        userRef.child("avatar").setValue(photoUrl)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Log.d(TAG, "Avatar URL updated successfully in Realtime DB.");
                                    } else {
                                        // Vẫn thông báo thành công vì Auth đã cập nhật, nhưng log lỗi DB
                                        Log.w(TAG, "Failed to update avatar URL in Realtime DB.", dbTask.getException());
                                        Toast.makeText(ProfileActivity.this, "Cập nhật Auth thành công, lỗi cập nhật DB.", Toast.LENGTH_SHORT).show();
                                    }
                                    // Ẩn dialog chờ sau khi hoàn tất (kể cả khi DB lỗi)
                                    if (progressDialog.isShowing()) progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Avatar đã cập nhật!", Toast.LENGTH_SHORT).show();
                                    // Hiển thị ảnh mới lên ImageView
                                    displayAvatar(photoUrl);
                                    // Xóa nội dung EditText sau khi cập nhật thành công
                                    etAvatarUrl.setText("");
                                });
                    } else {
                        // Lỗi cập nhật trên Firebase Authentication
                        Log.w(TAG, "Failed to update user profile in Firebase Auth.", task.getException());
                        if (progressDialog.isShowing()) progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Lỗi cập nhật avatar: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Hàm đếm số lượng video đã đăng của người dùng
    private void countVideo() {
        // Đảm bảo user không null
        if (user == null) return;

        DatabaseReference videosRef = FirebaseDatabase.getInstance().getReference("videos");
        // Query các video có 'uid' khớp với UID của người dùng hiện tại
        videosRef.orderByChild("uid").equalTo(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() { // Chỉ đọc 1 lần
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // snapshot.getChildrenCount() trả về số lượng bản ghi khớp query
                        videoCount.setText(String.valueOf(snapshot.getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Lỗi đếm video: " + error.getMessage());
                        videoCount.setText("Lỗi"); // Hiển thị lỗi trên UI
                    }
                });
    }

    // Hàm kiểm tra xem chuỗi có phải là URL hợp lệ hay không (sử dụng Patterns)
    private boolean isValidUrl(String url) {
        // Kiểm tra null/rỗng và khớp với pattern URL chuẩn
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }

    // Hàm này không còn cần thiết vì không xin quyền nữa
    // @Override
    // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { ... }

    // Hàm này không còn cần thiết vì không chọn ảnh từ bộ nhớ nữa
    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { ... }
}