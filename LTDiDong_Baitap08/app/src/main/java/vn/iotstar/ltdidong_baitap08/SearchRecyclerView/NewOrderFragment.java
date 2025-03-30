package vn.iotstar.ltdidong_baitap08.SearchRecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.iotstar.ltdidong_baitap08.databinding.FragmentNeworderBinding;

public class NewOrderFragment extends Fragment {
    private FragmentNeworderBinding binding;

    public NewOrderFragment() {
        // Constructor mặc định
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNeworderBinding.inflate(inflater, container, false);

        // RecyclerView hoặc các xử lý khác có thể thêm ở đây

        return binding.getRoot();
    }
}
