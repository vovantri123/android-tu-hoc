package vn.iostar.sharedpreferences.vd02;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreferences {  // Class dùng để lưu dữ liệu //Dùng cho ví dụ 2
    private static CustomSharedPreferences instance;

    private final SharedPreferences sharedPreferences;

    public CustomSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
    }

    public static synchronized CustomSharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new CustomSharedPreferences(context);
        }
        return instance;
    }
    /*
         Tác dụng hàm getInstance: Giúp chỉ có một instance duy nhất của CustomSharedPreferences được tạo ra trong toàn bộ ứng dụng., giúp dễ dùng lại, tiết kiệm bộ nhớ và tránh lỗi khi dùng SharedPreferences ở nhiều nơi, nhiều thread
         Ví dụ:
            CustomSharedPreferences customSharedPreferences = CustomSharedPreferences.getInstance(context);
            customSharedPreferences.saveEmail("Email");
            String email = customSharedPreferences.getEmail();
     */

    public void saveLoginDetails(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.apply();  // Nên dùng aplly thay vì commit
    }

    public String getEmail() {
        return sharedPreferences.getString("Email", "");
    }

    public String getPassword() {
        return sharedPreferences.getString("Password", "");
    }

    public boolean isUserLoggedOut() {
        boolean isEmailEmpty = getEmail().isEmpty();
        boolean isPasswordEmpty = getPassword().isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit(); // Khai báo cái này thì bắt buột phải có apply hoặc commit
        editor.clear(); // Xóa toàn bộ dữ liệu đăng nhập
        editor.apply();
    }
}
