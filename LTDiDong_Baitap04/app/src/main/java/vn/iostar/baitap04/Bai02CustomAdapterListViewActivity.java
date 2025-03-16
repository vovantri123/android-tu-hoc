package vn.iostar.baitap04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Bai02CustomAdapterListViewActivity extends AppCompatActivity {
    //khai báo
    private ListView listView;
    private MonHocAdapter adapter;

    private ArrayList<MonHoc> arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai02_custom_adapter_listview);

        //ánh xạ
        AnhXa();
        //Tạo Adapter
        adapter = new MonHocAdapter(Bai02CustomAdapterListViewActivity.this,
                R.layout.row_monhoc,
                arrayList
        );
        //truyền dữ liệu từ adapter ra listview
        listView.setAdapter(adapter);
    }

    private void AnhXa() {
        listView = (ListView) findViewById(R.id.listview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java));
        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin1",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1",R.drawable.dart));
    }
}
