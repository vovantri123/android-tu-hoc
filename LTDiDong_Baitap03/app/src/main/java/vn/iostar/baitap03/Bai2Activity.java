package vn.iostar.baitap03;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Bai2Activity extends AppCompatActivity {
    private ConstraintLayout layout;
    private Switch switchBackground;
    private Button btnPopup;
    private Button btnContext;
    private Button btnDialog;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        layout = findViewById(R.id.main_layout);
        switchBackground = findViewById(R.id.switchBackground);

        // Set hình nền ban đầu
        layout.setBackgroundResource(R.drawable.bg3);

        // Xử lý khi bật/tắt Switch
        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layout.setBackgroundResource(R.drawable.bg1);
            } else {
                layout.setBackgroundResource(R.drawable.bg3);
            }
        });

        // Popup
        btnPopup = findViewById(R.id.btnPopup);
        btnPopup.setOnClickListener(v -> popup());


        // Context Menu
        btnContext = findViewById(R.id.btnContext);
        registerForContextMenu(btnContext);  // Đăng ký view để sử dụng Context Menu

        // Dialog
        btnDialog = findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(v -> diaLog1());
    }

    // Popup
    private void popup() {
        PopupMenu popupMenu = new PopupMenu(Bai2Activity.this, btnPopup);
        popupMenu.getMenuInflater().inflate(R.menu.menu_setting,popupMenu.getMenu());

        // Bắt sự kiện chọn item trong menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_change_bg) {
                    layout.setBackgroundResource(R.drawable.bg2);
                    return true;
                } else if (id == R.id.menu_about) {
                    Toast.makeText(Bai2Activity.this, "Ứng dụng của bạn", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.menu_exit) {
                    finish();
                    return true;
                }
                return false;
            }
        });


        popupMenu.show();
    }

    // Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_setting2, menu);
        //menu.setHeaderTitle("Context Menu");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSetting2) {
            Toast.makeText(this, "Bạn đang chọn Cài đặt", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.menuShare) {
            btnContext.setText("Chia sẻ");
            return true;
        } else if (item.getItemId() == R.id.menuLogout) {
            finish();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    // Dialog
    private void diaLog1() {
        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Bỏ tiêu đề
        dialog.setContentView(R.layout.dialog); // Gán layout dialog
        dialog.setCanceledOnTouchOutside(true); // Đóng khi chạm ngoài

        // Ánh xạ các thành phần trong dialog.xml
        EditText editText1 = dialog.findViewById(R.id.editNumber1);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);

        // Xử lý sự kiện khi nhấn nút xác nhận
        btnXacNhan.setOnClickListener(v -> {
            String input = editText1.getText().toString().trim();
            if (!input.isEmpty()) {
                Toast.makeText(this, "Bạn đã nhập: " + input, Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Đóng dialog
            } else {
                Toast.makeText(this, "Vui lòng nhập số!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show(); // Hiển thị dialog
    }
}

