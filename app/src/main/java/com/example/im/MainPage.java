package com.example.im;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.example.im.adapter.MainViewPagerAdapter;
import com.example.im.view.ChatsLayout;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<TabLayout.Tab> tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.mainViewPager);
        tabLayout = findViewById(R.id.mainTabLayout);

        tabList = new ArrayList<>();

        // Set ViewPager Adapter
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addItem(new ChatsLayout());
    }
}
