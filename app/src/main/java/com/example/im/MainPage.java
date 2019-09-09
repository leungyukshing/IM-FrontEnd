package com.example.im;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.im.adapter.MainViewPagerAdapter;
import com.example.im.utils.ImageManager;
import com.example.im.view.ChatsLayout;
import com.example.im.view.ContactsLayout;
import com.example.im.view.MomentsLayout;

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
        mainViewPagerAdapter.addItem(new ContactsLayout());
        mainViewPagerAdapter.addItem(new MomentsLayout());

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));

        tabList.get(0).setIcon(R.drawable.msgselected).setText("Chats");
        tabList.get(1).setIcon(R.drawable.contactsunselected).setText("Contacts");
        tabList.get(2).setIcon(R.drawable.momentunselected).setText("Moments");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabList.get(tab.getPosition()).setIcon(ImageManager.tabImageID[tab.getPosition() + 3]);
                tabLayout.setTabTextColors(ContextCompat.getColor(MainPage.this, R.color.colorBlack),
                        ContextCompat.getColor(MainPage.this, R.color.colorBlue));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabList.get(tab.getPosition()).setIcon(ImageManager.tabImageID[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
