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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description: ViewPager 包装类
 *              包含无限循环滑动、定时滑动、定制指示点
 * Author     : kevin.bai
 * Time       : 2016/12/13 19:36
 * QQ         : 904869397@qq.com
 */

public class FixViewPager extends LinearLayout implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    LinearLayout mCirclePoint;
    LinearLayout mLinePoint;
    Timer mTimer=new Timer();
    Handler mHandler =new Handler(Looper.getMainLooper());
    Context mContext;
    FixPoint mPoint= FixPoint.CIRCLE;

    public FixViewPager(Context context) {
        super(context);
        initView(context);
    }

    public FixViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        this.mContext=context;
        View view= View.inflate(context, R.layout.view_viewpager,null);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_banner);
        mViewPager.addOnPageChangeListener(this);
        mCirclePoint = (LinearLayout) view.findViewById(R.id.ll_point_circle);
        mLinePoint = (LinearLayout) view.findViewById(R.id.ll_point_line);
        addView(view);

    }

    /**
     * 开启定时切换图片任务
     */
    public void startTimingTasks(){
        if(mTimer==null){
            return;
        }
        if(mViewPager==null||mViewPager.getAdapter().getCount()==0){
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
                        if(curPosition==mViewPager.getAdapter().getCount()){
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
        if(mViewPager==null||mViewPager.getAdapter().getCount()==0){
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
                        if(curPosition==mViewPager.getAdapter().getCount()){
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
    public void setPoint(FixPoint point){
        mPoint=point;
    }

    /**
     * 设置point相对位置
     * 对Circle有效
     * @param gravity
     */
    public void setPointGravity(int gravity){
        mCirclePoint.setGravity(gravity);
    }


    /**
     * 隐藏指示点
     * 默认显示圆形点
     * @param active
     */
    public void dismissPoint(boolean active){
        if(active){
            mCirclePoint.setVisibility(View.GONE);
            mLinePoint.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化指示点
     */
    private void initPoints(int size){
        if(size<=0){
            return;
        }
        if(mPoint== FixPoint.CIRCLE){
            initCirclePoint(size);
        }else{
            initLinePoint(size);
        }
    }

    /**
     * 初始化圆形指示点
     * @param size
     */
    private void initCirclePoint(int size){
        mCirclePoint.setVisibility(View.VISIBLE);
        mLinePoint.setVisibility(View.GONE);
        for (int i = 0; i < size; i++) {
            ImageView text=new ImageView(mContext);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 5, 0);
            text.setLayoutParams(params);
            text.setImageResource(i==0?R.drawable.banner_point_select:R.drawable.banner_point_unselect);
            mCirclePoint.addView(text);
        }
    }

    /**
     * 初始化线性指示点
     * @param size
     */
    private void initLinePoint(int size){
        mCirclePoint.setVisibility(View.GONE);
        mLinePoint.setVisibility(View.VISIBLE);
        for (int i = 0; i < size; i++) {
            ImageView text=new ImageView(mContext);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight=1;
            params.setMargins(0, 0, (i!=size-1)?5:0, 0);
            text.setLayoutParams(params);
            text.setBackgroundResource(i==0?R.color.colorAccent:R.color.colorPrimary);
            mLinePoint.addView(text);
        }

    }

    /**
     * 设置当前页
     * @param item
     */
    public void setCurrentItem(int item){
        mViewPager.setCurrentItem(item+1);
    }

    /**
     * 设置当前页
     * @param item
     * @param smoothScroll
     */
    public void setCurrentItem(int item, boolean smoothScroll){
        mViewPager.setCurrentItem(item+1, smoothScroll);
    }


    /**
     * 设置适配器
     * 默认从第一位开始
     * @param adapter
     */
    public void setAdapter(PagerAdapter adapter){
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1, false);
        initPoints(adapter.getCount()-2);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        changePoint(position);
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
            if (position == mViewPager.getAdapter().getCount() - 1) {
                mViewPager.setCurrentItem(1, false);
            } else if (position == 0) {
                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 2, false);
            } else {
                mViewPager.setCurrentItem(position);
            }
        }
    }

    /**
     * 将超出界限的item替换为正确的点
     * @param position
     */
    private void changePoint(int position){
        if (position == mViewPager.getAdapter().getCount() - 1) {
            position=1;
        } else if (position == 0) {
            position= mViewPager.getAdapter().getCount()-2;
        } else {
            position=position-1;
        }
        if(mPoint== FixPoint.CIRCLE){
            for (int i = 0; i < mCirclePoint.getChildCount(); i++) {
                ImageView text=(ImageView) mCirclePoint.getChildAt(i);
                text.setImageResource(
                        i==position?R.drawable.banner_point_select:R.drawable.banner_point_unselect);
            }
        }else{
            for (int i = 0; i < mLinePoint.getChildCount(); i++) {
                ImageView text=(ImageView) mLinePoint.getChildAt(i);
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

