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
    //arrays for two view and pojo model
//TODO: please read the note (^_^)
//    -------------------------------------------------------------------
//    - boss i thing we need two different adapter for this.            -
//    - there is different arraylist and different id of all layout     -
//    - components. so i think it will be convinient if we use two      -
//    - different adapter. meanwhile if they require something else     -
//    - later than it will be another pera to do it again. i made       -
//    - another pojo for "SheetSettingsActivity" and database too.      -
//    - Also i made some constructor and database insert & get method.  -
//    - Sorry boss. Happy Coding and bue for today. see you night.      -
//    -                 IN SHA ALLAH                                    -
//    -------------------------------------------------------------------

    private ArrayList<OrgInfo> orgInfos = new ArrayList<>();
    private ArrayList<SheetInfo> sheetInfos = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    public String activityName;

    public OrgInfoAdapter(ArrayList<OrgInfo> orgInfos){
        this.orgInfos = orgInfos;
    }
    public OrgInfoAdapter(ArrayList<OrgInfo> orgInfos, String activityName){
        this.orgInfos = orgInfos;
        this.activityName = activityName;
    }


    /*public OrgInfoAdapter(ArrayList<SheetInfo> sheetInfos, String activityName, String i_think_we_need_better_plan){
        this.sheetInfos = sheetInfos;
    }
    public OrgInfoAdapter(ArrayList<SheetInfo> sheetInfos, String activityName, String b, String c){
        this.sheetInfos = sheetInfos;
        this.activityName = activityName;
    }*/

    @Override
    public AddOrgInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(activityName.equals(Constants.ACTIVITY_ORG_SETTING)){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleview_org_info, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_sheet_info, parent, false);
        }
        return new AddOrgInfoVH(itemView);
    }



    @Override
    public void onBindViewHolder(AddOrgInfoVH holder, int position) {
        //TODO: boss if this is here will it through null pointer when im in sheetSettingsActivity?
        holder.tvOrgName.setText(orgInfos.get(position).getName());
        holder.tvOrgAddress.setText(orgInfos.get(position).getAddress());

        if(activityName.equals(Constants.ACTIVITY_ORG_SETTING)){
            holder.tvOrgName.setText(orgInfos.get(position).getName());
            holder.tvOrgAddress.setText(orgInfos.get(position).getAddress());
        }else{
            holder.tvSheetName.setText(orgInfos.get(position).getName());
        }
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
        TextView tvSheetName, tvOrgDetails;
        ImageView ivDelete, ivEdit;
        public AddOrgInfoVH(View itemView) {
            super(itemView);
            tvOrgName = (TextView) itemView.findViewById(R.id.tvName);
            tvOrgAddress = (TextView) itemView.findViewById(R.id.tvAddress);

            if(activityName.equals(Constants.ACTIVITY_SHEET_SETTING)){
                tvSheetName = (TextView) itemView.findViewById(R.id.tvNameSheet);
            }
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
