package com.android.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.viewpager.banner.LViewPager;
import com.android.viewpager.banner.Point;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> arrayList=
                Arrays.asList(getResources().getStringArray(R.array.banner));
        mViewPager= (LViewPager) findViewById(R.id.bvp);
        mViewPager.setPoint(Point.LINE);
        mViewPager.setAdapter(new MainAdapter(this,arrayList));
        mViewPager.startTimingTasks();
        mViewPager.setCurrentItem(3);

    }
}
