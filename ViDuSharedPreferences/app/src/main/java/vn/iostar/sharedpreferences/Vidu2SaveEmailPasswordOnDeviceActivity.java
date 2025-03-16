package vn.iostar.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Vidu2SaveEmailPasswordOnDeviceActivity extends AppCompatActivity { // Muốn chạy thì nhớ Clear Data trong của app này trong LDPlayer, hoặc nhấn đăng xuất

    // Khai báo biến toàn cục.
    private EditText mEmailView;
    private EditText mPasswordView;
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super. onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidu2_save_email_password_on_device);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView. OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        // Kiểm tra nếu thông tin đăng nhập đã được lưu
        if (!new PrefManager(this).isUserLoggedOut()) {
            // Nếu email và password đã được lưu trong SharedPreferences
            startHomeActivity();
        }

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {
        // Reset lỗi trước đó
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Lấy dữ liệu từ các ô nhập
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Kiểm tra password hợp lệ
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Kiểm tra email hợp lệ
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // Nếu có lỗi, focus vào view bị lỗi
            focusView.requestFocus();
        } else {
            // Nếu thông tin hợp lệ, lưu thông tin nếu checkbox được chọn
            if (checkBoxRememberMe.isChecked()) {
                saveLoginDetails(email, password);
            }

            // Chuyển đến HomeActivity
            startHomeActivity();
        }
    }

    // Chuyển đến HomeActivity
    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại để không quay lại màn hình đăng nhập khi nhấn Back
    }

    // Lưu thông tin đăng nhập vào SharedPreferences
    private void saveLoginDetails(String email, String password) {
        new PrefManager(this).saveLoginDetails(email, password);
    }

    // Kiểm tra định dạng email hợp lệ
    private boolean isEmailValid(String email) {
        // TODO: Thay thế bằng logic kiểm tra phức tạp hơn nếu cần
        return email.contains("@");
    }

    // Kiểm tra mật khẩu hợp lệ (ít nhất 5 ký tự)
    private boolean isPasswordValid(String password) {
        // TODO: Thay thế bằng logic kiểm tra phức tạp hơn nếu cần
        return password.length() > 4;
    }


}
