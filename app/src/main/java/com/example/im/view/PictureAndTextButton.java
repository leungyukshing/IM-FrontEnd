package com.example.im.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im.R;

public class PictureAndTextButton extends LinearLayout {
    private Context context;

    private TextView textView;
    private ImageView imageView;
    private PictureAndTextButtonOnClickListener listener;

    // PictureAndTextButton attrs
    // ImageView
    private Drawable image;
    private Drawable imageBackground;

    // TextView
    private String text;
    private float textSize;
    private int textColor;
    private Drawable textBackground;

    public PictureAndTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        findAttrs(attrs);
        initView();
        setPictureAndTextButtonLayoutParams();
        setPictureAndTextButtonClickListener();
    }

    private void findAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PictureAndTextButton);

        image = typedArray.getDrawable(R.styleable.PictureAndTextButton_picture);
        imageBackground = typedArray.getDrawable(R.styleable.PictureAndTextButton_pictureBackground);

        text = typedArray.getString(R.styleable.PictureAndTextButton_text);
        textSize = typedArray.getDimension(R.styleable.PictureAndTextButton_textSize, 24);
        textColor = typedArray.getColor(R.styleable.PictureAndTextButton_textColor, 0);
        textBackground = typedArray.getDrawable(R.styleable.PictureAndTextButton_textBackground);

        typedArray.recycle();
    }

    private void initView() {
        imageView = new ImageView(context);
        textView = new TextView(context);

        imageView.setImageDrawable(image);
        imageView.setBackground(imageBackground);

        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setBackground(textBackground);
        textView.setGravity(Gravity.CENTER);
    }

    private void setPictureAndTextButtonLayoutParams() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(20, 0,0, 0);

        LayoutParams imageViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(imageView, imageViewLayoutParams);

        LayoutParams textViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(textView, textViewLayoutParams);
    }

    public interface PictureAndTextButtonOnClickListener {
        void onClick(View view);
    }

    public void SetOnClickListener(PictureAndTextButtonOnClickListener listener) {
        this.listener = listener;
    }

    public void setImageView(int resourceID) {
        imageView.setImageResource(resourceID);
    }

    private void setPictureAndTextButtonClickListener() {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
            }
        });
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
            }
        });
    }
}