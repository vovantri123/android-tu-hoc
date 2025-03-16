package vn.iostar.databinding;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vn.iostar.databinding.databinding.ActivityDatabindRecyclerviewBinding;

public class DataBindingRecyclerViewActivity extends AppCompatActivity implements ListUserAdapter.OnItemClickListener{// Nhớ implement
    public ObservableField<String> title = new ObservableField<>();
    private ListUserAdapter listUserAdapter;
    private ActivityDatabindRecyclerviewBinding binding;  // class được tạo tự động

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_databind_recyclerview);
        title.set("Ví dụ về DataBinding cho RecycleView");
        binding.setHome(this);
        setData();
        binding.rcView.setLayoutManager(new LinearLayoutManager(this));
        binding.rcView.setAdapter(listUserAdapter);
        listUserAdapter.setOnItemClickListener(this);

    }

    private void setData() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName("Trí" + i);
            user.setLastName("Võ" + i);
            userList.add(user);
        }
        listUserAdapter = new ListUserAdapter(userList);
    }

    @Override
    public void itemClick(User user) {
        Toast.makeText(this, "bạn vừa click " + user.getFirstName(), Toast.LENGTH_SHORT).show();
    }

}