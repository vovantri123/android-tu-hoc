package vn.iostar.databinding;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class UserModel extends BaseObservable { // Dùng cho ví dụ 1
    private String lastName;
    private String firstName;

    public UserModel() {
    }

    public UserModel(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Bindable  // Cho phép Data Binding theo dõi thuộc tính này. Nếu giá trị lastName thay đổi, các TextView hoặc thành phần giao diện khác sử dụng nó sẽ được cập nhật tự động.
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);  // // BR là một class tự động được tạo ra bởi Data Binding, chứa ID của các biến Bindable trong XML. Khi bạn khai báo một biến trong @Bindable, hệ thống sẽ sinh ra một ID tương ứng trong BR.java.
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }


}
