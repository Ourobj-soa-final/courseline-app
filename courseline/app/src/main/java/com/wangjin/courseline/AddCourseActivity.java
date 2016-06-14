package com.wangjin.courseline;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.courseline.NetWork.HttpRequestUtils;
import com.wangjin.courseline.model.Course;
import com.wangjin.courseline.model.CourseTime;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {


    Button addCourseInfo;
    Button saveCourse;
    EditText coursNameEditText;
    EditText teacherNameEditText;
    LinearLayout coursetime;
    ArrayList<CourseTime> times = new ArrayList<>();
    String[] weekday = {"一","二","三","四","五","六","七","八","九","十","十一"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_course);

        addCourseInfo = (Button) findViewById(R.id.add_courseinfo);
        addCourseInfo.setOnClickListener(this);
        coursetime = (LinearLayout) findViewById(R.id.courseinfo);
        saveCourse = (Button) findViewById(R.id.save_course);
        saveCourse.setOnClickListener(this);
        coursNameEditText = (EditText) findViewById(R.id.course_name);
        teacherNameEditText = (EditText) findViewById(R.id.course_place);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_courseinfo:
                makeTimeDialog();
                break;
            case R.id.save_course:
                saveCourse();
                break;
        }
    }

    private void makeTimeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("添加课程时间");
        View v = getLayoutInflater().inflate(R.layout.timedialog_layout,null,false);
        final NumberPicker week = (NumberPicker) v.findViewById(R.id.week_picker);
        final NumberPicker begin = (NumberPicker) v.findViewById(R.id.begin_picker);
        final NumberPicker end = (NumberPicker) v.findViewById(R.id.end_picker);
        final EditText p = (EditText) v.findViewById(R.id.place);
        week.setMinValue(1);
        week.setMaxValue(7);
        begin.setMinValue(1);
        begin.setMaxValue(11);
        end.setMinValue(1);
        end.setMaxValue(11);
        begin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int endValue = end.getValue();
                if (newVal > endValue) {
                    end.setValue(newVal);
                }
            }
        });
        end.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int beginValue = begin.getValue();
                if (newVal < beginValue) {
                    begin.setValue(newVal);
                }
            }
        });
        builder.setView(v);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int weeknum = week.getValue();
                int beignnum = begin.getValue();
                int endnum = end.getValue();
                String place = p.getText().toString();
                final CourseTime time = new CourseTime();
                time.setWeek(weeknum);
                time.setBeginTime(beignnum);
                time.setEndTime(endnum);
                time.setPlace(place);
                times.add(time);
                final View view = getLayoutInflater().inflate(R.layout.courseinfo_item_layout,null,false);
                TextView t = (TextView) view.findViewById(R.id.time);
                t.setText("周" + weekday[weeknum - 1] + "  第" + weekday[beignnum - 1] + "节 到 第" + weekday[endnum - 1] + "节");
                TextView t2 = (TextView) view.findViewById(R.id.location);
                t2.setText(place);
                coursetime.addView(view);
                ImageView delete = (ImageView) view.findViewById(R.id.del_courseinfo);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coursetime.removeView(view);
                        times.remove(time);
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

    public void saveCourse(){
        int userid = Saver.getUserId();
        String courseName = coursNameEditText.getText().toString();
        String teacherName = teacherNameEditText.getText().toString();
        for (CourseTime time : times){
            Map<String,String> parms = new HashMap<>();
            parms.put("coursename",courseName);
            parms.put("week",String.valueOf(time.getWeek()));
            parms.put("startnumber",String.valueOf(time.getBeginTime()));
            parms.put("endnumber",String.valueOf(time.getEndTime()));
            parms.put("teachername",teacherName);
            parms.put("courseroom",time.getPlace());
            parms.put("userid",String.valueOf(userid));
            HttpRequestUtils.getInstance().postJson("http://smallpath.net/courses", parms, new HttpRequestUtils.onResponseFinishedListener() {
                @Override
                public void onFinish(String response) {
                    System.out.println(response);
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }

}
