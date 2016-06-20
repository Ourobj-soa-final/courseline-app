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
import com.wangjin.courseline.Utils.JsonParser;
import com.wangjin.courseline.View.MHorizontalScrollView;
import com.wangjin.courseline.model.Course;
import com.wangjin.courseline.model.CourseTime;
import com.wangjin.courseline.model.Exam;

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

    //String res = "{\"courses\": [{\"id\":\"1\",\"courseId\":\"07037616\",\"courseName\":\"马克思主义基本原理\",\"obligatory\":\"必修\",\"exam\":\"考试\",\"remark\":\"\",\"credit\":\"3.0\",\"teacher\":\"苏开贵\",\"classArrangement\":\"苏开贵 星期一 1-2 [1-17] 广楼G107\",\"campus\":\"嘉定校区\"},{\"id\":\"2\",\"courseId\":\"320004B3\",\"courseName\":\"体育(4)\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"男女混合，羽毛球\",\"credit\":\"1.0\",\"teacher\":\"何传华\",\"classArrangement\":\"何传华 星期一 5-6 [1-17] 嘉定篮球场\",\"campus\":\"嘉定校区\"},{\"id\":\"3\",\"courseId\":\"36000703\",\"courseName\":\"世界大战与局部战争\",\"obligatory\":\"选修\",\"exam\":\"考查\",\"remark\":\"\",\"credit\":\"1.5\",\"teacher\":\"郑义炜\",\"classArrangement\":\"郑义炜 星期四 5-6 [1-17] 复楼F211\",\"campus\":\"嘉定校区\"},{\"id\":\"4\",\"courseId\":\"36002605\",\"courseName\":\"大学生安全教育\",\"obligatory\":\"选修\",\"exam\":\"考查\",\"remark\":\"\",\"credit\":\"1.5\",\"teacher\":\"张国清\",\"classArrangement\":\"张国清 星期一 9-10 [1-17] 复楼F211\",\"campus\":\"嘉定校区\"},{\"id\":\"5\",\"courseId\":\"42002801\",\"courseName\":\"软件项目管理\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\" 授课语言：中\",\"credit\":\"3.0\",\"teacher\":\"杜庆峰\",\"classArrangement\":\"杜庆峰 星期三 1-2 [1-17] 济事楼430 \\u003cbr\\u003e杜庆峰 星期五 1-2 单[1-17] 济事楼430\",\"campus\":\"嘉定校区\"},{\"id\":\"6\",\"courseId\":\"42011102\",\"courseName\":\"软件测试技术\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"\",\"credit\":\"3.0\",\"teacher\":\"杜庆峰\",\"classArrangement\":\"杜庆峰 星期三 3-4 单[1-17] 济事楼430 \\u003cbr\\u003e杜庆峰 星期五 3-4 [1-17] 济事楼430\",\"campus\":\"嘉定校区\"},{\"id\":\"7\",\"courseId\":\"42017001\",\"courseName\":\"信息安全基础\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"网络与主机软件课程群\",\"credit\":\"3.0\",\"teacher\":\"尹长青\",\"classArrangement\":\"尹长青 星期二 3-4 单[1-17] 济事楼416 \\u003cbr\\u003e尹长青 星期四 7-8 [1-17] 济事楼416\",\"campus\":\"嘉定校区\"},{\"id\":\"8\",\"courseId\":\"42030201\",\"courseName\":\"Web服务与SOA应用\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"网络与主机软件方向 授课语言：英\",\"credit\":\"3.0\",\"teacher\":\"刘岩\",\"classArrangement\":\"刘岩 星期一 7-8 双[2-16] 济事楼516 \\u003cbr\\u003e刘岩 星期三 5-6 [1-17] 济事楼516\",\"campus\":\"嘉定校区\"},{\"id\":\"9\",\"courseId\":\"42031701\",\"courseName\":\"主机系统管理\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"网络与主机软件课程群\",\"credit\":\"3.0\",\"teacher\":\"高珍\",\"classArrangement\":\"高珍 星期一 3-4 [1-17] 济事楼516 \\u003cbr\\u003e高珍 星期三 3-4 双[2-16] 济事楼516\",\"campus\":\"嘉定校区\"},{\"id\":\"10\",\"courseId\":\"42033106\",\"courseName\":\"社会调查\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"\",\"credit\":\"0.5\",\"teacher\":\"王冬青\",\"classArrangement\":\"王冬青 星期二 1-2 双[2-16] 临时申请\",\"campus\":\"四平路校区\"},{\"id\":\"11\",\"courseId\":\"42033204\",\"courseName\":\"专业方向综合项目\",\"obligatory\":\"必修\",\"exam\":\"考查\",\"remark\":\"网络与主机软件课程群\",\"credit\":\"3.0\",\"teacher\":\"何宗键\",\"classArrangement\":\"何宗键 星期三 7-8 [1-17] 济事楼416 \\u003cbr\\u003e何宗键 星期五 7-8 单[1-17] 济事楼416\",\"campus\":\"嘉定校区\"}]}";
    //String res = "{\"exams\": [{\"id\":\"1\",\"courseId\":\"36002605\",\"courseName\":\"大学生安全教育\",\"date\":\"2016-06-20\",\"examArrangement\":\"2016-06-20 \\u003cbr/\\u003e第17周 星期一 \\u003cbr/\\u003e 18:30-20:10\",\"place\":\"复楼F211\",\"station\":\"正常\",\"remark\":\"开卷考试\",\"otherIntroduction\":\"\"},{\"id\":\"2\",\"courseId\":\"36000703\",\"courseName\":\"世界大战与局部战争\",\"date\":\"2016-06-23\",\"examArrangement\":\"2016-06-23 \\u003cbr/\\u003e第17周 星期四 \\u003cbr/\\u003e 13:30-15:05\",\"place\":\"复楼F211\",\"station\":\"正常\",\"remark\":\"提交论文\",\"otherIntroduction\":\"\"},{\"id\":\"3\",\"courseId\":\"07037616\",\"courseName\":\"马克思主义基本原理\",\"date\":\"2016-06-29\",\"examArrangement\":\"2016-06-29 \\u003cbr/\\u003e第18周 星期三 \\u003cbr/\\u003e 10:30-12:30\",\"place\":\"广楼G207\",\"station\":\"正常\",\"remark\":\"\",\"otherIntroduction\":\"\"},{\"id\":\"4\",\"courseId\":\"42033106\",\"courseName\":\"社会调查\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"学院网站“信息中心”已发布相关通知，第13周已经截止材料提交。\",\"otherIntroduction\":\"\"},{\"id\":\"5\",\"courseId\":\"42017001\",\"courseName\":\"信息安全基础\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"项目答辩 时间：第17周周四7.8节随堂 地点：济事楼416\",\"otherIntroduction\":\"\"},{\"id\":\"6\",\"courseId\":\"42031701\",\"courseName\":\"主机系统管理\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"1.作业提交方式：作业在主机上在线提交，归档资料提交到服务器10.60.41.1的zOS课程目录下。 2.截止时间：每次布置作业后一周之内。\",\"otherIntroduction\":\"\"},{\"id\":\"7\",\"courseId\":\"320004B3\",\"courseName\":\"体育(4)\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"随堂测试立定跳远，50米跑，1000米跑以及课外锻炼综合给予期末成绩。\",\"otherIntroduction\":\"\"},{\"id\":\"8\",\"courseId\":\"42033204\",\"courseName\":\"专业方向综合项目\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"项目答辩： 时间：6月15日（周三）15:25 地点：济事楼416\",\"otherIntroduction\":\"\"},{\"id\":\"9\",\"courseId\":\"42030201\",\"courseName\":\"Web服务与SOA应用\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"项目答辩 时间：第17周周三5、6节 地点：济事楼516\",\"otherIntroduction\":\"\"},{\"id\":\"10\",\"courseId\":\"42011102\",\"courseName\":\"软件测试技术\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"项目答辩 具体时间和地点安排请关注任课教师课堂通知。\",\"otherIntroduction\":\"\"},{\"id\":\"11\",\"courseId\":\"42002801\",\"courseName\":\"软件项目管理\",\"date\":\"\",\"examArrangement\":\"\",\"place\":\"\",\"station\":\"正常\",\"remark\":\"项目答辩 具体时间和地点安排请关注任课教师课堂通知。\",\"otherIntroduction\":\"\"}]}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_layout);

        //List<Course> courses = JsonParser.parseCourseFromJson(res);
        //Log.d("ddebug","coursesize:"+courses.size());
        //for (Course c : courses){
        //    Log.d("ddebug",c.getName() + " " + c.getTeacherName() + " " + c.getTime().getPlace() + " " +c.getTime().getBeginTime() + " " +c.getTime().getEndTime() + " " + c.getTime().getWeek());
       // }
        //List<Exam> exams = JsonParser.parseExamFromJson(res);
        //for (Exam e : exams){
            //Log.d("ddebug",e.getSubject() + " " +e.getLocation() + " " +e.getStart_time() + " " +
            //e.getEnd_time() + " " + e.getDate() + " " +e.getRemark());
        //}

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
