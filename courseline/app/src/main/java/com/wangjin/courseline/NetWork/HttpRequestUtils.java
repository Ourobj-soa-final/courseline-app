package com.wangjin.courseline.NetWork;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wangjin.courseline.BaseApplication;

import org.json.JSONObject;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjin on 16/5/9.
 */
public class HttpRequestUtils {

    private RequestQueue mRequestQueue;

    private static volatile HttpRequestUtils httpRequestUtils;

    private HttpRequestUtils(RequestQueue r) {
        this.mRequestQueue = r;
    }


    /**
     * 获得HttpRequestUtils的单例
     * */
    public static HttpRequestUtils getInstance() {
        if (httpRequestUtils == null) {
            synchronized (HttpRequestUtils.class) {
                if (httpRequestUtils == null) {
                    httpRequestUtils = new HttpRequestUtils(
                            Volley.newRequestQueue(BaseApplication.getInstance(), 10 * 1024 * 1024));
                }
            }
        }
        return httpRequestUtils;
    }

    /**
     * 发送一个GET请求，返回时调用回调接口onResponseFinishedListener的方法处理
     *
     * @param address 请求json的url地址
     * @param listener 监听并处理请求返回的json
     *
     **/
    public void getJson(String address, final onResponseFinishedListener listener) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(address, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onFinish(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        jsonObjectRequest.setShouldCache(false);
        mRequestQueue.add(jsonObjectRequest);
    }

    /**
     * 发送一个POST请求，返回时调用回调接口onResponseFinishedListener的方法处理
     *
     * @param address 请求json的url地址
     * @param params post参数的键值对
     * @param listener 监听并处理请求返回的json
     *
     **/
    public void postJson(String address,final Map<String,String> params,final onResponseFinishedListener listener) {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listener.onFinish(new JSONObject(response));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        mRequestQueue.add(stringRequest);
    }


    /**
     *  回调接口，处理请求的返回
     */
    public interface onResponseFinishedListener {

        //请求成功返回时调用
        void onFinish(JSONObject response);

        //请求错误时调用
        void onError(VolleyError error);
    }

}