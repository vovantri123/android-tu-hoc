package vn.iotstar.baitap10.firebase.vidu01;

import com.google.firebase.database.IgnoreExtraProperties; // Thêm cái này để tránh lỗi nếu có trường lạ

@IgnoreExtraProperties // Bỏ qua các trường không khớp khi đọc từ Firebase
public class Video1Model {
    private String title;
    private String desc;
    private String url;
    private String uid;
    private String email;
    private Long likeCount; // Đổi tên và kiểu dữ liệu
    private Long dislikeCount; // Đổi tên và kiểu dữ liệu

    // Firebase cần constructor không tham số
    public Video1Model() {
    }

    // Constructor cũ (có thể giữ lại nếu cần)
    public Video1Model(String title, String desc, String url, String uid, String email, Long likeCount, Long dislikeCount) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.uid = uid;
        this.email = email;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getUrl() { return url; }
    public String getUid() { return uid; }
    public String getEmail() { return email; }
    public Long getLikeCount() { return likeCount != null ? likeCount : 0L; } // Trả về 0 nếu null
    public Long getDislikeCount() { return dislikeCount != null ? dislikeCount : 0L; } // Trả về 0 nếu null

    // Setters (Firebase cần setters hoặc trường public)
    public void setTitle(String title) { this.title = title; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setUrl(String url) { this.url = url; }
    public void setUid(String uid) { this.uid = uid; }
    public void setEmail(String email) { this.email = email; }
    public void setLikeCount(Long likeCount) { this.likeCount = likeCount; }
    public void setDislikeCount(Long dislikeCount) { this.dislikeCount = dislikeCount; }
}