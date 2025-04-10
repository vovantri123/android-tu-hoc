package vn.iostar.databinding.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import vn.iostar.databinding.BR;

public class User extends BaseObservable { // Dùng cho ví dụ 1
    private String lastName;
    private String firstName;

    public User() {
    }

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Bindable  // lien ket du lieu voi UI
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
        // cap nhat UI khi du lieu thay doi
        // BR là một class tự động được tạo ra bởi Data Binding, chứa ID của các biến Bindable trong XML. Khi bạn khai báo một @Bindable trong getter, hệ thống sẽ sinh ra một ID tương ứng trong BR.java.
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
