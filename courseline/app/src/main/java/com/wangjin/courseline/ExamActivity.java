package com.wangjin.courseline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.VolleyError;
import com.wangjin.courseline.NetWork.HttpRequestUtils;
import com.wangjin.courseline.Utils.JsonAdapter;
import com.wangjin.courseline.Utils.JsonParser;
import com.wangjin.courseline.model.Course;
import com.wangjin.courseline.model.Exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhengsuren on 16/6/21.
 */
public class ExamActivity extends Activity {

    private ListView listView;
    private JsonAdapter adapter;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity);

        listView = (ListView) findViewById(R.id.mlistview);
        button = (Button) findViewById(R.id.auto_add_exam);

        //创建键值对,存储要填入的值
       /* ArrayList<HashMap<String,String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("subject","测试科目1");
        map.put("begin","10:00");
        map.put("end","12:00");
        map.put("date","12月28日");
        mylist.add(map);

        SimpleAdapter mAdapter = new SimpleAdapter(this,mylist,R.layout.exam_item,
                new String[]{"subject","begin","end","date"},new int[]{R.id.subject,R.id.exbegin,R.id.exend,R.id.exdate});

        listView.setAdapter(mAdapter);*/
        adapter = new JsonAdapter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExamInput();
            }
        });
    }

    private void showExamInput()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("输入你的教务账号");
        View v = getLayoutInflater().inflate(R.layout.course_input_dialogview,null);
        builder.setView(v);
        final EditText num = (EditText) v.findViewById(R.id.xuehao);
        final EditText pwd = (EditText) v.findViewById(R.id.mima);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String xuehao = num.getText().toString();
                String mima = pwd.getText().toString();
                HttpRequestUtils.getInstance().getJson("http://121.42.38.10:8080/courselineServer/getexamsinfo?id="
                        + xuehao + "&password=" + mima, new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(String response) {
                        List<Exam> exams = JsonParser.parseExamFromJson(response);
                        //将考试数据存入适配器,返回给页面
                        adapter.setData(exams);
                        listView.setAdapter(adapter);
                        Log.d("ddebug", response);

                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }
}
