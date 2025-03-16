package vn.iostar.ltdidong_baitap06;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> array;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_cate);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameCategory);

            // Bắt sự kiện cho item holder trong MyViewHolder
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý khi nhấp vào Item trên category
                    Toast.makeText(context, "Bạn đã chọn category " + tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.tenSp.setText(category.getName());

        // Load ảnh với Glide
        Glide.with(context)
                .load(category.getImages()) // URL hoặc đường dẫn hình ảnh
                .into(holder.images);  // ImageView để hiển thị ảnh

        /*
        Glide.with(context)
             .load("đường dẫn hình")
             .override(200, 300)     // Resize ảnh về kích thước 200x300
             .centerCrop()           // Cắt ảnh và canh giữa
             .fitCenter()            // Điền ảnh sao cho vừa với kích thước
             .placeholder(R.drawable.placeholder) // Hiển thị hình ảnh tạm thời khi ảnh chính đang được tải từ Internet hoặc từ nguồn dữ liệu nào đó
             .error(R.drawable.error)   // Ảnh hiển thị khi lỗi
             .into(imageView);     // ImageView để hiển thị ảnh
         */
    }

    @Override
    public int getItemCount() {
        return (array == null) ? 0 : array.size();
    }

}
