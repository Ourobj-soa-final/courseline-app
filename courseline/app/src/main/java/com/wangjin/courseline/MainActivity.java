package com.wangjin.courseline;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangjin.courseline.View.MHorizontalScrollView;
import com.wangjin.courseline.model.Course;
import com.wangjin.courseline.model.CourseTime;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int itemHeight;
    FrameLayout[] panels = new FrameLayout[7];
    private DisplayMetrics metrics;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_layout);
        metrics = new DisplayMetrics();
        ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        itemHeight = (int)(75 * metrics.density);
        System.out.println(itemHeight);
        for (int i = 0;i < panels.length;i++){
            panels[i] = (FrameLayout) findViewById(R.id.course_panel1+i);
        }
        MHorizontalScrollView mHorizontalScrollView = (MHorizontalScrollView) findViewById(R.id.mhsv);
        LinearLayout courseLayout = (LinearLayout) findViewById(R.id.course_layout);
        mHorizontalScrollView.init(courseLayout,metrics);

        Course c = new Course();
        ArrayList<CourseTime> times = new ArrayList<>();
        CourseTime time = new CourseTime();
        time.setBeginTime(3);
        time.setEndTime(4);
        time.setWeek(1);
        times.add(time);
        c.setTimes(times);
        CourseTime time1 = new CourseTime();
        time1.setBeginTime(7);
        time1.setEndTime(8);
        time1.setWeek(1);
        times.add(time1);
        c.setTimes(times);
        addCourse(c);

    }

    private void addCourse(Course course){
        List<CourseTime> times = course.getTimes();
        for (CourseTime ct : times){
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT ,
                    itemHeight*(ct.getEndTime() - ct.getBeginTime() + 1));
            //lp.setMargins(0,itemHeight * (ct.getBeginTime() - 1), 0, 0);
            tv.setY(itemHeight * (ct.getBeginTime() - 1));
            tv.setLayoutParams(lp);
            //tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setText("test");
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            //tv.setY(itemHeight*ct.getBeginTime());
            panels[ct.getWeek() - 1].addView(tv);
        }

    }
}
