package vn.iostar.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        buttonTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                if (username.equals("admin") && password.equals("123")) {
                    Toast.makeText(Vidu1SaveLoginActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();

                    // nếu có check
                    if (cbRememberMe.isChecked()) {
                        // Chỉnh sửa nội dung file lưu trữ
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", password);
                        editor.putBoolean("trangthai", true);
                        editor.apply(); // xác nhận việc lưu
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("trangthai");
                        editor.apply();
                    }
                } else {
                    Toast.makeText(Vidu1SaveLoginActivity.this, "Dang nhap that bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Lấy dữ liệu đã được lưu
        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);  // dataLogin là trong ViDu1

        // Lấy giá trị từ SharedPreferences
        usernameTxt.setText(sharedPreferences.getString("taikhoan", ""));
        passwordTxt.setText(sharedPreferences.getString("matkhau", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean("trangthai", false));

    }

    private void AnhXa() {
        buttonTxt = (Button) findViewById(R.id.buttonTxt);
        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        cbRememberMe = (CheckBox) findViewById(R.id.cbmemberme);
    }
}