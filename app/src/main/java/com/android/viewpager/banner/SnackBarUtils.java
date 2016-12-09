package com.android.viewpager.banner;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/8 18:14
 * QQ         : 904869397@qq.com
 */

public class SnackBarUtils {

    public static void showSnackbar(View view,String message){
        final Snackbar snackbar=Snackbar.make(view,message,3000);
        snackbar.setAction("dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
