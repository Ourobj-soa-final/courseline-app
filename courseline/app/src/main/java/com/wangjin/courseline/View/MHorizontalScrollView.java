package com.wangjin.courseline.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.wangjin.courseline.R;

/**
 * Created by wangjin on 16/5/30.
 */
public class MHorizontalScrollView extends HorizontalScrollView {

    int lastX, lastY, curX, curY, dX, dY;

    int maxY;

    int y = 0;

    private LinearLayout course, courseLayout;

    public void init(LinearLayout courseLayout,DisplayMetrics metrics) {
        course = (LinearLayout) findViewById(R.id.course_panels);
        this.courseLayout = courseLayout;
        maxY = (int)(825 * metrics.density);
    }

    public MHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private boolean canScrollDown = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = (int) ev.getRawX();
            lastY = (int) ev.getRawY();
        } else {
            curX = (int) ev.getRawX();
            curY = (int) ev.getRawY();
            dX = curX - lastX;
            dY = curY - lastY;
            lastX = curX;
            lastY = curY;
            if (dY < 0){
                canScrollDown = true;
            }
            if (y <= 0 && canScrollDown) {
                course.scrollBy(0, -dY);
                courseLayout.scrollBy(0, -dY);
                y += dY;
            }else if (y > 0){
                course.scrollBy(0, y);
                courseLayout.scrollBy(0, y);
                y = 0;
                canScrollDown = false;
            }
        }
        return super.onTouchEvent(ev);
    }
}
