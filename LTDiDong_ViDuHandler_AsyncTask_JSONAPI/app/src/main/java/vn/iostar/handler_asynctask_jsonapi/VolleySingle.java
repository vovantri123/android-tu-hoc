package vn.iostar.handler_asynctask_jsonapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingle {
    private static VolleySingle mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    // Khởi tạo constructor
    private VolleySingle(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    // Hàm lấy context
    public static synchronized VolleySingle getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingle(context);
        }
        return mInstance;
    }

    // Hàm RequestQueue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    // Hàm addToRequest
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
