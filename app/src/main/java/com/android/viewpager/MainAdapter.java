package com.android.viewpager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.viewpager.banner.LAdapter;
import com.android.viewpager.banner.SnackBarUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/9 16:18
 * QQ         : 904869397@qq.com
 */

public class MainAdapter extends LAdapter {

    public MainAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    protected View handleView(Context context, Object o, final int position) {
        View view= View.inflate(context, R.layout.adapter_item,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_item);
        Glide.with(context).load((String) o).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackBarUtils.showSnackbar(view,position+"");
            }
        });
        return view;
    }

}
