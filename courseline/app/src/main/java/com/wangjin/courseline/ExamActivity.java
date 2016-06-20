package com.wangjin.courseline;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhengsuren on 16/6/21.
 */
public class ExamActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity);

        ListView listView = (ListView) findViewById(R.id.mlistview);

        //创建键值对,存储要填入的值
        ArrayList<HashMap<String,String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("subject","测试科目1");
        map.put("begin","10:00");
        map.put("end","12:00");
        map.put("date","12月28日");
        mylist.add(map);

        SimpleAdapter mAdapter = new SimpleAdapter(this,mylist,R.layout.exam_item,
                new String[]{"subject","begin","end","date"},new int[]{R.id.subject,R.id.exbegin,R.id.exend,R.id.exdate});

        listView.setAdapter(mAdapter);
    }
}
