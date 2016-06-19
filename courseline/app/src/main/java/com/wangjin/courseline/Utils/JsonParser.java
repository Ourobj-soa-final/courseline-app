package com.wangjin.courseline.Utils;

import com.wangjin.courseline.model.Course;
import com.wangjin.courseline.model.CourseTime;
import com.wangjin.courseline.model.Exam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjin on 16/6/19.
 */
public class JsonParser {
        public static List<Course> parseCourseFromJson(String js){
            List<Course> courses = new ArrayList<>();
            try {
                JSONObject res = new JSONObject(js);
                JSONArray a = res.getJSONArray("courses");
                for (int i = 0;i <a.length();i++){
                    JSONObject course = a.getJSONObject(i);
                    String courseName = course.getString("courseName");
                    String teacher = course.getString("teacher");
                    String classArrangeMent = course.getString("classArrangement");
                    String[] s = classArrangeMent.split("\\u003cbr\\u003e");
                    for (int j = 0;j < s.length;j++){
                        String[] ss = s[j].split(" ");
                        int week = getWeek(ss[1]);
                        String[] t = ss[2].split("-");
                        int beginTime = Integer.valueOf(t[0]);
                        int endTime = Integer.valueOf(t[1]);
                        String place = ss[4];
                        CourseTime time = new CourseTime();
                        time.setWeek(week);
                        time.setBeginTime(beginTime);
                        time.setEndTime(endTime);
                        time.setPlace(place);
                        Course c = new Course();
                        c.setTime(time);
                        c.setTeacherName(teacher);
                        c.setName(courseName);
                        courses.add(c);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return courses;
        }


    public static List<Exam> parseExamFromJson(String js) {
        List<Exam> exams = new ArrayList<>();
        try {
            JSONObject res = new JSONObject(js);
            JSONArray a = res.getJSONArray("exams");
            for (int i = 0;i < a.length();i++){
                JSONObject exam = a.getJSONObject(i);
                String name = "";
                String date = "";
                String arrangement = "";
                String place = "";
                String[] begin_end = {"",""};
                name = exam.getString("courseName");
                date = exam.getString("date");
                arrangement = exam.getString("examArrangement");
                if (!arrangement.equals("")) {
                    place = exam.getString("place");
                    String[] s = arrangement.split("\\u003cbr/\\u003e");
                    begin_end = s[2].split("-");
                }
                Exam e = new Exam();
                e.setDate(date);
                e.setStart_time(begin_end[0]);
                e.setEnd_time(begin_end[1]);
                e.setLocation(place);
                e.setSubject(name);
                exams.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exams;
    }






    public static int getWeek(String week){
        switch (week){
            case "星期一":
                return 1;

            case "星期二":
                return 2;

            case "星期三":
                return 3;

            case "星期四":
                return 4;

            case "星期五":
                return 5;

            case "星期六":
                return 6;

            case "星期日":
                return 7;

            default:
                return 1;

        }
    }
}
