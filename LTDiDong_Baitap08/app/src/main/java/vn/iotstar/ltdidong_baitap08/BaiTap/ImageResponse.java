package vn.iotstar.ltdidong_baitap08.BaiTap;


import java.util.List;

public class ImageResponse {
    private boolean success;
    private String message;
    private List<ImagesSlider> result;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ImagesSlider> getResult() {
        return result;
    }
}
