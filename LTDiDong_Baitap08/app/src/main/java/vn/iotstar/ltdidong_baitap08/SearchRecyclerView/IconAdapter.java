package vn.iotstar.ltdidong_baitap08.SearchRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstar.ltdidong_baitap08.R;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {
    private Context context;
    private List<IconModel> arrayList;

    public IconAdapter(Context context, List<IconModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_icon_promotion, parent, false); // Sửa lỗi attachToRoot
        return new IconHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconHolder viewHolder, int position) {
        IconModel iconModel = arrayList.get(position);
        Glide.with(context).load(iconModel.getImgId()).into(viewHolder.imageView);
        viewHolder.tvIcon.setText(iconModel.getDesc());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0; // Sửa lỗi thiếu dấu `{}` và return
    }

    public static class IconHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvIcon;

        public IconHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImgIcon);
            tvIcon = itemView.findViewById(R.id.tvIcon);
        }
    }

    // Thêm phương thức để cập nhật danh sách IconModel
    public void setListenerList(List<IconModel> iconModelList) {
        this.arrayList = iconModelList;
        notifyDataSetChanged(); // Cập nhật lại RecyclerView
    }
}
