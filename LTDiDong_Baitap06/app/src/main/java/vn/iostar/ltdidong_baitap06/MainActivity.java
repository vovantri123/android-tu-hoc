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
    List<Category> categoryList = new ArrayList<>();

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
        rcCate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void GetCategory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        // Gọi API để lấy danh sách Category
        Call<List<Category>> call = apiService.getCategoryAll();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                    rcCate.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}