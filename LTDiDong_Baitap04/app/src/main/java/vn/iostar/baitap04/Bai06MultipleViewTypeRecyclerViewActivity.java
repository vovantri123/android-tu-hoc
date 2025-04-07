package vn.iostar.baitap04;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iostar.baitap04.adapter.UserAdapter;
import vn.iostar.baitap04.model.User;

public class Bai06MultipleViewTypeRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView rvMultipleViewType;
    private ArrayList<Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai06_multiple_view_type_recyclerview);

        rvMultipleViewType = findViewById(R.id.rv_multipe_view_type);
        mData = new ArrayList<>();

        // Thêm dữ liệu vào mData
        mData.add(new User("Nguyen Van Nghia", "Quan 1"));
        mData.add(R.drawable.ic_launcher_foreground);
        mData.add("Text 0");
        mData.add("Text 1");
        mData.add(new User("Pham Nguyen Tam Phu", "Quan 10"));
        mData.add("Text 2");
        mData.add(R.drawable.ic_launcher_foreground);
        mData.add(R.drawable.ic_launcher_foreground);
        mData.add(new User("Tran Van Phuc", "Quan 11"));
        mData.add("Text 3");
        mData.add("Text 4");
        mData.add(new User("Nguyen Hoang Minh", "Quan 3"));
        mData.add(R.drawable.ic_launcher_foreground);

        // Khởi tạo adapter và set cho RecyclerView
        UserAdapter adapter = new UserAdapter(this, mData);

        rvMultipleViewType.setAdapter(adapter);

        rvMultipleViewType.setLayoutManager(new LinearLayoutManager(this));
    }
}