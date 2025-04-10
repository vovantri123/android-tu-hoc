package vn.iostar.databinding.vd02;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vn.iostar.databinding.R;
import vn.iostar.databinding.databinding.ActivityDatabindRecyclerviewBinding;
import vn.iostar.databinding.model.User;

public class DataBindingRecyclerViewActivity extends AppCompatActivity implements ListUserAdapter.OnItemClickListener { // Phải implemet cái interface mới truyền sự kiện click dô được

    private ActivityDatabindRecyclerviewBinding binding;
    private ListUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_databind_recyclerview);
        binding.setNameVariableVidu2(this); // gán giá trị title trong xml

        binding.rcView.setLayoutManager(new LinearLayoutManager(this));

        List<User> userList = generateUserList();
        adapter = new ListUserAdapter(userList);
        adapter.setOnItemClickListener(this); // Chấp nhận

        binding.rcView.setAdapter(adapter);
    }

    private List<User> generateUserList() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User("Võ" + i, "Trí" + i));
        }
        return list;
    }

    @Override
    public void itemClick(User user, int position) {
        Toast.makeText(this, "Bạn vừa click: STT " + position + " - " + user.getFirstName(), Toast.LENGTH_SHORT).show();
    }
}
