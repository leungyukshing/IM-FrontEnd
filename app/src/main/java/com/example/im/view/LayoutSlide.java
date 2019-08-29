package com.example.im.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.im.DressUp;
import com.example.im.Profile;
import com.example.im.R;
import com.example.im.Setting;

import java.util.jar.Attributes;

public class LayoutSlide extends FrameLayout {
    private Context context;

    // components
    private PictureAndTextButton dressUp;
    private PictureAndTextButton profile;
    private PictureAndTextButton setting;
    private PictureAndTextButton night;
    private boolean nightMode = false; // day mode default

    public LayoutSlide(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public LayoutSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        setClickListener();
    }

    private void initView() {
        this.addView(LayoutInflater.from(context).inflate(R.layout.layout_slide, null));

        dressUp = findViewById(R.id.dressup);
        profile = findViewById(R.id.profile);
        setting = findViewById(R.id.setting);
        night = findViewById(R.id.night);

        loadData();
    }

    private void loadData() {
        // TODO: load dressUp from server

        // TODO: load profile from server
    }

    private void setClickListener() {
        dressUp.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                // jump to DressUp Page
                Intent intent = new Intent(context, DressUp.class);
                context.startActivity(intent);
            }
        });

        profile.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                // jump to Profile Page
                Intent intent = new Intent(context, Profile.class);
                context.startActivity(intent);
            }
        });

        setting.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                // jump to Setting Page
                Intent intent = new Intent(context, Setting.class);
                context.startActivity(intent);
            }
        });

        night.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                // change background by changing background
                if (nightMode) {
                    findViewById(R.id.layout_slide).setBackgroundColor(0xff878787);
                    nightMode = false;
                }
                else {
                    findViewById(R.id.layout_slide).setBackgroundColor(0xffe9e9e9);
                    nightMode = true;
                }
            }
        });
    }
}
