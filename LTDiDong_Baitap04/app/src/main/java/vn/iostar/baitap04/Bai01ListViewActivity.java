package vn.iostar.baitap04;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Bai01ListViewActivity extends AppCompatActivity {
    private int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai01_listview);

        //khai báo
        ListView listView;
        ArrayList<String> arrayList;


        //ánh xạ
        listView = findViewById(R.id.listview1);
        //Thêm dữ liệu vào ArrayList
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        // Tạo ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                Bai01ListViewActivity.this,   // Context: màn hình hiện tại (Activity)
                android.R.layout.simple_list_item_1, // Layout mặc định của Android cho mỗi item trong ListView
                arrayList  // Dữ liệu nguồn (danh sách các phần tử)
        );

        //truyền dữ liệu từ adapter ra listview
        listView.setAdapter(adapter);

        /*
        //bắt sự kiện click nhanh trên từng dòng của Listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên ListView -> i ban đầu =0
                Toast.makeText(Bai01ListViewActivity.this,
                        "Bạn đang nhấn giữ "+ i + "-" + arrayList.get(i) ,
                        Toast.LENGTH_SHORT).show();
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
                adapter.notifyDataSetChanged(); // Thông báo cho Adapter rằng dữ liệu đã thay đổi, để cập nhật lại danh sách hiển thị trên ListView.
            }
        });

        //bắt sự kiện trên từng dòng của Listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // adapterView (parent): GridView hoặc ListView chứa các item.
                // view: View của item được click.
                // i (position): Vị trí của item trong danh sách.
                // l (id): ID của item (thường dùng khi có cơ sở dữ liệu).

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
