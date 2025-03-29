package vn.iostar.handler_asynctask_jsonapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Vidu3AsyncTaskActivity extends AppCompatActivity {

    Button btnStart;
    Vidu3MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidu3_async_task);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo tiến trình của bạn
                // Truyền Activity chính là AsynctaskActivity sang bên tiến trình của mình
                myAsyncTask = new Vidu3MyAsyncTask(Vidu3AsyncTaskActivity.this);
                // Gọi hàm execute để kích hoạt tiến trình
                myAsyncTask.execute();
            }
        });
    }
}
