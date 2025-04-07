package vn.iostar.baitap04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iostar.baitap04.R;
import vn.iostar.baitap04.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> { // Hai tham số trong <> là classAdapter và cái classViewHolder trong classAdapter, Còn trong bài 06 có nhiều viewHolder nên để chung chung là <RecyclerView.ViewHolder>
    private Context mContext; // Màn hình hiển thị
    private List<Song> mSongs;  // Danh sách bài hát

    public SongAdapter(Context context, List<Song> datas) {
        mContext = context;
        mSongs = datas;
    }

    // Phải override 3 hàm dưới
    // Tạo ViewHolder (đại diện cho View của mỗi item)
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // @NonNull (nếu có): Annotation này chỉ ra rằng tham số parent không được phép là null.
        // parent: Là RecyclerView chứa các item con.
        // viewType (chưa dùng) cho phép bạn tạo ra nhiều kiểu item khác nhau trong một RecyclerView.

        //  nếu RecyclerView đã cached lại ViewHolder thì phương thức này sẽ không được gọi
        // Inflate view from row_item_song.xml
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext); // Dùng để "bơm" giao diện từ file XML vào View.
        View itemView = mLayoutInflater.inflate(R.layout.row_item_song, parent, false);
        return new SongViewHolder(itemView);
    }

    // Gắn dữ liệu vào ViewHolder
    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = mSongs.get(position);
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


    class SongViewHolder extends RecyclerView.ViewHolder {
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
                    Song song = mSongs.get(getAdapterPosition());
                    Toast.makeText(mContext, song.getMTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
