package vn.iostar.baitap04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iostar.baitap04.R;
import vn.iostar.baitap04.model.User;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{ // Do nhiều viewHolder nên để chung là "RecyclerView.ViewHolder"
    private Context mContext;
    private List<Object> mObjects;
    public static final int TEXT = 0;
    public static final int IMAGE = 1;
    public static final int USER = 2;
    public UserAdapter(Context context, List<Object> objects) {
        mContext = context;
        mObjects = objects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        switch (viewType) {
            case TEXT:
                View itemView0 = li.inflate(R.layout.row_text, parent, false);
                return new TextViewHolder(itemView0);
            case IMAGE:
                View itemView1 = li.inflate(R.layout.row_image, parent,false);
                return new ImageViewHolder(itemView1);
            case USER:
                View itemView2 = li.inflate(R.layout.row_user, parent,false);
                return new UserViewHolder(itemView2);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == TEXT) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.tvText.setText(mObjects.get(position).toString());
        } else if (viewType == IMAGE) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.imvImage.setImageResource((int) mObjects.get(position));
        } else if (viewType == USER) {
            User user = (User) mObjects.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvName.setText(user.getName());
            userViewHolder.tvAddress.setText(user.getAddress());
        }
    }


    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    // Ngoài 3 hàm trên còn phải override thêm
    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof String)
            return TEXT;
        else if (mObjects.get(position) instanceof Integer)
            return IMAGE;
        else if (mObjects.get(position) instanceof User)
            return USER;
        return -1;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;

        public TextViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);

            itemView.setOnClickListener(view -> {
                Toast.makeText(mContext, mObjects.get(getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imv_image);

            itemView.setOnClickListener(view -> {
                Toast.makeText(mContext, mObjects.get(getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAddress;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(view -> {
                User user = (User) mObjects.get(getAdapterPosition());
                Toast.makeText(mContext, user.getName() + ", " + user.getAddress(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
