package com.dhakadigital.tdd.wtc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.pojo.OrgInfo;

import java.util.ArrayList;

/**
 * Created by y34h1a on 2/4/17.
 */

public class OrgInfoAdapter extends RecyclerView.Adapter<OrgInfoAdapter.AddOrgInfoVH> {
    private ArrayList<OrgInfo> orgInfos = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public OrgInfoAdapter(ArrayList<OrgInfo> orgInfos){
        this.orgInfos = orgInfos;
    }

    @Override
    public AddOrgInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_org, parent, false);

        return new AddOrgInfoVH(itemView);
    }

    @Override
    public void onBindViewHolder(AddOrgInfoVH holder, int position) {
        holder.tvOrgName.setText(orgInfos.get(position).getName());
        holder.tvOrgAddress.setText(orgInfos.get(position).getAddress());

    }

    public void add (int position, OrgInfo orgInfo){
        orgInfos.add(position, orgInfo);
        notifyItemInserted(position);
    }

    public void delete(int position){
        orgInfos.remove(position);
        notifyItemRemoved(position);
    }

    public void reAddOrgInfo(ArrayList<OrgInfo> addOrgInfo){
        orgInfos = addOrgInfo;
        notifyItemRangeChanged(0, addOrgInfo.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orgInfos.size();
    }

    public class AddOrgInfoVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvOrgName, tvOrgAddress;
        ImageView ivDelete, ivEdit;
        public AddOrgInfoVH(View itemView) {
            super(itemView);
            tvOrgName = (TextView) itemView.findViewById(R.id.tvName);
            tvOrgAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            itemView.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == ivEdit.getId()){
                if(onItemClickListener!=null){
                    onItemClickListener.onEditIconClick(v,getAdapterPosition());

                }
            }

            if(v.getId() == ivDelete.getId()){
                if(onItemClickListener!=null){
                    onItemClickListener.onDeleteIconClick(v,getAdapterPosition());

                }
            }

            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
        void onDeleteIconClick(View view, int position);
        void onEditIconClick(View view, int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
