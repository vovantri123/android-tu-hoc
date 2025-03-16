package vn.iostar.baitap04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private static final String TAG = "SongAdapter";  // Không thấy dùng
    private List<SongModel> mSongs;  // Danh sách bài hát
    private Context mContext;  // Trong Android, Context là một lớp quan trọng cung cấp thông tin về trạng thái hiện tại của ứng dụng. Nó giúp truy cập tài nguyên, khởi chạy activity, hiển thị Toast, và nhiều chức năng khác.
    private LayoutInflater mLayoutInflater;  // Dùng để "bơm" giao diện từ file XML vào View.
    public SongAdapter(Context context, List<SongModel> datas) {
        mContext = context;
        mSongs = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //  dùng để tạo View mới  cho RecyclerView, nếu RecyclerView đã cached lại View thì phương thức này sẽ không được gọi
        // Inflate view from row_item_song.xml
        View itemView = mLayoutInflater.inflate(R.layout.row_item_song, parent, false);
        return new SongViewHolder(itemView);  // Trả về một SongViewHolder để quản lý View đó.
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {  // Dùng để gán dữ liệu vào View
        // Get song in mSong via position
        SongModel song = mSongs.get(position);
        //bind data to viewholder
        holder.tvCode.setText(song.getMCode());
        holder.tvTitle.setText(song.getMTitle());
        holder.tvLyric.setText(song.getMLyric());
        holder.tvArtist.setText(song.getMArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class SongViewHolder extends RecyclerView. ViewHolder {
        private TextView tvCode;
        private TextView tvTitle;
        private TextView tvLyric;
        private TextView tvArtist;

        public SongViewHolder(View itemView) {
            super(itemView);
            tvCode = (TextView) itemView.findViewById(R.id.tv_code);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvLyric = (TextView) itemView.findViewById(R.id.tv_lyric);
            tvArtist = (TextView) itemView.findViewById(R.id.tv_artist);

            //thiết lập sự kiện
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SongModel song = mSongs.get(getAdapterPosition());
                    Toast.makeText(mContext, song.getMTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
