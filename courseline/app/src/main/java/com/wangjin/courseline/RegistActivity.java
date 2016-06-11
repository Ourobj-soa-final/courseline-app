package com.wangjin.courseline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zhengsuren on 16/6/3.
 */
public class RegistActivity extends AppCompatActivity {

    private EditText pwd;
    private EditText pwd_confirm;
    private Button regist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);

        pwd = (EditText) findViewById(R.id.pwd);
        pwd_confirm = (EditText) findViewById(R.id.pwd_confirm);

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

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password))
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

        if (!isPasswordConfirm(password,password_confirm))
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
}
