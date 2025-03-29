package vn.iostar.handler_asynctask_jsonapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Vidu4VolleyProfileActivity extends AppCompatActivity {

    public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener {
        TextView id, name, email, gender;
        Button btnLogout;
        ImageView imageViewProfile;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vidu4_volley_profile);

            // Kiểm tra nếu người dùng chưa đăng nhập, chuyển về LoginActivity
            if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
                Intent intent = new Intent(ProfilesActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            // Ánh xạ view từ layout
            id = findViewById(R.id.tvIdValue);
            name = findViewById(R.id.tvUsernameValue);
            email = findViewById(R.id.tvEmailValue);
            gender = findViewById(R.id.tvGenderValue);
            btnLogout = findViewById(R.id.btnLogout);
            imageViewProfile = findViewById(R.id.imageViewProfile);

            // Lấy thông tin người dùng từ SharedPreferences
            User user = SharedPrefManager.getInstance(this).getUser();

            // Hiển thị thông tin người dùng
            id.setText(String.valueOf(user.getId()));
            email.setText(user.getEmail());
            gender.setText(user.getGender());
            name.setText(user.getName());

            // Load ảnh profile bằng Glide
            Glide.with(getApplicationContext())
                    .load(user.getImages())
                    .into(imageViewProfile);

            // Gán sự kiện click cho nút logout
            btnLogout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(btnLogout)) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Intent intent = new Intent(ProfilesActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
