package vn.iostar.databinding;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import vn.iostar.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private UserModel userModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userModel = new UserModel("Võ", "Trí");
        binding.setUser(userModel);

        // Thêm để tự cập nhật thay đổi
        userModel.setFirstName("Vinh");
        userModel.setLastName("Hoàng");

    }
}