package com.android.viewpager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.viewpager.banner.SnackBarUtils;
import com.android.viewpager.banner.FixAdapter;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/14 14:34
 * QQ         : 904869397@qq.com
 */

public class MainAdapter extends FixAdapter {

    public MainAdapter(List list) {
        super(list);
    }

    @Override
    public View initializationItem(Context context, Object object, final int position) {
        View view= View.inflate(context, R.layout.adapter_item,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_item);
        Glide.with(context).load((String)object).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackBarUtils.showSnackbar(view,position+"");
            }
        });
        return view;
    }

}
