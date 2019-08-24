package com.example.im.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.jar.Attributes;

public class TitleBar extends RelativeLayout {
    private Context context;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        findAttrs(attrs);
        initView();
        setTitleBarLayoutParams();
        setButtononClickListner();
    }

    private void findAttrs(AttributeSet attrs) {

    }

    private void initView() {

    }

    private void setTitleBarLayoutParams() {

    }

    private void setButtononClickListner() {

    }

    private void setTitleText(String titleText) {

    }
}
