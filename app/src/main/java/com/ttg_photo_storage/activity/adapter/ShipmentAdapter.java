package com.ttg_photo_storage.activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.PostResultSecondActivity;
import com.ttg_photo_storage.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.login.crn.UidsItem;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder> {
    private Activity mContext;
    private List<UidsItem> list;
    private MvpView mvpView;

    public ShipmentAdapter(Activity context, List<UidsItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ship_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        try {

            String sourceString = "<b>" + "CRN:  " + "</b> "  +list.get(listPosition).getUid();
            String timeString = "<b>" + "Time:  " + "</b> "  +list.get(listPosition).getUid();
            String dateString = "<b>" + "Date:  " + "</b> "  +list.get(listPosition).getUid();
            String qualityString = "<b>" + "Packaging quality:  " + "</b> "  +list.get(listPosition).getUid();
            holder.uid_no.setText(Html.fromHtml(sourceString));
            holder.date.setText(Html.fromHtml(dateString));
            holder.time.setText(Html.fromHtml(timeString));
            holder.quality.setText(Html.fromHtml(qualityString));

            holder.asset_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PostResultSecondActivity.class);
                    intent.putExtra("uid_id", list.get(listPosition).getUid());
                    mContext.startActivity(intent);
//                    mContext.finish();
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


    public void updatelist(List<UidsItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.uid_no)
        TextView uid_no;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.quality)
        TextView quality;
        @BindView(R.id.asset_ll)
        LinearLayout asset_ll;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            btnTeanandbusiness.setOnClickListener(v -> {
//                Log.e("MEMBER ID", "= " + list.get(getAdapterPosition()).getMemberId());
//                mvpView.getMyClickPosition(list.get(getAdapterPosition()).getFirstName() + " " + list.get(getAdapterPosition()).getLastName(), String.valueOf(list.get(getAdapterPosition()).getMemberId()));
//
//            });

        }
    }
}

