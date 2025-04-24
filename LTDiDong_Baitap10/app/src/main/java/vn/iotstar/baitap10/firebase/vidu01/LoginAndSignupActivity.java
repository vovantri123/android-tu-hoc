package vn.iotstar.baitap10.firebase.vidu01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull; // Thêm import này
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener; // Thêm import này
import com.google.android.gms.tasks.Task; // Thêm import này
import com.google.firebase.auth.AuthResult; // Thêm import này
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference; // Thêm import này
import com.google.firebase.database.FirebaseDatabase; // Thêm import này

import java.util.HashMap; // Thêm import này

import vn.iotstar.baitap10.R;

public class LoginAndSignupActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference; // Thêm reference cho Realtime DB
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        auth = FirebaseAuth.getInstance();
        // Khởi tạo reference đến node "users" trong Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (validateInput(email, password)) {
                registerUser(email, password);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (validateInput(email, password)) {
                loginUser(email, password);
            }
        });
    }

    // XÓA BỎ PHẦN KIỂM TRA ĐĂNG NHẬP TỰ ĐỘNG TRONG onStart()
    // @Override
    // protected void onStart() {
    //     super.onStart();
    //     FirebaseUser currentUser = auth.getCurrentUser();
    //     if (currentUser != null) {
    //         navigateToMainApp();
    //     }
    // }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            emailInput.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Định dạng email không hợp lệ!", Toast.LENGTH_SHORT).show();
            emailInput.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            passwordInput.requestFocus();
            return false;
        }
        // Firebase yêu cầu mật khẩu tối thiểu 6 ký tự khi đăng ký
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            passwordInput.requestFocus();
            return false;
        }
        return true;
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { // Sử dụng OnCompleteListener đầy đủ
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = auth.getCurrentUser(); // Lấy thông tin user vừa tạo

                            if (firebaseUser != null) {
                                // **Lưu thông tin user vào Realtime Database**
                                saveUserToDatabase(firebaseUser);
                            } else {
                                // Trường hợp hiếm gặp user null sau khi tạo thành công
                                Toast.makeText(LoginAndSignupActivity.this, "Đăng ký thành công nhưng không lấy được thông tin user.", Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "User is null after successful creation");
                            }

                        } else {
                            // Đăng ký thất bại
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(LoginAndSignupActivity.this, "Mật khẩu yếu, vui lòng chọn mật khẩu mạnh hơn.", Toast.LENGTH_LONG).show();
                                passwordInput.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginAndSignupActivity.this, "Email không hợp lệ.", Toast.LENGTH_LONG).show();
                                emailInput.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(LoginAndSignupActivity.this, "Email này đã được đăng ký.", Toast.LENGTH_LONG).show();
                                emailInput.requestFocus();
                            } catch (Exception e) {
                                Toast.makeText(LoginAndSignupActivity.this, "Lỗi đăng ký: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    // Hàm lưu thông tin cơ bản của user vào Realtime Database
    private void saveUserToDatabase(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        String userEmail = firebaseUser.getEmail();

        // Tạo một HashMap để chứa dữ liệu user
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("email", userEmail);
        // Bạn có thể thêm các trường mặc định khác ở đây nếu muốn
        // userData.put("avatar", "URL_AVATAR_MAC_DINH");
        // userData.put("createdAt", ServerValue.TIMESTAMP); // Cần import ServerValue

        // Ghi dữ liệu vào node /users/<userId>
        databaseReference.child(userId).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "saveUserToDatabase:success for user " + userId);
                    // Thông báo đăng ký hoàn tất sau khi lưu DB thành công
                    Toast.makeText(LoginAndSignupActivity.this, "Đăng ký thành công! Vui lòng đăng nhập.", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "saveUserToDatabase:failure", e);
                    // Thông báo lỗi nếu không lưu được vào DB (nhưng user Auth vẫn được tạo)
                    Toast.makeText(LoginAndSignupActivity.this, "Đăng ký tài khoản thành công nhưng lỗi lưu thông tin bổ sung.", Toast.LENGTH_LONG).show();
                });
    }


    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        navigateToMainApp();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        // Phân tích lỗi đăng nhập cụ thể hơn nếu muốn
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(LoginAndSignupActivity.this, "Sai mật khẩu hoặc tài khoản không tồn tại.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginAndSignupActivity.this, "Lỗi đăng nhập: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateToMainApp() {
        Intent intent = new Intent(LoginAndSignupActivity.this, MainActivityFirebase.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}