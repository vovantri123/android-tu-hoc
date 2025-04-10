package vn.iostar.databinding.vd02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iostar.databinding.R;
import vn.iostar.databinding.databinding.ItemRowBinding;
import vn.iostar.databinding.model.User;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.MyViewHolder> {
    private List<User> userList;

    // Interface OnItemClickListener được khai báo bên dưới
    // Dùng để lưu lại đối tượng implement interface này (là DataBindingRecyclerViewActivity)
    private OnItemClickListener onItemClickListener;


    public ListUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {  // Setter để truyền interface từ ngoài vào (là cái this bên DataBindingRecyclerViewActivity)
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()); // K cần truyền Context vào Adapter vì đã có parent.getContext()
        /*
            parent: là ViewGroup cha chứa các item của RecyclerView (thường là chính RecyclerView).
            parent.getContext(): sẽ trả về Context của RecyclerView, mà RecyclerView lại thuộc về Activity → nên thực chất bạn đang lấy Context của Activity luôn.
        */
        ItemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_row, parent, false); // ItemRowBinding được sinh ra từ file item_row.xml
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position); // position do android tự động tạo, bắt đầu từ 0.
        holder.setBinding(user, position); // Truyền user và position vào viewholder vào cái itemView trong MyViewHolder
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemRowBinding itemView;
        private User user;

        public MyViewHolder(ItemRowBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
            itemView.getRoot().setOnClickListener(this); // chấp nhận
        }

        public void setBinding(User user, int position) {
            this.user = user;
            itemView.setUser(user); // Dùng biến user trong XML
            itemView.setStt(String.valueOf(position));
            itemView.executePendingBindings();
        }

        @Override
        public void onClick(View v) {  // chấp nhận (override method của View.OnClickListener)
            int vt = Integer.parseInt(itemView.getStt()); // Cần dùng nhiều chỗ ngoài onClick(), thì đặt biến toàn cuc trên MyViewHolder, rồi cập nhật constructor là được
            onItemClickListener.itemClick(user, vt);
        }

//        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnFocusChangeListener
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//
//        }
    }


    public interface OnItemClickListener {
        void itemClick(User user, int position); // Được override bên DataBindingRecyclerViewActivity.java
    }
}
