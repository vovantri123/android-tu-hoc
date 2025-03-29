package vn.iostar.handler_asynctask_jsonapi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Vidu4VolleyLoginActivity extends AppCompatActivity {

    EditText etName, etPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidu4_volley_login);

        // Kiểm tra nếu người dùng đã đăng nhập, chuyển đến MainActivity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Vidu4VolleyProfileActivity.class));
            return; // Thêm return để tránh tiếp tục thực thi phần còn lại
        }

        progressBar = findViewById(R.id.progressBar);
        etName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etUserPassword);

        // Gọi phương thức userLogin() khi nhấn vào nút đăng nhập
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        // Lấy giá trị từ EditText
        final String username = etName.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        // Kiểm tra dữ liệu nhập vào
        if (TextUtils.isEmpty(username)) {
            etName.setError("Please enter your username");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        // Hiển thị ProgressBar khi đang xử lý đăng nhập
        progressBar.setVisibility(View.VISIBLE);

        // Gửi request đến server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            // Chuyển response sang JSON object
                            JSONObject obj = new JSONObject(response);

                            // Kiểm tra lỗi từ server
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                // Lấy thông tin người dùng từ response
                                JSONObject userJson = obj.getJSONObject("user");

                                // Tạo đối tượng User từ dữ liệu JSON
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email"),
                                        userJson.getString("gender"),
                                        userJson.getString("images")
                                );

                                // Lưu thông tin user vào SharedPreferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                // Chuyển sang MainActivity
                                finish();
                                startActivity(new Intent(Vidu4VolleyLoginActivity.this, Vidu4VolleyProfileActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        // Thêm request vào hàng đợi của Volley
        VolleySingle.getInstance(this).addToRequestQueue(stringRequest);
    }

}
