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

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.BaseViewHolder> {
    private List<Image> imageViews;
    private Context context;
    private LayoutInflater inflater;
    private static int selectedImageAvatar = 0;
    private List<RelativeLayout> imageContainer = new ArrayList<>();
    private Drawable backgroundImageDrawable;


    public AvatarAdapter(Context context, List<Image> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
        this.inflater = LayoutInflater.from(context);
        selectedImageAvatar = 0; // default selected
        backgroundImageDrawable = context.getResources().getDrawable(R.drawable.bgimage); // default background
    }

    @NonNull
    @Override
    public AvatarAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.choose_image, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.imageView.setImageResource(imageViews.get(i).getImageID());
        imageContainer.get(selectedImageAvatar).setBackground(backgroundImageDrawable);

        baseViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change background
                int pos = baseViewHolder.getAdapterPosition();
                if (pos != selectedImageAvatar) {
                    imageContainer.get(pos).setBackground(backgroundImageDrawable);
                    imageContainer.get(selectedImageAvatar).setBackgroundColor(0);
                    selectedImageAvatar = pos;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        // Corresponding to choose_image.xml
        ImageView imageView;

        BaseViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            RelativeLayout relativeLayout = view.findViewById(R.id.imageContainer);
            imageContainer.add(relativeLayout);
        }
    }
}
