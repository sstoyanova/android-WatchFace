package com.example.android.wearable.watchface;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Simona Stoyanova on 13.10.2016 г..
 * Dev Labs
 * simona@devlabs.bg
 */
class SimpleImageAdapter extends RecyclerView.Adapter<SimpleImageAdapter.ViewHolder> {
    private Activity activity;
    private String directoryPath = "";
    private List<String> imagePaths;

    SimpleImageAdapter(String directoryPath, Activity activity) {
        this.directoryPath = directoryPath;
        this.activity = activity;
        fillImagePaths();
    }

    void setImagePaths(String directoryPath) {
        this.directoryPath = directoryPath;
        fillImagePaths();
        notifyDataSetChanged();
    }


    private void fillImagePaths() {
        imagePaths = new ArrayList<>();
        File dir = new File(directoryPath);
        File[] foundFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });
        if (foundFiles != null && foundFiles.length > 0) {
            int max = foundFiles.length;
            if (max > 10) {
                max = 10;
            }
            for (int i = 0; i < max; i++) {
                File file = foundFiles[i];
                String name = file.getName();
                imagePaths.add(name);
            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        final String imagePath = directoryPath + imagePaths.get(i);
        File file = new File(imagePath);
//        Uri uri = Uri.fromFile(file);
        Glide.with(activity)
                .load(file)
                .placeholder(R.drawable.placeholder)
//                .listener(new )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'card_order.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
