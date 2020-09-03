package com.ttg_photo_storage.activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.Demo;
import com.ttg_photo_storage.activity.ViewShipmentDetails;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.login.ViewShip.ShipmentsItem;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder> {
    private Activity mContext;
    private List<ShipmentsItem> list;
    private MvpView mvpView;

    public ShipmentAdapter(Activity context, List<ShipmentsItem> list, MvpView mvp) {
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

            String sourceString = "<b>" + "CRN:  " + "</b> "  +list.get(listPosition).getCrn();
            String timeString = "<b>" + "Time:  " + "</b> "  +list.get(listPosition).getShipTimeFormatted();
            String dateString = "<b>" + "Date:  " + "</b> "  +list.get(listPosition).getShipDateFormatted();
            String condition ="<b>"+"Packaging Quality :"+list.get(listPosition).getBoxCondition()+"</b>";
            String status1 = "<b>"+"Shipment Status : Accepted Shipment"+"</b>";
            String status2 = "<b>"+"Shipment Status : Rejected Shipment"+"</b>";
            if (list.get(listPosition).getBoxCondition().equalsIgnoreCase("Good")){
                holder.tv_quality.setTextColor(mContext.getResources().getColor(R.color.success));
            }else if (list.get(listPosition).getBoxCondition().equalsIgnoreCase("Poor")){
                holder.tv_quality.setTextColor(mContext.getResources().getColor(R.color.red));
            }else if (list.get(listPosition).getBoxCondition().equalsIgnoreCase("Fair")){
                holder.tv_quality.setTextColor(mContext.getResources().getColor(R.color.yellow));
            }
            if (list.get(listPosition).getIsReject().equalsIgnoreCase("no")){
                holder.status.setText(Html.fromHtml(status1));
                holder.status.setTextColor(mContext.getResources().getColor(R.color.success));
            }else if (list.get(listPosition).getIsReject().equalsIgnoreCase("yes")) {
                holder.status.setText(Html.fromHtml(status2));
                holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            holder.crn_no.setText(Html.fromHtml(sourceString));
            holder.tv_quality.setText(Html.fromHtml(condition));
            holder.time.setText(Html.fromHtml(timeString));
            holder.date.setText(Html.fromHtml(dateString));
            holder.view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ViewShipmentDetails.class);
                    intent.putExtra("hash_id", list.get(listPosition).getHash());
                    intent.putExtra("crn", list.get(listPosition).getCrn());
                    PreferencesManager.getInstance(mContext).setPositionCrn(list.get(listPosition).getCrn());
                    PreferencesManager.getInstance(mContext).setPositionHash(list.get(listPosition).getHash());
                    mContext.startActivity(intent);
                }
            });

            holder.edit_images.setVisibility(View.INVISIBLE);

//            holder.btn_editImages.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, Demo.class);
//                    intent.putExtra("hash_id_ship", list.get(listPosition).getHash());
//                    PreferencesManager.getInstance(mContext).setPositionCrn(list.get(listPosition).getCrn());
//                    PreferencesManager.getInstance(mContext).setPositionHash(list.get(listPosition).getHash());
//                    mContext.startActivity(intent);
//
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updatelist(List<ShipmentsItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.crn_no)
        TextView crn_no;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.tv_quality)
        TextView tv_quality;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.edit_images)
        TextView edit_images;
        @BindView(R.id.view_details)
        TextView view_details;
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

