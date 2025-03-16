package vn.iotstar.intents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private ActivityResultLauncher<Intent> activityResultLauncher;  // Biến dùng để khởi chạy Activity và nhận kết quả trả về. Đây là API mới thay cho startActivityForResult.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Gán layout activity_main.xml cho Activity

        textViewResult = findViewById(R.id.textViewResult);
        Button buttonOpen = findViewById(R.id.buttonOpen);

        // Khởi tạo ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String data = result.getData().getStringExtra("data_return");
                            textViewResult.setText("Dữ liệu nhận: " + data);
                        }
                    }
                }
        );

        // Mở ActivityInput khi nhấn nút
        buttonOpen.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActivityInput.class);
            activityResultLauncher.launch(intent);
        });
    }
}