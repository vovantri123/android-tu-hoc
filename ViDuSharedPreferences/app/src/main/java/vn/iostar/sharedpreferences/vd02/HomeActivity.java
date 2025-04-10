package vn.iostar.sharedpreferences.vd02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.sharedpreferences.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            CustomSharedPreferences customSharedPreferences = CustomSharedPreferences.getInstance(HomeActivity.this);
            customSharedPreferences.logout(); // Xóa dữ liệu đăng nhập

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(HomeActivity.this, Vidu2SaveEmailPasswordOnDeviceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack
            /*
                Intent.FLAG_ACTIVITY_NEW_TASK
                Tạo một task (ngăn xếp) mới để chứa activity được khởi chạy -> Khi dùng flag này, activity mới sẽ không nằm trong stack cũ.

                Intent.FLAG_ACTIVITY_CLEAR_TASK
                Xoá toàn bộ các activity có trong task hiện tại trước khi mở activity mới. -> Nó sẽ dọn sạch mọi activity đang mở trước đó (kể cả HomeActivity) trước khi mở activity mới
             */
            startActivity(intent);
            // finish(); // Đóng HomeActivity (có thể không dùng do đã có Intent.FLAG_ACTIVITY_CLEAR_TASK)
        });
    }
}