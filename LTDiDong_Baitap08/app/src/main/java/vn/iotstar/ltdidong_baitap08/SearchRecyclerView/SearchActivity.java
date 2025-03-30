package vn.iotstar.ltdidong_baitap08.SearchRecyclerView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.ltdidong_baitap08.R;

import androidx.appcompat.widget.SearchView; // Import đúng thư viện!
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcIcon;
    private SearchView searchView;
    private List<IconModel> arrayList1, filteredList;
    private IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rv);

        // Ánh xạ view
        rcIcon = findViewById(R.id.rcIcon);
        searchView = findViewById(R.id.searchView);

        arrayList1 = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Thêm dữ liệu vào danh sách IconModel
        arrayList1.add(new IconModel(R.drawable.sell, "Khuyến mãi đặc biệt"));
        arrayList1.add(new IconModel(R.drawable.shopping_cart, "Mua 1 tặng 1"));
        arrayList1.add(new IconModel(R.drawable.price_change, "Giảm giá 50%"));

        // Cấu hình GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);

        // Khởi tạo Adapter và gán vào RecyclerView
        iconAdapter = new IconAdapter(this, arrayList1);
        rcIcon.setAdapter(iconAdapter);

        // Cấu hình SearchView
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Không làm gì khi nhấn "Enter"
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText); // Gọi hàm lọc danh sách
                return true;
            }
        });
    }

    // Hàm lọc danh sách theo từ khóa nhập vào
    private void filterListener(String text) {
        filteredList.clear();
        for (IconModel iconModel : arrayList1) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(iconModel);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu phù hợp", Toast.LENGTH_SHORT).show();
        }

        iconAdapter.setListenerList(filteredList);
    }
}
