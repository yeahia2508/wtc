package com.dhakadigital.tdd.wtc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.model.SheetInfo;

import java.util.ArrayList;

/**
 * Created by y34h1a on 2/5/17.
 */

public class SheetInfoAdapter extends RecyclerView.Adapter<SheetInfoAdapter.AddSheetInfoVH> {


    private ArrayList<SheetInfo> sheetInfos = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    public String activityName;

    public SheetInfoAdapter(ArrayList<SheetInfo> sheetInfos) {
        this.sheetInfos = sheetInfos;
    }


    @Override
    public AddSheetInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_sheet_info, parent, false);

        return new AddSheetInfoVH(itemView);
    }


    @Override
    public void onBindViewHolder(AddSheetInfoVH holder, int position) {

        holder.tvSheetName.setText(sheetInfos.get(position).getName());
        holder.tvOrgName.setText(sheetInfos.get(position).getOrg_name());
        holder.tvOrgAddress.setText(sheetInfos.get(position).getOrg_address());
        holder.tvHourRate.setText("$" + sheetInfos.get(position).getHourRate());
    }

    public void add(int position, SheetInfo sheetInfo) {
        sheetInfos.add(position, sheetInfo);
        notifyItemInserted(position);
    }

    public void delete(int position) {
        sheetInfos.remove(position);
        notifyItemRemoved(position);
    }

    public void reAddOrgInfo(ArrayList<SheetInfo> addSheetInfo) {
        sheetInfos = addSheetInfo;
        notifyItemRangeChanged(0, addSheetInfo.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sheetInfos.size();
    }

    public class AddSheetInfoVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvOrgName, tvOrgAddress, tvHourRate;
        TextView tvSheetName;
        ImageView ivDelete, ivEdit;

        public AddSheetInfoVH(View itemView) {
            super(itemView);
            tvSheetName = (TextView) itemView.findViewById(R.id.tvNameSheet);
            tvOrgName = (TextView) itemView.findViewById(R.id.tvName);
            tvOrgAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvHourRate = (TextView) itemView.findViewById(R.id.tvHourRate);


            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);

            itemView.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == ivEdit.getId()) {
                if (onItemClickListener != null) {
                    onItemClickListener.onEditIconClick(v, getAdapterPosition());

                }
            }

            if (v.getId() == ivDelete.getId()) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDeleteIconClick(v, getAdapterPosition());

                }
            }

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onDeleteIconClick(View view, int position);

        void onEditIconClick(View view, int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

