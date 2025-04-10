package vn.iostar.databinding.vd01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import vn.iostar.databinding.R;
import vn.iostar.databinding.model.User;
import vn.iostar.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private User user;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        user = new User("Võ", "Trí");
        binding.setNameVariableVidu1(user);

        // Thêm để tự cập nhật thay đổi
        user.setFirstName("Vinh");
        user.setLastName("Hoàng");

    }
}