package vn.iostar.baitap04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MonHocAdapter extends BaseAdapter {
    //khai báo
    private Context context;
    private int layout;
    private List<MonHoc> monHocList;

    public MonHocAdapter(Context context, int layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }

    //trả về số dòng
    @Override
    public int getCount() {
        return  monHocList == null ? 0 :monHocList.size(); //lấy kích thước monhoclist
    }

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { // K dùng cái ViewGroup nen dont care
        // i (position) là vị trí phần tử trong danh sách.
        // view (convertView) là View cũ được tái sử dụng để hiển thị dữ liệu mới,
        // viewGroup (parent) là danh sách chứa các View con (ListView, GridView...).


        //khởi tạo viewholder
        ViewHolder viewHolder;
        //lấy context
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //gọi view chứa layout
            view = inflater.inflate(layout,null);  // Dùng viewHolder giúp tranh việc LUÔN tạo mới View, gây tốn tài nguyên
            //ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView) view.findViewById(R.id.textName);
            viewHolder.textDesc = (TextView)  view.findViewById(R.id.textDesc);
            viewHolder.imagePic = (ImageView) view.findViewById(R.id.imagePic);
            view.setTag(viewHolder);
        // Mục đích sử dụng ViewHolder, để tái sử dụng View, nhằm tránh việc tạo View mới và findViewById quá nhiều.

        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        //gán giá trị
        MonHoc monHoc = monHocList.get(i);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());
        //trả về view
        return view;
    }
}
