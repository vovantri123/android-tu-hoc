package vn.iostar.sharedpreferences.vd02;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.sharedpreferences.R;

public class Vidu2SaveEmailPasswordOnDeviceActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;
    private CheckBox checkBoxRememberMe;
    private CustomSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidu2_save_email_password_on_device);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        Button mEmailSignInButton = findViewById(R.id.sign_in_button);

        sharedPreferences = CustomSharedPreferences.getInstance(this);

        // Nếu đã lưu thông tin đăng nhập thì tự động chuyển sang HomeActivity
        if (!sharedPreferences.isUserLoggedOut()) {
            startHomeActivity();
        }

        mEmailSignInButton.setOnClickListener(view -> attemptLogin());
    }

    private void attemptLogin() {
        // Reset lỗi trước đó
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        View focusView = null;
        boolean cancel = false;

         if (!TextUtils.isEmpty(email) && !isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus(); // Nếu có lỗi → focus vào ô cần sửa.
        } else {
            // Kiểm tra thông tin đăng nhập có đúng admin không
            if (email.equals("admin@gmail.com") && password.equals("admin")) {
                if (checkBoxRememberMe.isChecked()) {
                    sharedPreferences.saveLoginDetails(email, password);
                }
                startHomeActivity();
            }
            else{
                Toast.makeText(this,R.string.email_or_password_is_not_correct,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
