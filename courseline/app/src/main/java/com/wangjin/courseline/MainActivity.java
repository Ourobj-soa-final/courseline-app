package com.wangjin.courseline;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.courseline.NetWork.HttpRequestUtils;
import com.wangjin.courseline.View.MHorizontalScrollView;
import com.wangjin.courseline.model.Course;
import com.wangjin.courseline.model.CourseTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private int itemHeight;
    FrameLayout[] panels = new FrameLayout[7];
    private DisplayMetrics metrics;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_layout);

        

        add = (ImageView) findViewById(R.id.add_course);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivity(i);
            }
        });
        metrics = new DisplayMetrics();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        itemHeight = (int) (75 * metrics.density);
        System.out.println(itemHeight);
        for (int i = 0; i < panels.length; i++) {
            panels[i] = (FrameLayout) findViewById(R.id.course_panel1 + i);
        }
        MHorizontalScrollView mHorizontalScrollView = (MHorizontalScrollView) findViewById(R.id.mhsv);
        LinearLayout courseLayout = (LinearLayout) findViewById(R.id.course_layout);
        mHorizontalScrollView.init(courseLayout, metrics);

        getDataAndShow();

    }

    private void addCourse(Course course) {
        CourseTime ct = course.getTime();
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                itemHeight * (ct.getEndTime() - ct.getBeginTime() + 1));
        //lp.setMargins(0,itemHeight * (ct.getBeginTime() - 1), 0, 0);
        tv.setY(itemHeight * (ct.getBeginTime() - 1));
        tv.setLayoutParams(lp);
        //tv.setGravity(Gravity.TOP);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(12);
        tv.setText(course.getName() +"\n" + course.getTime().getPlace() + "\n" +course.getTeacherName());
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        //tv.setY(itemHeight*ct.getBeginTime());
        panels[ct.getWeek() - 1].addView(tv);
    }



    private void getDataAndShow(){
        int userid = Saver.getUserId();
        HttpRequestUtils.getInstance().getJson("http://smallpath.net/courses/userid/" + userid,
                new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONArray courses = new JSONArray(response);
                            for (int i = 0;i < courses.length();i++){
                                addCourse(parseJSON(courses.getJSONObject(i)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    public Course parseJSON(JSONObject js) throws JSONException {
        Course c = new Course();
        c.setName(js.getString("course_name"));
        c.setTeacherName(js.getString("teacher_name"));
        CourseTime ct = new CourseTime();
        ct.setBeginTime(js.getInt("start_number"));
        ct.setEndTime(js.getInt("end_number"));
        ct.setPlace(js.getString("class_room"));
        ct.setWeek(js.getInt("week"));
        c.setTime(ct);
        return c;
    }


}
