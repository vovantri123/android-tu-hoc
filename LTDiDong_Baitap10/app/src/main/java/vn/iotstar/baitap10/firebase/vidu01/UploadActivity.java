package vn.iotstar.baitap10.firebase.vidu01;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils; // Thêm import
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import vn.iotstar.baitap10.R; // Đảm bảo đúng R package

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 100;
    private Uri videoUri; // Lưu trữ Uri của video đã chọn

    Button btnAction; // Đổi tên nút cho rõ ràng hơn
    EditText txtTitle, txtDesc;

    Cloudinary cloudinary;
    DatabaseReference firebaseDB;
    FirebaseAuth mAuth; // Thêm FirebaseAuth
    ProgressDialog progressDialog;
    private static final String TAG = "UploadActivity"; // Thêm TAG

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sử dụng layout của bạn, ví dụ: upload_activity.xml
        setContentView(R.layout.upload_video); // Đảm bảo đúng tên layout file

        // --- KIỂM TRA ĐĂNG NHẬP ---
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Vui lòng đăng nhập để upload video", Toast.LENGTH_SHORT).show();
            // Chuyển về màn hình Login nếu muốn
            // Intent loginIntent = new Intent(this, LoginActivity.class);
            // loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(loginIntent);
            finish(); // Đóng activity upload
            return; // Ngăn code dưới chạy
        }
        // --- KẾT THÚC KIỂM TRA ---

        // Ánh xạ View
        btnAction = findViewById(R.id.btnOpenAndUpload); // Đảm bảo ID nút đúng
        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        progressDialog = new ProgressDialog(UploadActivity.this);

        // Firebase Realtime Database reference đến node "videos"
        firebaseDB = FirebaseDatabase.getInstance().getReference("videos");

        // Cloudinary config (Đảm bảo thông tin chính xác)
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dc7swmu59", // Thay bằng cloud_name của bạn
                "api_key", "331483385352912",    // Thay bằng api_key của bạn
                "api_secret", "ism1zMupZTw0MfHDv72PqcDtPOU" // Thay bằng api_secret của bạn
        ));

        // Thiết lập trạng thái ban đầu của nút
        btnAction.setText("Chọn Video");

        // Xử lý sự kiện click nút
        btnAction.setOnClickListener(v -> {
            // Kiểm tra tiêu đề và mô tả
            String title = txtTitle.getText().toString().trim();
            String desc = txtDesc.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                Toast.makeText(UploadActivity.this, "Vui lòng nhập tiêu đề video", Toast.LENGTH_SHORT).show();
                txtTitle.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(desc)) {
                Toast.makeText(UploadActivity.this, "Vui lòng nhập mô tả video", Toast.LENGTH_SHORT).show();
                txtDesc.requestFocus();
                return;
            }

            // Nếu chưa chọn video (videoUri là null) -> Mở bộ chọn video
            if (videoUri == null) {
                checkPermissionAndPickVideo();
            } else {
                // Nếu đã chọn video -> Bắt đầu upload
                uploadVideoToCloudinary(videoUri, title, desc);
            }
        });
    }

    // Mở trình chọn video của hệ thống
    private void openVideoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "Chọn video"), PICK_VIDEO_REQUEST);
    }

    // Xử lý kết quả trả về từ trình chọn video
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData(); // Lưu Uri của video đã chọn
            Log.d(TAG, "Video selected: " + videoUri.toString());
            // Đổi text nút để người dùng biết cần nhấn lần nữa để upload
            btnAction.setText("Upload Video");
            Toast.makeText(this, "Đã chọn video. Nhấn 'Upload Video' để tải lên.", Toast.LENGTH_LONG).show();
        } else {
            // Nếu người dùng hủy chọn hoặc có lỗi
            videoUri = null; // Reset Uri
            btnAction.setText("Chọn Video"); // Reset text nút
        }
    }

    // Upload video lên Cloudinary và sau đó lưu vào Firebase
    private void uploadVideoToCloudinary(Uri uri, String title, String desc) {
        // Hiển thị dialog chờ
        progressDialog.setMessage("Đang upload video...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Thực hiện upload trên một luồng khác để không chặn UI thread
        new Thread(() -> {
            try {
                // Lấy đường dẫn thực từ Uri (Cần xử lý lỗi nếu hàm này trả về null)
                String realPath = getRealPathFromURI(uri);
                if (realPath == null) {
                    throw new Exception("Không thể lấy đường dẫn thực của video từ Uri: " + uri.toString());
                }
                File file = new File(realPath);
                if (!file.exists()) {
                    throw new Exception("File không tồn tại tại đường dẫn: " + realPath);
                }

                Log.d(TAG, "Starting Cloudinary upload for file: " + realPath);
                // Upload file lên Cloudinary
                Map uploadResult = cloudinary.uploader().upload(file,
                        ObjectUtils.asMap("resource_type", "video")); // Chỉ định loại tài nguyên là video

                // Lấy URL an toàn (https) của video đã upload
                String videoUrl = (String) uploadResult.get("secure_url");
                if (videoUrl == null || videoUrl.isEmpty()) {
                    throw new Exception("Cloudinary không trả về URL sau khi upload.");
                }
                Log.d(TAG, "Cloudinary upload successful. URL: " + videoUrl);

                // Lấy thông tin người dùng hiện tại để lưu vào Firebase
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // Gọi hàm lưu thông tin vào Firebase
                    saveVideoToFirebase(title, desc, videoUrl, user);

                    // Cập nhật UI trên luồng chính sau khi lưu Firebase (hoặc ngay sau khi upload Cloudinary thành công)
                    runOnUiThread(() -> {
                        progressDialog.dismiss(); // Ẩn dialog
                        Toast.makeText(UploadActivity.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                        // Reset trạng thái để có thể upload video khác
                        videoUri = null;
                        txtTitle.setText("");
                        txtDesc.setText("");
                        btnAction.setText("Chọn Video");
                        // Có thể đóng Activity này nếu muốn
                        // finish();
                    });
                } else {
                    // Trường hợp user bị null (khó xảy ra nếu đã check ở onCreate)
                    throw new Exception("Người dùng không hợp lệ khi lưu vào Firebase.");
                }

            } catch (Exception e) {
                // Xử lý lỗi nếu có
                Log.e(TAG, "Upload failed", e);
                runOnUiThread(() -> {
                    progressDialog.dismiss(); // Luôn ẩn dialog khi có lỗi
                    Toast.makeText(UploadActivity.this, "Lỗi upload: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    // Có thể reset lại nút thành "Chọn Video" nếu upload lỗi
                    // videoUri = null;
                    // btnAction.setText("Chọn Video");
                });
            }
        }).start(); // Bắt đầu luồng upload
    }

    // Lưu thông tin video vào Firebase Realtime Database
    private void saveVideoToFirebase(String title, String desc, String url, FirebaseUser user) {
        String id = firebaseDB.push().getKey(); // Tạo ID duy nhất cho video

        // Kiểm tra xem ID và User có hợp lệ không
        if (id == null || user == null) {
            Log.e(TAG, "Cannot save to Firebase: Invalid video ID or User");
            // Không nên tiếp tục nếu thiếu thông tin cơ bản
            // Có thể hiển thị Toast lỗi ở đây nếu cần, nhưng lỗi upload thường đã được báo trước đó
            return;
        }

        // Tạo đối tượng Map để lưu dữ liệu
        Map<String, Object> videoData = new HashMap<>();
        videoData.put("title", title);
        videoData.put("desc", desc);
        videoData.put("url", url); // URL từ Cloudinary
        videoData.put("likeCount", 0L); // Khởi tạo số lượt thích là 0 (Long)
        videoData.put("dislikeCount", 0L); // Khởi tạo số lượt không thích là 0 (Long)
        videoData.put("uid", user.getUid()); // Lưu ID người đăng
        videoData.put("email", user.getEmail()); // Lưu email người đăng
        // videoData.put("timestamp", ServerValue.TIMESTAMP); // Có thể thêm timestamp nếu muốn

        // Ghi dữ liệu vào Firebase tại node /videos/<videoId>
        firebaseDB.child(id).setValue(videoData)
                .addOnSuccessListener(aVoid -> {
                    // Ghi thành công
                    Log.d(TAG, "Video saved to Firebase successfully. ID: " + id);
                    // Toast thành công đã hiển thị ở hàm uploadVideoToCloudinary
                })
                .addOnFailureListener(e -> {
                    // Ghi thất bại
                    Log.e(TAG, "Failed to save video to Firebase", e);
                    // Thông báo lỗi cụ thể nếu cần thiết, mặc dù Toast upload lỗi có thể đã đủ
                    // runOnUiThread(() -> Toast.makeText(UploadActivity.this, "Lỗi lưu thông tin video vào Firebase.", Toast.LENGTH_SHORT).show());
                });
    }

    // Lấy đường dẫn file thực từ Uri (Giữ nguyên như code bạn cung cấp)
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            Log.e(TAG, "getRealPathFromURI: Cursor is null for Uri: " + contentUri);
            return null; // Trả về null nếu cursor null
        }
        String path = null;
        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            if (cursor.moveToFirst()) {
                path = cursor.getString(column_index);
            } else {
                Log.e(TAG, "getRealPathFromURI: Cursor is empty for Uri: " + contentUri);
            }
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI: Error getting path from cursor", e);
        } finally {
            cursor.close(); // Luôn đóng cursor
        }
        Log.d(TAG, "getRealPathFromURI: Path found: " + path + " for Uri: " + contentUri);
        return path;
    }

    // Kiểm tra quyền và yêu cầu quyền nếu cần (Giữ nguyên)
    private void checkPermissionAndPickVideo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_VIDEO}, REQUEST_PERMISSION);
            } else {
                openVideoPicker();
            }
        } else { // Android < 13
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                openVideoPicker();
            }
        }
    }

    // Xử lý kết quả yêu cầu quyền (Giữ nguyên)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            // Kiểm tra xem quyền có được cấp không (cho READ_MEDIA_VIDEO hoặc READ_EXTERNAL_STORAGE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, mở trình chọn video
                openVideoPicker();
            } else {
                // Quyền bị từ chối, thông báo cho người dùng
                Toast.makeText(this, "Bạn phải cấp quyền truy cập bộ nhớ để chọn video", Toast.LENGTH_SHORT).show();
            }
        }
    }
}