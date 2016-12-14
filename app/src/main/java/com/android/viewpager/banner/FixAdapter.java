package com.android.viewpager.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description: PagerAdapter 父类,采用继承方式
 *              包装PagerAdapter方案不可行，
 *              instantiateItem 与 destroyItem 无法对应, 与内部包装PagerAdapter冲突
 * Author     : kevin.bai
 * Time       : 2016/12/13 19:23
 * QQ         : 904869397@qq.com
 */

public abstract class FixAdapter<T> extends PagerAdapter {

    SparseArray<View> mViewList;
    int length=0;
    List<T> mList;

    public FixAdapter(List<T> list){
        this.mList=list;
        this.length=mList.size()+2;
        this.mViewList=new SparseArray<>(length);
    }

    @Override
    public int getCount() {
        return length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mViewList.get(position);
        if(view==null){
            if(position==0){
                view=initializationItem(
                        container.getContext(),mList.get(mList.size()-1),mList.size()-1);
            }else if(position==length-1){
                view=initializationItem(
                        container.getContext(),mList.get(0),0);
            }else{
                view=initializationItem(
                        container.getContext(),mList.get(position-1),position-1);
            }
            mViewList.put(position,view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    public abstract View initializationItem(Context context, T object, int position);




}
