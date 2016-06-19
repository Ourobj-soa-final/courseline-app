package com.wangjin.courseline;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.VolleyError;
import com.wangjin.courseline.NetWork.RegistThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengsuren on 16/6/3.
 */
public class RegistActivity extends AppCompatActivity {

    private EditText email;
    private EditText pwd,name;
    private EditText pwd_confirm;
    private Button regist;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);

        email = (EditText) findViewById(R.id.email_regist);
        pwd = (EditText) findViewById(R.id.pwd);
        pwd_confirm = (EditText) findViewById(R.id.pwd_confirm);
        name = (EditText) findViewById(R.id.nickname);
        context = this;

        Bundle bundle = this.getIntent().getExtras();
        String r_email = bundle.getString("email");
        email.setText(r_email);

        regist = (Button) findViewById(R.id.regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempRegist();
            }
        });
    }

    private boolean isPasswordValid(String password)
    {
        return password.length() > 5;
    }

    private boolean isPasswordConfirm(String password,String password_confirm)
    {
        return password_confirm.equals(password);
    }

    private void attempRegist()
    {
        String password = pwd.getText().toString();
        String password_confirm = pwd_confirm.getText().toString();
        String r_email = email.getText().toString();
        String r_name = name.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && isPasswordValid(password) &&
                isPasswordConfirm(password,password_confirm) && isEmailValid(r_email))
        {
            RegistThread thread = RegistThread.getInstance();
            Map<String,String> map = new HashMap<String,String>();
            map.put("name",r_name);
            map.put("email",r_email);
            map.put("password",password);

            System.out.println("邮箱，密码，昵称：～～～～～～" + r_email + password + r_name);

            //重写监听器，注册成功跳转登录页面，失败则弹出提示框
            RegistThread.onResponseFinishedListener mListener = new RegistThread.onResponseFinishedListener() {
                @Override
                public void onFinish(String response) {
                    //解析、处理返回的json数据
                    System.out.println(response);
                    int result = parse_json(response);//根据返回的json数据判断注册是否成功
                    if (0 == result)
                    {
                        Intent intent = new Intent(context,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (1 == result)
                    {
                        new  AlertDialog.Builder(context).setTitle("提示").setMessage("注册失败，该邮箱已存在！")
                                .setPositiveButton("确定",null).show();
                    }

                }
                @Override
                public void onError(VolleyError error) {
                    new  AlertDialog.Builder(context).setTitle("提示").setMessage("注册失败，请检查网络连接！")
                            .setPositiveButton("确定",null).show();
                }
            };

            thread.postJson("http://smallpath.net/users",map,mListener);
        }

        // Check for a valid email address.
        else if (TextUtils.isEmpty(r_email)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        }
        else if (!isEmailValid(r_email)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password))
        {
            pwd.setError(getString(R.string.error_field_required));
            focusView = pwd;
            cancel = true;
        }
        else if (!isPasswordValid(password))
        {
            pwd.setError(getString(R.string.error_invalid_password));
            focusView = pwd;
            cancel = true;
        }
        else if (!isPasswordConfirm(password,password_confirm))
        {
            pwd_confirm.setError(getString((R.string.error_password_confirm)));
            focusView = pwd_confirm;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
        }
    }

    private int parse_json(String json){
        try {
            JSONObject object = new JSONObject(json);
            int code = object.getInt("code");
            return code;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 1;
    }

    private boolean isEmailValid(String email) {
        //检查邮箱格式是否合法
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
