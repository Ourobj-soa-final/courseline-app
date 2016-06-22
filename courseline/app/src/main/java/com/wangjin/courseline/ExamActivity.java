package com.wangjin.courseline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Map;

/**
 * Created by zhengsuren on 16/6/21.
 */
public class ExamActivity extends Activity {

    private ListView listView;
    private JsonAdapter adapter;
    private Button addExam_button,delExam_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity);

        listView = (ListView) findViewById(R.id.mlistview);
        addExam_button = (Button) findViewById(R.id.auto_add_exam);
        delExam_button = (Button) findViewById(R.id.delete_exam);

        adapter = new JsonAdapter(this);

        addExam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExamInput();
            }
        });

        delExam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://smallpath.net/exams/userid/" + Saver.getUserId();
                HttpRequestUtils.getInstance().delete(url, new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(String response) {
                        refresh();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        });

        refresh();
    }

    private void showExamInput()
    {
        //将学院网上的考试信息导入
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("输入你的教务账号");
        View v = getLayoutInflater().inflate(R.layout.course_input_dialogview,null);
        builder.setView(v);
        final EditText num = (EditText) v.findViewById(R.id.xuehao);
        final EditText pwd = (EditText) v.findViewById(R.id.mima);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String student_Id = num.getText().toString();
                String student_pwd = pwd.getText().toString();
                HttpRequestUtils.getInstance().getJson("http://121.42.38.10:8080/courselineServer/getexamsinfo?id="
                        + student_Id + "&password=" + student_pwd, new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(String response) {
                        List<Exam> exams = JsonParser.parseExamFromJson(response);
                        //保存学生账号密码,使得考试列表保存
                        Saver.saveStudent(student_Id,student_pwd);
                        saveExams(exams);
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

    private void showDeleteDialog(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("是否删除该考试?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HttpRequestUtils.getInstance().delete("http://smallpath.net/exams/id/" + id, new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(String response) {
                        refresh();

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

    private void saveExams(List<Exam> exams){
        int userid = Saver.getUserId();
        for (Exam exam : exams) {
            Map<String,String> parms = new HashMap<>();
            parms.put("name",exam.getSubject());
            parms.put("place",exam.getLocation());

            String starttime;
            String endtime;

            starttime = exam.getDate() + " " + exam.getStart_time();
            endtime = exam.getDate() + " " + exam.getEnd_time();
            parms.put("starttime",starttime);
            parms.put("endtime",endtime);

            //注意参数格式!!!yyyy-mm-dd hh:mm:ss
            /*if (!starttime.isEmpty() || !endtime.isEmpty())
            {
                starttime = exam.getDate() + " " + exam.getStart_time();
                endtime = exam.getDate() + " " + exam.getEnd_time();
                parms.put("starttime",starttime);
                parms.put("endtime",endtime);
            }
            else {
                parms.put("starttime","2016-06-28 16:00");
                parms.put("endtime","2016-06-28 16:00");
                System.out.println("check success~~~~~!!!");
            }*/

            parms.put("userid",String.valueOf(userid));
            HttpRequestUtils.getInstance().postJson("http://smallpath.net/exams", parms, new HttpRequestUtils.onResponseFinishedListener() {
                @Override
                public void onFinish(String response) {

                }
                @Override
                public void onError(VolleyError error) {

                }
            });
        }

        refresh();
    }

    private void refresh()
    {
        if (!Saver.getStudentId().isEmpty() && Saver.getUserId() != -1)
        {
            String url = "http://smallpath.net/exams/userid/" + Saver.getUserId();

            HttpRequestUtils.getInstance().getJson(url,new HttpRequestUtils.onResponseFinishedListener()
            {
                @Override
                public void onFinish(String response) {
                    List<Exam> exams = JsonParser.parseExam(response);
                    //将考试数据存入适配器,返回给页面
                    adapter.setData(exams);
                    listView.setAdapter(adapter);

                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String mid = exams.get(position).getId();
                            showDeleteDialog(mid);
                            return false;
                        }
                    });
                    Log.d("ddebug", response);
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }
}
