package vn.iostar.ltdidong_baitap06;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category {
    @SerializedName("id") // Annotation này là của thư viện Gson, thường dùng với Retrofit. (@SerializedName("id") sẽ map với  "id" trong JSON, rồi gán cho private int id;)
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("images")
    private String images;
    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
