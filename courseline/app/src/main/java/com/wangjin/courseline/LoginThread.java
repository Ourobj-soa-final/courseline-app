package soa_final.courseline;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhengsuren on 16/6/11.
 */
public class LoginThread extends Thread {
    private String url,email,password;
    private Handler handler;
    private Context context;

    public LoginThread(String email,String password,Handler handler,Context context)
    {
        this.email = email;
        this.password = password;
        this.handler = handler;
        this.context = context;
    }

    private void httpRequest()
    {
        //请求登录
        url =  "http://smallpath.net/users/email/"+ email + "/password/"+ password;

        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);

            //将请求到的数据缓存
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;

            while ((str = bufferedReader.readLine()) != null)
            {
                sb.append(str);
            }

            System.out.println(sb);

            int result = parseJson(sb.toString());

            handler.sendEmptyMessage(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseJson(String json)
    {
        //解析请求到的json数据
        try {
            JSONObject jsonObject = new JSONObject(json);
            int errcode = jsonObject.getInt("code");
            return errcode;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void doGet()
    {
        httpRequest();
    }

    @Override
    public void run() {
        Looper.prepare();
        doGet();
    }
}
