package vn.iostar.baitap01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);  // Dòng này yêu cầu Android không hiển thị thanh tiêu đề (Title Bar) của Activity.
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, // Dòng này yêu cầu Activity ẩn cả thanh trạng thái (Status Bar) ở trên cùng. Cho phép hiển thị ở chế độ fullscreen
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Cau4Activity.class);
                startActivity(intent);
            }
        });

    }
}