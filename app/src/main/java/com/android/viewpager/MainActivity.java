package com.android.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.viewpager.banner.FixPoint;
import com.android.viewpager.banner.FixViewPager;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    FixViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] arrayList=getResources().getStringArray(R.array.banner);
        mViewPager= (FixViewPager) findViewById(R.id.bvp);
        mViewPager.setPoint(FixPoint.LINE);
        mViewPager.setAdapter(new MainAdapter(Arrays.asList(arrayList)));
        mViewPager.startTimingTasks();

    }
}
