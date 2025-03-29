package vn.iostar.handler_asynctask_jsonapi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Vidu3MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextParent;

    // Tạo constructor
    public Vidu3MyAsyncTask(Activity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Hàm này sẽ chạy đầu tiên
        // Ở đây mình sẽ thông báo
        Toast.makeText(contextParent, "Bắt đầu tiến trình", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Hàm này thực hiện các tác vụ nền
        // Tuyệt đối không vẽ giao diện trong này
        for (int i = 0; i <= 100; i++) {
            SystemClock.sleep(100); // Giả lập công việc chạy nền
            // Khi gọi hàm này thì onProgressUpdate sẽ được gọi
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // Hàm thực hiện update giao diện khi có dữ liệu từ doInBackground gửi xuống
        ProgressBar progressBar = contextParent.findViewById(R.id.prbDemo);
        int number = values[0];
        progressBar.setProgress(number);

        // Đồng thời hiển thị giá trị % lên TextView
        TextView textView = contextParent.findViewById(R.id.txtStatus);
        textView.setText(number + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Hàm này được thực hiện khi tiến trình kết thúc
        Toast.makeText(contextParent, "Đã hoàn thành, Finished", Toast.LENGTH_SHORT).show();
    }
}
