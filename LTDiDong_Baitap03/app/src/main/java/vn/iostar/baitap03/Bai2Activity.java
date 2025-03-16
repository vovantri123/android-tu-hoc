package vn.iostar.baitap03;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Bai2Activity extends AppCompatActivity {
    private ConstraintLayout layout;
    private Switch switchBackground;
    private boolean isBackground1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        layout = findViewById(R.id.main_layout);
        switchBackground = findViewById(R.id.switchBackground);

        // Set hình nền ban đầu
        layout.setBackgroundResource(R.drawable.bg3);

        // Xử lý khi bật/tắt Switch
        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layout.setBackgroundResource(R.drawable.bg1);
            } else {
                layout.setBackgroundResource(R.drawable.bg3);
            }
        });
    }
}

