package com.android.viewpager.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.viewpager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/8 18:04
 * QQ         : 904869397@qq.com
 */

public class LViewPager<T> extends LinearLayout implements ViewPager.OnPageChangeListener{

    ViewPager mViewPager;
    LinearLayout mPointCircle;
    LinearLayout mPointLine;
    Timer mTimer=new Timer();
    Handler mHandler =new Handler(Looper.getMainLooper());
    Context mContext;
    Point mPoint=Point.CIRCLE;
    List<T> mList=new ArrayList<T>();
    LAdapter<T> mAdapter;

    public LViewPager(Context context) {
        super(context);
        initView(context);
    }

    public LViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        this.mContext=context;
        View view= View.inflate(context,R.layout.view_viewpager,null);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_banner);
        mViewPager.addOnPageChangeListener(this);
        mPointCircle = (LinearLayout) view.findViewById(R.id.ll_point_circle);
        mPointLine= (LinearLayout) view.findViewById(R.id.ll_point_line);
        this.addView(view);

    }

    /**
     * 开启定时切换图片任务
     */
    public void startTimingTasks(){
        if(mTimer==null){
            return;
        }
        if(mList==null||mList.size()==0){
            return;
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int curPosition=mViewPager.getCurrentItem();
                        int nextPosition=curPosition+1;
                        if(curPosition==mList.size()){
                            nextPosition=0;
                        }
                        mViewPager.setCurrentItem(nextPosition);
                    }
                });
            }
        },3000,3000);
    }

    /**
     * 开启定时切换图片任务
     */
    public void startTimingTasks(long delay, long period){
        if(mTimer==null){
            return;
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int curPosition=mViewPager.getCurrentItem();
                        int nextPosition=curPosition+1;
                        if(curPosition==mViewPager.getChildCount()){
                            nextPosition=0;
                        }
                        mViewPager.setCurrentItem(nextPosition);
                    }
                });
            }
        },delay,period);
    }

    /**
     * 结束定时切换任务
     */
    public void endTimeTasks(){
        if(mTimer!=null){
            mTimer.cancel();
        }
    }

    /**
     * 设置点类型
     * @param point
     */
    public void setPoint(Point point){
        mPoint=point;
    }

    /**
     * 根据传入数据初始化指示点
     *
     * @param list
     */
    private void initPoints(List<T> list){
        if(list==null||list.size()==0){
            return;
        }
        if(mPoint== Point.CIRCLE){
            initPointCircle(list);
        }else{
            initPointLine(list);
        }
    }

    private void initPointCircle(List<T> list){
        mPointCircle.setVisibility(View.VISIBLE);
        mPointLine.setVisibility(View.GONE);
        for (int i = 0; i < list.size(); i++) {
            ImageView text=new ImageView(mContext);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 5, 0);
            text.setLayoutParams(params);
            text.setImageResource(i==0?R.drawable.banner_point_select:R.drawable.banner_point_unselect);
            mPointCircle.addView(text);
        }
    }

    private void initPointLine(List<T> list){
        mPointCircle.setVisibility(View.GONE);
        mPointLine.setVisibility(View.VISIBLE);
        for (int i = 0; i < list.size(); i++) {
            ImageView text=new ImageView(mContext);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight=1;
            params.setMargins(0, 0, (i!=list.size()-1)?5:0, 0);
            text.setLayoutParams(params);
            text.setBackgroundResource(i==0?R.color.colorAccent:R.color.colorPrimary);
            mPointLine.addView(text);
        }

    }

    /**
     * 设置point位置
     * 对Circle有效
     * @param gravity
     */
    public void setPointGravity(int gravity){
        mPointCircle.setGravity(gravity);
    }

    /**
     * 设置适配器
     * @param adapter
     */
    public void setAdapter(PagerAdapter adapter){
        if(adapter!=null&&adapter instanceof LAdapter){
            mAdapter= (LAdapter<T>) adapter;
            mList=mAdapter.getList();
            initPoints(mAdapter.getPointList());
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(1, false);
        }else{
            throw new IllegalArgumentException("Please extends LAdapter!!");
        }
    }

    /**
     * 设置当前页
     * @param item
     */
    public void setCurrentItem(int item){
        mViewPager.setCurrentItem(item);
    }

    /**
     * 设置当前页
     * @param item
     * @param smoothScroll
     */
    public void setCurrentItem(int item, boolean smoothScroll){
        mViewPager.setCurrentItem(item, smoothScroll);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        drawPoint(position);
        changePage(position,positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 将超出边界的位置置换到正常位置
     * 0.0表示当前页面没有滑动偏移量
     * 1.0表示当前页面已滑动完成
     */
    private void changePage(int position,int positionOffsetPixels){
        if (positionOffsetPixels == 0.0) {
            if (position == mList.size() - 1) {
                mViewPager.setCurrentItem(1, false);
            } else if (position == 0) {
                mViewPager.setCurrentItem(mList.size() - 2, false);
            } else {
                mViewPager.setCurrentItem(position);
            }
        }
    }

    /**
     * 绘制当前点
     * @param position
     */
    private void drawPoint(int position){
        if(mAdapter==null){
           return;
        }
        position=mAdapter.getPointPosition(position);
        if(mPoint== Point.CIRCLE){
            for (int i = 0; i < mPointCircle.getChildCount(); i++) {
                ImageView text=(ImageView) mPointCircle.getChildAt(i);
                text.setImageResource(
                        i==position?R.drawable.banner_point_select:R.drawable.banner_point_unselect);
            }
        }else{
            for (int i = 0; i < mPointLine.getChildCount(); i++) {
                ImageView text=(ImageView) mPointLine.getChildAt(i);
                text.setBackgroundResource(
                        i==position?R.color.colorAccent:R.color.colorPrimary);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        endTimeTasks();
    }
}
