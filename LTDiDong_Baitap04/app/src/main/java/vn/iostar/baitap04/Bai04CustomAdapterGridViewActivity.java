package vn.iostar.baitap04;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import vn.iostar.baitap04.adapter.MonHocAdapter;
import vn.iostar.baitap04.model.MonHoc;

public class Bai04CustomAdapterGridViewActivity extends AppCompatActivity {
    //khai báo
    GridView gridView;
    ArrayList<MonHoc> arrayList;

    MonHocAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai04_custom_adapter_gridview);

        //ánh xạ
        AnhXa();
        //Tạo Adapter
        adapter = new MonHocAdapter(
                this,
                R.layout.row_monhoc,
                arrayList
        );
        //truyền dữ liệu từ adapter ra gridview
        gridView.setAdapter(adapter);

    }

    private void AnhXa() {
        gridView = (GridView) findViewById(R.id.gridview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java));
        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin1",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1",R.drawable.dart));
    }
}
