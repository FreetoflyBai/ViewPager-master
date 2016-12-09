package com.android.viewpager.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/9 16:02
 * QQ         : 904869397@qq.com
 */

public abstract class LAdapter<T> extends PagerAdapter {
    private List<T> mList=new ArrayList<>();
    private SparseArray<View> mViewList;
    private Context mContext;
    private List<T> mPointList=new ArrayList<>();

    public LAdapter(Context context, List<T> list){
        setList(list);
        this.mViewList=new SparseArray<>(mList.size());
        this.mContext=context;
    }

    private void setList(List<T> list){
        if(list==null||list.size()==0){
            throw new IllegalArgumentException("invalid list!!");
        }
        mPointList=list;
        mList.add(list.get(list.size()-1));
        mList.addAll(list);
        mList.add(list.get(0));
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mViewList.get(position);
        if(view==null){
            int truePos=getPointPosition(position);
            view= handleView(mContext,mList.get(position),truePos);
            mViewList.put(position,view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }


    protected abstract View handleView(Context context,T t,int position);

    /**
     * 获取包含边界List
     * @return
     */
    public List<T> getList(){
        return mList;
    }

    /**
     * 获取真实List
     * @return
     */
    public List<T> getPointList(){
        return mPointList;
    }

    /**
     * 获取真实位置
     * @param position
     * @return
     */
    public int getPointPosition(int position){
        if(position==mList.size()-1){
            position=0;
        }else if(position==0){
            position=mList.size() - 2;
        }else{
            position=position-1;
        }
        return position;
    }




}
