package vn.iostar.baitap05;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Khai báo biến toàn cục
    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ ListView
        listView = findViewById(R.id.listView1);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // Gọi adapter
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_note, arrayList);
        listView.setAdapter(adapter);

        // Gọi hàm databaseSQLite
        InitDatabaseSQLite();
        //createDatabaseSQLite();
        databaseSQLite();



    }

    private void createDatabaseSQLite() {
        // Thêm dữ liệu vào database
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Vi du SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Vi du SQLite 2')");
    }

    private void InitDatabaseSQLite() {
        // Khởi tạo database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);

        // Tạo bảng Notes
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite() {
        // Xóa dữ liệu cũ trước khi tải dữ liệu mới
        arrayList.clear();

        // Lấy dữ liệu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            // Thêm dữ liệu vào arrayList
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    //gọi menu để Thêm Notes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) {
            DialogCapNhatNotes("", -1); // Truyền ID = -1 để xác định là thêm mới
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Hàm dialog cập nhật Notes
    public void DialogCapNhatNotes(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_note); // ánh xạ xml qua

        // Ánh xạ
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonLuu = dialog.findViewById(R.id.btnEdit);
        Button buttonHuy = dialog.findViewById(R.id.btnHuy);

        // Nếu id != -1 thì là chế độ sửa, còn nếu -1 thì thêm mới
        if (id != -1) {
            editText.setText(name);
            buttonLuu.setText("Cập nhật NOTES");
        } else {
            buttonLuu.setText("Thêm mới NOTES");
        }

        // Bắt sự kiện
        buttonLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editText.getText().toString().trim();
                if (newName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (id == -1) {
                    // Thêm mới
                    databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '" + newName + "')");
                    Toast.makeText(MainActivity.this, "Đã thêm note mới", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật
                    databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + newName + "' WHERE Id = " + id);
                    Toast.makeText(MainActivity.this, "Đã cập nhật note", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
                databaseSQLite(); // Load lại danh sách
            }
        });

        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }

    // Hàm AlertDialog Xóa
    public void DialogDelete(String name, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes " + name + " này không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE FROM Notes WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Đã xóa Notes " + name + " thành công", Toast.LENGTH_SHORT).show();
                databaseSQLite(); // Gọi hàm load lại dữ liệu
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }



}
