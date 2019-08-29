package com.example.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.im.utils.Image;
import com.example.im.utils.ImageManager;
import com.example.im.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class DressUp extends AppCompatActivity {
    private TitleBar titleBar;
    private RecyclerView avatar_recyclerView;
    private RecyclerView background_recyclerView;
    private Button saveBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dress_up);

        initView();
        setClickListener();
    }

    private void initView() {
        titleBar = findViewById(R.id.dressup_titleBar);
        avatar_recyclerView = findViewById(R.id.avatar_recyclerView);
        background_recyclerView = findViewById(R.id.background_recyclerView);
        saveBtn = findViewById(R.id.save);

        addAvatarRecyclerView();
        addBackgroundRecyclerView();
    }

    private void setClickListener() {
        titleBar.SetTitleBarClickListener(new TitleBar.TitleBarOnClickListener() {
            @Override
            public void leftButtonClick() {
                // finish this page and back to the last page
                finish();
            }

            @Override
            public void rightButtonClick() { } // no operation
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show Toast and back to the last page
                Toast.makeText(DressUp.this, "Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void addAvatarRecyclerView() {
        List<Image> images = new ArrayList<>();
        for (int imageAvatarTmp : ImageManager.imagesAvatar) {
            Image img = new Image();
            img.setImageID(imageAvatarTmp);
            images.add(img);
        }

        // Add Adapter
        
    }

    private void addBackgroundRecyclerView() {

    }
}
