package vn.iostar.handler_asynctask_jsonapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Vidu1Handler1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidu1_handler1);

        // Tạo một luồng mới để chuyển sang LoginActivity sau 2 giây
        new Thread(() -> {
            int n = 0;
            try {
                do {
                    if (n >= 2000) {  // Sau 2 giây (2000ms)
                        // Kết thúc activity hiện tại
                        Vidu1Handler1Activity.this.finish();

                        // Tạo intent để chuyển sang LoginActivity
                        Intent intent = new Intent(
                                Vidu1Handler1Activity.this.getApplicationContext(),
                                LoginActivity.class
                        );
                        // Khởi chạy LoginActivity
                        Vidu1Handler1Activity.this.startActivity(intent);
                        return;
                    }
                    // Chờ 100ms rồi tăng biến n
                    Thread.sleep(100);
                    n += 100;
                } while (true);
            } catch (InterruptedException interruptedException) {
                // Nếu luồng bị ngắt, vẫn chuyển sang LoginActivity
                Vidu1Handler1Activity.this.finish();
                Intent intent = new Intent(
                        Vidu1Handler1Activity.this.getApplicationContext(),
                        LoginActivity.class
                );
                Vidu1Handler1Activity.this.startActivity(intent);
            } catch (Throwable throwable) {
                // Nếu gặp lỗi bất kỳ, vẫn chuyển sang LoginActivity
                Vidu1Handler1Activity.this.finish();
                Intent intent = new Intent(
                        Vidu1Handler1Activity.this.getApplicationContext(),
                        LoginActivity.class
                );
                Vidu1Handler1Activity.this.startActivity(intent);
                throw throwable; // Ném lại lỗi để dễ debug
            }
        }).start();
    }
}
