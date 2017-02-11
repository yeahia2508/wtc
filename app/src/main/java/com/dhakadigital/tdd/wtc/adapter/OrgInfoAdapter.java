package com.dhakadigital.tdd.wtc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.constants.Constants;
import com.dhakadigital.tdd.wtc.model.SheetInfo;
import com.dhakadigital.tdd.wtc.model.OrgInfo;

import java.util.ArrayList;

/**
 * Created by y34h1a on 2/4/17.
 */

public class OrgInfoAdapter extends RecyclerView.Adapter<OrgInfoAdapter.AddOrgInfoVH> {

    private ArrayList<OrgInfo> orgInfos = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    public String activityName;
    public int selectedCard = -1;

    public OrgInfoAdapter(ArrayList<OrgInfo> orgInfos) {
        this.orgInfos = orgInfos;
    }

    @Override
    public AddOrgInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_org_info, parent, false);
        return new AddOrgInfoVH(itemView);
    }
//http://stackoverflow.com/questions/39270623/android-recycler-view-select-only-one-image-and-show-tick-mark-on-selected-image
    @Override
    public void onBindViewHolder(AddOrgInfoVH holder, int position) {
        holder.tvOrgName.setText(orgInfos.get(position).getName());
        holder.tvOrgAddress.setText(orgInfos.get(position).getAddress());
        holder.tvOrgName.setText(orgInfos.get(position).getName());
        holder.tvOrgAddress.setText(orgInfos.get(position).getAddress());
        holder.ivOrgSelectStatus.setBackgroundResource(R.drawable.edit);//set an image in card when clicked
    }

    public void add(int position, OrgInfo orgInfo) {
        orgInfos.add(position, orgInfo);
        notifyItemInserted(position);
    }

    public void delete(int position) {
        orgInfos.remove(position);
        notifyItemRemoved(position);
    }

    public void reAddOrgInfo(ArrayList<OrgInfo> addOrgInfo) {
        orgInfos = addOrgInfo;
        notifyItemRangeChanged(0, addOrgInfo.size());
        notifyDataSetChanged();
    }

    public void selectedOrg(int position){

    }
    @Override
    public int getItemCount() {
        return orgInfos.size();
    }

    public class AddOrgInfoVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvOrgName, tvOrgAddress;
        ImageView ivDelete, ivEdit,ivOrgSelectStatus,ivSelectOrg;

        public AddOrgInfoVH(View itemView) {
            super(itemView);
            tvOrgName = (TextView) itemView.findViewById(R.id.tvName);
            tvOrgAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            ivSelectOrg = (ImageView) itemView.findViewById(R.id.ivSelect);
            ivOrgSelectStatus = (ImageView) itemView.findViewById(R.id.ivSelectStatus);

            itemView.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
            ivSelectOrg.setOnClickListener(this);
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

            if (v.getId() == ivSelectOrg.getId()) {
                if (onItemClickListener != null) {
                    onItemClickListener.onOrgSelectClick(v, getAdapterPosition());
                    selectedCard = getLayoutPosition();
                    notifyDataSetChanged();
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

        void onOrgSelectClick(View view, int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
