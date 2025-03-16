package vn.iostar.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            PrefManager prefManager = new PrefManager(HomeActivity.this);
            prefManager.logout(); // Xóa dữ liệu đăng nhập

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(HomeActivity.this, Vidu2SaveEmailPasswordOnDeviceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack
            startActivity(intent);
            finish(); // Đóng HomeActivity
        });
    }
}