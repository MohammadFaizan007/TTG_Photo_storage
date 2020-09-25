package com.ttg_photo_storage.adapter;



import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ttg_photo_storage.BuildConfig;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.FullScreenImageActivity;
import com.ttg_photo_storage.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.login.viewShipDetails.FilesItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class ViewShipImagesAdapter extends RecyclerView.Adapter<ViewShipImagesAdapter.ViewHolder> {
    private Activity mContext;
    private List<FilesItem> list;
    private MvpView mvpView;

    public ViewShipImagesAdapter(Activity context, List<FilesItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getpost_adapter, parent, false);
        return new ViewShipImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.description.setText(list.get(position).getDesc());
//            holder.description.setText(list.get(list.indexOf(list)).getDesc());
            Glide.with(mContext).load(BuildConfig.BASE_URL_FORIMAGE + list.get(position).getFile())
                    .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.photo_view)
                            .error(R.drawable.photo_view))
                    .into(holder.postImage);

            holder.postImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, FullScreenImageActivity.class);
                    intent.putExtra("image_url", list.get(position).getFile());
                    intent.putExtra("comment",list.get(position).getDesc());
                    mContext.startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updatelist(List<FilesItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.postImage)
        ImageView postImage;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}


