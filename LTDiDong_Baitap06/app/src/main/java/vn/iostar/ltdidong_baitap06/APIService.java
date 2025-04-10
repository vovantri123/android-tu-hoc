package vn.iostar.ltdidong_baitap06;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("categories.php")
    Call<ArrayList<Category>> getCategoryAll(); // Call<T> đại diện cho một yêu cầu HTTP bất đồng bộ (từng cái) hoặc đồng bộ (song song)
}
