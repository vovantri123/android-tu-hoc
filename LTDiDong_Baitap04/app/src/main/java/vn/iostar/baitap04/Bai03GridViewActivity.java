package vn.iostar.baitap04;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Bai03GridViewActivity extends AppCompatActivity {

    private int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai03_gridview);

        //khai báo
        GridView gridView;
        ArrayList<String> arrayList;

        //ánh xạ
        gridView = (GridView) findViewById(R.id.gridview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        //Tạo ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                Bai03GridViewActivity.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        gridView.setAdapter(adapter);

        /*
        //bắt sự kiện click nhanh trên từng dòng của Gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên GridView -> i ban đầu =0
                Toast.makeText(Bai03GridViewActivity.this, "" + i, Toast.LENGTH_SHORT).show();
            }
        });
         */

        // ----------------------------------------------------------------------------------------

        EditText editText1;
        Button btnThem;
        Button btnCapNhat;
        Button btnXoa;

        editText1 = (EditText) findViewById(R.id.editText1);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        btnXoa = (Button) findViewById(R.id.btnXoa);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                arrayList.add(name);
                adapter.notifyDataSetChanged(); // Thông báo cho Adapter rằng dữ liệu đã thay đổi, để cập nhật lại danh sách hiển thị trên GridView.
            }
        });

        //bắt sự kiện trên từng dòng của GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lấy nội dung đua lên edittext
                editText1.setText(arrayList.get(i));
                vitri = i;
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.set(vitri, editText1.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(vitri);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
