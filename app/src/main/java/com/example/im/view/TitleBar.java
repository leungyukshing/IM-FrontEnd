package com.example.im.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im.R;

public class TitleBar extends RelativeLayout {
    private Context context;

    // Components
    private TextView textView;
    private Button left_btn;
    private Button right_btn;

    // TitleBar Attrs
    private String titleText;
    private float titleTextSize;
    private int titleTextColor;
    private Drawable titleBackground;

    private String leftText;
    private float leftTextSize;
    private int leftTextColor;
    private Drawable leftBackground;

    private String rightText;
    private float rightTextSize;
    private int rightTextColor;
    private Drawable rightBackground;

    // TitleBar OnClick Listener
    private TitleBarOnClickListener listener;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        findAttrs(attrs);
        initView();
        setTitleBarLayoutParams();
        setButtonClickListener();
    }

    public interface TitleBarOnClickListener {
        void leftButtonClick();
        void rightButtonClick();
    }

    // TitleBar's attrs (XML)
    private void findAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        titleText = typedArray.getString(R.styleable.TitleBar_titleText);
        titleTextSize = typedArray.getFloat(R.styleable.TitleBar_titleTextSize, 24);
        titleTextColor = typedArray.getColor(R.styleable.TitleBar_leftTextColor, 0);
        titleBackground = typedArray.getDrawable(R.styleable.TitleBar_titleBackground);

        leftText = typedArray.getString(R.styleable.TitleBar_leftText);
        leftTextSize = typedArray.getFloat(R.styleable.TitleBar_leftTextSize, 24);
        leftTextColor = typedArray.getColor(R.styleable.TitleBar_leftTextColor, 0);
        leftBackground = typedArray.getDrawable(R.styleable.TitleBar_leftBackground);

        rightText = typedArray.getString(R.styleable.TitleBar_rightText);
        rightTextSize = typedArray.getFloat(R.styleable.TitleBar_rightTextSize, 24);
        rightTextColor = typedArray.getColor(R.styleable.TitleBar_rightTextColor, 0);
        rightBackground = typedArray.getDrawable(R.styleable.TitleBar_rightBackground);

        typedArray.recycle();
    }

    private void initView() {
        textView = new TextView(context);
        left_btn = new Button(context);
        right_btn = new Button(context);

        textView.setText(titleText);
        textView.setTextSize(titleTextSize);
        textView.setTextColor(titleTextColor);
        textView.setBackground(titleBackground);
        textView.setGravity(Gravity.CENTER);

        left_btn.setText(leftText);
        left_btn.setTextSize(leftTextSize);
        left_btn.setTextColor(leftTextColor);
        left_btn.setBackground(leftBackground);

        right_btn.setText(rightText);
        right_btn.setTextSize(rightTextSize);
        right_btn.setTextColor(rightTextColor);
        right_btn.setBackground(rightBackground);

        setBackgroundColor(0xFF01AAFF);
    }

    // Set TitleBar Layout
    private void setTitleBarLayoutParams() {
        left_btn.setAllCaps(false);
        right_btn.setAllCaps(false);

        LayoutParams titleLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(textView, titleLayoutParams);

        LayoutParams leftBtnLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftBtnLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, TRUE);
        addView(left_btn, leftBtnLayoutParams);

        LayoutParams rightBtnLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightBtnLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, TRUE);
        addView(right_btn, rightBtnLayoutParams);
    }

    // add btn listeners
    private void setButtonClickListener() {
        left_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.leftButtonClick();
            }
        });

        right_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.rightButtonClick();
            }
        });
    }

    // Public Method to set title
    public void SetTitleText(String titleText) {
        textView.setText(titleText);
    }

    // Public Method to set listener
    public void SetTitleBarClickListener(TitleBarOnClickListener listener) {
        this.listener = listener;
    }
}
