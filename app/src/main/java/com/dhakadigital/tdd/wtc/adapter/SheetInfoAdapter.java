package com.dhakadigital.tdd.wtc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.pojo.SheetInfo;
import java.util.ArrayList;

/**
 * Created by y34h1a on 2/5/17.
 */

public class SheetInfoAdapter extends RecyclerView.Adapter<SheetInfoAdapter.AddSheetInfoVH> {
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

    private ArrayList<SheetInfo> sheetInfos = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    public String activityName;

    public SheetInfoAdapter(ArrayList<SheetInfo> orgInfos){
        this.sheetInfos = sheetInfos;
    }


    /*public SheetInfoAdapter(ArrayList<SheetInfo> sheetInfos, String activityName){
        this.sheetInfos = sheetInfos;
        this.activityName = activityName;
    }*/


    @Override
    public AddSheetInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_sheet_earning, parent, false);

        return new AddSheetInfoVH(itemView);
    }


    @Override
    public void onBindViewHolder(AddSheetInfoVH holder, int position) {
        //TODO: boss if this is here will it through null pointer when im in sheetSettingsActivity?

        holder.tvSheetName.setText(sheetInfos.get(position).getName());
        holder.tvOrgName.setText(sheetInfos.get(position).getOrg_name());
        holder.tvOrgAddress.setText(sheetInfos.get(position).getOrg_address());
    }

    public void add (int position, SheetInfo sheetInfo){
        sheetInfos.add(position, sheetInfo);
        notifyItemInserted(position);
    }

    public void delete(int position){
        sheetInfos.remove(position);
        notifyItemRemoved(position);
    }

    public void reAddOrgInfo(ArrayList<SheetInfo> addSheetInfo){
        sheetInfos = addSheetInfo;
        notifyItemRangeChanged(0, addSheetInfo.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sheetInfos.size();
    }

    public class AddSheetInfoVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvOrgName, tvOrgAddress;
        TextView tvSheetName;
        ImageView ivDelete, ivEdit;

        public AddSheetInfoVH(View itemView) {
            super(itemView);
            tvSheetName = (TextView) itemView.findViewById(R.id.tvNameSheet);
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

