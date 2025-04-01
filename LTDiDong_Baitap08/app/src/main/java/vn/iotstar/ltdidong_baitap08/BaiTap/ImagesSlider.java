package vn.iotstar.ltdidong_baitap08.BaiTap;

import java.io.Serializable;

public class ImagesSlider implements Serializable {
    private int id;
    private int position;
    private String avatar;

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public String getAvatar() {
        return avatar;
    }
}