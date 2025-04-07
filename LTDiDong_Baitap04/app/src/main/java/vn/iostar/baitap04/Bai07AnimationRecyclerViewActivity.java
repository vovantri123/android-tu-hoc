package vn.iostar.baitap04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iostar.baitap04.adapter.CustomAnimationAdapter;

public class Bai07AnimationRecyclerViewActivity extends AppCompatActivity {

    private Button btnAddItem;
    private RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai07_animation_recyclerview);

        btnAddItem = findViewById(R.id.btn_add_item);
        rvItems = findViewById(R.id.rv_items);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("item " + i);
        }

        final CustomAnimationAdapter adapter = new CustomAnimationAdapter(data);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        // Thiết lập ItemAnimator cho RecyclerView
        rvItems.setItemAnimator(new DefaultItemAnimator());

         /*
            Vì sử dụng DefaultItemAnimator nên các animation sẽ như sau:
            • Animation Added: thực hiện thay đổi giá trị alpha
              của itemView từ 0 đến 1.
            • Animation Removed: thực hiện thay đổi giá trị alpha
              của itemView từ 1 về 0.
            • Animation Changed: thực hiện animation từ 1 về 0,
              sau đó thực hiện animation từ 0 đến 1 để thay đổi item.

            Đây là cách mà DefaultItemAnimator xử lý. Nếu muốn tùy chỉnh animation theo mong muốn,
            bạn cần viết lại animation bằng cách kế thừa từ SimpleItemAnimator.

         */

        // Xử lý sự kiện khi nhấn nút "Add Item"
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thêm một item mới vào adapter và scroll RecyclerView đến vị trí item mới
                adapter.addItem("new item");
                rvItems.scrollToPosition(adapter.getItemCount() - 1); // tự động cuộn tới item vừa thêm
            }
        });
    }

}
