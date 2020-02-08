package com.example.im.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.im.R;
import com.example.im.utils.Image;

import java.util.ArrayList;
import java.util.List;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.BaseViewHolder> {
    private List<Image> imageViews;
    private Context context;
    private LayoutInflater inflater;
    private static int selectedBackground = 0;
    private List<RelativeLayout> imageContainer = new ArrayList<>();
    private Drawable backgroundImageDrawable;

    public BackgroundAdapter(Context context, List<Image> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
        this.inflater = LayoutInflater.from(context);
        selectedBackground = 0;
        backgroundImageDrawable = context.getResources().getDrawable(R.drawable.bgimage2);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.choose_image, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.imageView.setImageResource(imageViews.get(i).getImageID());
        imageContainer.get(selectedBackground).setBackground(backgroundImageDrawable);

        baseViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = baseViewHolder.getAdapterPosition();
                if (pos != selectedBackground) {
                    imageContainer.get(pos).setBackground(backgroundImageDrawable);
                    imageContainer.get(selectedBackground).setBackgroundColor(0);
                    selectedBackground = pos;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        BaseViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            RelativeLayout relativeLayout = view.findViewById(R.id.imageContainer);
            imageContainer.add(relativeLayout);
        }
    }
}
