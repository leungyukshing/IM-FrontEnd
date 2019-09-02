package com.example.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.im.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuidePage extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<View> viewList;
    private ViewPager viewPager;
    private GuideViewPagerAdapter guideViewPagerAdapter;
    private ImageView imageViews[] = new ImageView[3];
    private int[] indicatorDotIds = {R.id.dot1, R.id.dot2, R.id.dot3};
    private Button btnToMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_page);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView() {
        // load view
        final LayoutInflater inflater = LayoutInflater.from(this);

        viewList = new ArrayList<>();
        viewList.add(inflater.inflate(R.layout.guide_page1, null));
        viewList.add(inflater.inflate(R.layout.guide_page2, null));
        viewList.add(inflater.inflate(R.layout.guide_page3, null));

        // Initialize variables
        // bind Id with ImageView
        for (int i = 0; i < indicatorDotIds.length; i++) {
            imageViews[i] = (ImageView)findViewById(indicatorDotIds[i]);
        }

        guideViewPagerAdapter = new GuideViewPagerAdapter(this, viewList);

        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        viewPager.setAdapter(guideViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        btnToMain = (Button)viewList.get(2).findViewById(R.id.btn_to_main);

        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jump to Login or Register
                Intent intent = new Intent(GuidePage.this, LoginOrRegister.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int pos) {
        for (int i = 0; i < indicatorDotIds.length; i++) {
            if (i != pos) {
                imageViews[i].setImageResource(R.drawable.unselected);
            }
            else {
                imageViews[i].setImageResource(R.drawable.selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
