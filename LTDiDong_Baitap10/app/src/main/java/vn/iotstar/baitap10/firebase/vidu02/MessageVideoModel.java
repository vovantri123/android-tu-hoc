package vn.iotstar.baitap10.firebase.vidu02;

import java.io.Serializable;
import java.util.List;

public class MessageVideoModel implements Serializable {
    private boolean success;
    private String message;
    private List<VideoModel> result;

    // Constructor
    public MessageVideoModel(boolean success, String message, List<VideoModel> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    // Getter and Setter methods
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VideoModel> getResult() {
        return result;
    }

    public void setResult(List<VideoModel> result) {
        this.result = result;
    }
}