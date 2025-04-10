package vn.iostar.ltdidong_baitap06;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcCate;
    CategoryAdapter categoryAdapter;
    APIService apiService;
    ArrayList<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        GetCategory(); // Load dữ liệu cho category
    }

    private void AnhXa() {
        // Ánh xạ RecyclerView
        rcCate = findViewById(R.id.rc_category);
        rcCate.setHasFixedSize(true);
        rcCate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void GetCategory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        // Gọi API để lấy danh sách Category
        Call<ArrayList<Category>> call = apiService.getCategoryAll();

        call.enqueue(new Callback<ArrayList<Category>>() { // được dùng để thực hiện gọi API bất đồng bộ (asynchronous), tức là chạy ngầm mà không chặn UI của ứng dụng. Khi server phản hồi (thành công hoặc thất bại), các hàm bên trong sẽ được gọi tự động.
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                    rcCate.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}