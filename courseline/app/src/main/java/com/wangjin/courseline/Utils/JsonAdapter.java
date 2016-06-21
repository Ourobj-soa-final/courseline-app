package com.wangjin.courseline.Utils;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangjin.courseline.R;
import com.wangjin.courseline.model.Exam;

import java.util.List;

/**
 * Created by zhengsuren on 16/6/21.
 */
public class JsonAdapter extends BaseAdapter {

    //考试页面自动导入的适配器
    private List<Exam> list;
    private Context context;
    private LayoutInflater inflater;


    public JsonAdapter(List<Exam> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public JsonAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Exam> data)
    {
        this.list = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.exam_item,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }

        Exam exam = list.get(position);

        holder.subject.setText(exam.getSubject());
        holder.begin.setText(exam.getStart_end());
        holder.end.setText(exam.getLocation());
        holder.date.setText(exam.getDate());

        return convertView;
    }


    class Holder
    {
        private TextView subject,begin,end,date;

        public Holder(View view)
        {
            subject = (TextView) view.findViewById(R.id.subject);
            begin = (TextView) view.findViewById(R.id.exbegin);
            end = (TextView) view.findViewById(R.id.exend);
            date = (TextView) view.findViewById(R.id.exdate);
        }

    }
}
