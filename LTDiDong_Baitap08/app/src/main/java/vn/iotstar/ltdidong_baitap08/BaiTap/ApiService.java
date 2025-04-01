package vn.iotstar.ltdidong_baitap08.BaiTap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("appfoods/newimagesmanager.php")
    Call<ImageResponse> getImages(@Field("position") String position);
}