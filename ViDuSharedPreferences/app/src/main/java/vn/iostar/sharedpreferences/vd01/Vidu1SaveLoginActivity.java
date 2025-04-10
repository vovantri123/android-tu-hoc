package vn.iostar.sharedpreferences.vd01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.sharedpreferences.R;

public class Vidu1SaveLoginActivity extends AppCompatActivity{

    //Khai báo biến toàn cục
    Button buttonTxt;
    EditText usernameTxt, passwordTxt;
    CheckBox cbRememberMe;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidu1_save_login);

        AnhXa();

        // Khởi tạo
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);  // dataLogin tên  của file SharedPreferences mà bạn muốn tạo hoặc truy cập trong ứng dụng.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        loadLoginData(); // Lấy giá trị từ SharedPreferences (nếu có)

        buttonTxt.setOnClickListener(view -> {
            String username = usernameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            if (username.equals("admin") && password.equals("admin")) {
                Toast.makeText(Vidu1SaveLoginActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();

                if (cbRememberMe.isChecked()) {
                    editor.putString("taikhoan", username);
                    editor.putString("matkhau", password);
                    editor.putBoolean("trangthai", true);
                } else {
                    editor.clear(); // Xóa tất cả dữ liệu login
                }
                editor.apply(); // xác nhận việc lưu

            } else {
                Toast.makeText(Vidu1SaveLoginActivity.this, "Dang nhap that bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        buttonTxt = findViewById(R.id.buttonTxt);
        usernameTxt = findViewById(R.id.usernameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        cbRememberMe = findViewById(R.id.cbmemberme);
    }

    private void loadLoginData() {
        usernameTxt.setText(sharedPreferences.getString("taikhoan", ""));
        passwordTxt.setText(sharedPreferences.getString("matkhau", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean("trangthai", false));
    }
}