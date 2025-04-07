package vn.iostar.baitap04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.iostar.baitap04.model.MonHoc;
import vn.iostar.baitap04.R;


public class MonHocAdapter extends BaseAdapter {
    //khai báo
    private Context context;
    private int layout;
    private List<MonHoc> monHocList;

    // Context: màn hình hiển thị
    // Dạng Layout cho mỗi item
    // Dữ liệu arrayList
    public MonHocAdapter(Context context, int layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;

    }

    // Phải Override 4 hàm dưới

    // Phương thức này xác định số lượng phần tử mà adapter sẽ hiển thị.
    @Override
    public int getCount() {
        return  monHocList == null ? 0 :monHocList.size();
    }

    // 2 cái dưới chắc hiện tại chưa cần nên k cần đụng vào
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //lấy context
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //gọi view chứa layout
        convertView = inflater.inflate(layout,null);
        //ánh xạ view
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textDesc = (TextView)  convertView.findViewById(R.id.textDesc);
        ImageView imagePic = (ImageView) convertView.findViewById(R.id.imagePic);
        //gán giá trị
        MonHoc monHoc = monHocList.get(position);
        textName.setText(monHoc.getName());
        textDesc.setText(monHoc.getDesc());
        imagePic.setImageResource(monHoc.getPic());
        //trả về view
        return convertView;
    }
    */

    private class ViewHolder{
        TextView textName,textDesc;
        ImageView imagePic;
    }

    // Phương thức getView() được gọi mỗi khi ListView hoặc GridView cần hiển thị một mục trong danh sách.
    // Nó tạo và trả về một view cho từng item con, với dữ liệu tương ứng từ danh sách.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // position: là vị trí phần tử trong danh sách.
        // convertView: là view tái sử dụng, nó chứa layout của item con trước đó trong danh sách mà không cần phải tái tạo lại từ đầu.
        // parent: Là ListView hoặc GridView chứa các item con.

        //khởi tạo viewholder
        ViewHolder viewHolder;
        //lấy context
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //gọi view chứa layout
            convertView = inflater.inflate(layout,null);  // Dùng viewHolder giúp tránh việc LUÔN tạo mới View, tạo View mới và findViewById quá nhiều.
            //ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.textName = convertView.findViewById(R.id.textName);
            viewHolder.textDesc = convertView.findViewById(R.id.textDesc);
            viewHolder.imagePic = convertView.findViewById(R.id.imagePic);
            convertView.setTag(viewHolder); // Lưu viewHolder vào convertView để tái sử dụng.

        }else{
            viewHolder= (ViewHolder) convertView.getTag(); // Lấy convertView ra gắn vào viewHolder để tiếp tục sử dụng mà k cần findViewById
        }

        //gán giá trị
        MonHoc monHoc = monHocList.get(position);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());
        //trả về view
        return convertView;
    }
}
