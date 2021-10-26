package com.example.flickermsapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {
    private ArrayList<FlickerPhoto> photoList;
    private Context context;
    private MyPhotoListener listener;



    public PhotosAdapter(Context context, ArrayList<FlickerPhoto> photoList) {
        this.photoList = photoList;
        this.context = context;
    }

    public interface MyPhotoListener{
        void onPhotoClicked(int position ,View view);
    }

    // Clean all elements of the recycler
    public void clear() {
        photoList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<FlickerPhoto> list) {
        photoList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flicker_item,parent,false);
        return new PhotosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        FlickerPhoto flickerPhoto = photoList.get(position);

        Glide.with(context).load(flickerPhoto.getPhotoUrl()).into(holder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setListenter(MyPhotoListener listenter){
        this.listener = listenter;
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhoto;

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.iv_photo_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onPhotoClicked(getAbsoluteAdapterPosition(),view);
                    }
                }
            });
        }

    }
}
