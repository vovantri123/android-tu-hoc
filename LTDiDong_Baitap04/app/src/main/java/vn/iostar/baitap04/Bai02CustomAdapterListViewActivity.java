package vn.iostar.baitap04;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import vn.iostar.baitap04.adapter.MonHocAdapter;
import vn.iostar.baitap04.model.MonHoc;

public class Bai02CustomAdapterListViewActivity extends AppCompatActivity {
    //khai báo

    int vitri = -1;
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
        adapter = new MonHocAdapter(
                this,
                R.layout.row_monhoc,
                arrayList
        );
        //truyền dữ liệu từ adapter ra listview
        listView.setAdapter(adapter);


        // --------------------------------- Khúc dưới làm y chang Bai01

        EditText editName;
        EditText editDesc;
        Button btnThem;
        Button btnCapNhat;
        Button btnXoa;

        editName = findViewById(R.id.editText1);
        editDesc = findViewById(R.id.editText2);
        btnThem =  findViewById(R.id.btnThem);
        btnCapNhat =  findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String desc = editDesc.getText().toString();
                arrayList.add(new MonHoc(name, desc, R.drawable.java));
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                editName.setText(arrayList.get(i).getName());
                editDesc.setText(arrayList.get(i).getDesc());
                vitri = i;
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String desc = editDesc.getText().toString();
                arrayList.set(vitri, new MonHoc(name, desc, R.drawable.java));
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

    private void AnhXa() {
        listView = findViewById(R.id.listview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java));
        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin1",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1",R.drawable.dart));
    }

}
