package vn.iostar.baitap04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.iostar.baitap04.adapter.SongAdapter;
import vn.iostar.baitap04.model.Song;

public class Bai05RecyclerViewActitivy extends AppCompatActivity {
    //khai báo
    private RecyclerView rvSongs;  // RecyclerView để hiển thị danh sách bài hát.
    private SongAdapter mSongAdapter; // Adapter giúp hiển thị dữ liệu lên RecyclerView.
    private ArrayList<Song> mSongs; // Danh sách các bài hát.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai05_recyclerview);

        // Ánh xạ RecyclerView trong layout với biến rvSongs
        rvSongs = findViewById(R.id.rv_songs);

        // Create song data
        mSongs = new ArrayList<>();
        mSongs.add(new Song("60696","NEU EM CÒN TỔN TẠI","Khi anh bắt đầu 1 tinh yêu Là lúc anh tự thay","Trinh Đinh Quang"));
        mSongs.add(new Song( "60701","NGOC", "Co rat nhieu nhung cau chuyen Em dau rieng minh em biết", "Khac Viet"));
        mSongs.add(new Song( "68658","HAY TIN ANH LAN NUA", "Dau cho ta da sai khi o ben nhau Co yeu thương", "Thien Dong"));
        mSongs.add(new Song( "68618","CHUOI NGÀY VANG EM", "Từ khi em bước ra đi coi lòng anh ngập trang bao", "Duy Cuong"));
        mSongs.add(new Song( "68656","KHI NGƯỜI MINH YÊU KHOC", "Nưoc mat em dang roi tren những ngon tay Nước mat em", "Pham Mạnh Quỳnh"));
        mSongs.add(new Song( "68685","HỞ", "Anh mơ gặp em anh mơ được om anh mơ đuợc gan",  "Trinh Thang Binh"));
        mSongs.add(new Song( "60752","TINH YÊU CHẤP VA", "Muon đi xa noi yeu thương minh từng co Đe khong nghe","Mr. Siro"));
        mSongs.add(new Song( "68608","CHO NGAY MUA TAN", "1 ngày mưa va em khuất xa nơi anh bong dang cứ", "Trung Dức"));
        mSongs.add(new Song( "68603","CÂU HỎI EM CHƯA TRẢ LỜI", "Cần nơi em 1 lời giải thích thật lòng Dừng lặng im", "Yuki Huy Nam"));
        mSongs.add(new Song( "68728","QUA ĐI LẶNG LỄ", "Đôi khi đến với nhau yêu thương chẳng được lâu nhưng khi", "Phan Mạnh Quỳnh"));
        mSongs.add(new Song( "60856","QUÊN ANH LÀ DIỂU EM KHÔNG THE - REMIX", "Cần thêm bao lâu để em quên đi niềm đâu Cần thêm", "Thien Ngon"));

        mSongAdapter = new SongAdapter( // Adapter của recycler view k cần tham số Dạng Layout cho mỗi item, mà nó được truyền thẳng trong class Adapter
                this,
                mSongs
        ); // Khởi tạo SongAdapter và truyền Context là màn hình hiển thị hiện tại (this) + ArrayList

        // Truyền dữ liệu từ adapter ra recycler view
        rvSongs.setAdapter(mSongAdapter);  // Gán mSongAdapter vào RecyclerView.

        // Tùy chỉnh hiển thị theo kiểu ListView (ngang và dọc) hay GridView,...
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false); // Thử thay thành LinearLayoutManager.HORIZONTAL
        rvSongs.setLayoutManager(linearLayoutManager);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);  // 2 cột
//        rvSongs.setLayoutManager(gridLayoutManager);
    }
}
