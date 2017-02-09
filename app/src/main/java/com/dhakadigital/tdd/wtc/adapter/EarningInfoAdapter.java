package com.dhakadigital.tdd.wtc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.model.EarningInfo;

import java.util.ArrayList;

/**
 * Created by sk on 2/5/17.
 */

public class EarningInfoAdapter extends RecyclerView.Adapter<EarningInfoAdapter.AddEarningInfoVH> {
    private ArrayList<EarningInfo> EarningInfos = new ArrayList<>();
    private EarningInfoAdapter.OnItemClickListener onItemClickListener;

    public EarningInfoAdapter(ArrayList<EarningInfo> EarningInfos){
        this.EarningInfos = EarningInfos;
    }

    @Override
    public EarningInfoAdapter.AddEarningInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_user_earning, parent, false);

        return new EarningInfoAdapter.AddEarningInfoVH(itemView);
    }

    @Override
    public void onBindViewHolder(EarningInfoAdapter.AddEarningInfoVH holder, int position) {
        holder.tv_ern_duration.setText(String.valueOf(EarningInfos.get(position).getDuration()+" hr"));
        holder.tv_ern_wages.setText(EarningInfos.get(position).getWages());
        holder.tv_ern_date.setText(EarningInfos.get(position).getDate_in_millis());
        holder.tv_ern_start_end.setText(EarningInfos.get(position).getStart_time_millis());
    }

    public void add (int position, EarningInfo EarningInfo){
        EarningInfos.add(position, EarningInfo);
        notifyItemInserted(position);
    }

    public void delete(int position){
        EarningInfos.remove(position);
        notifyItemRemoved(position);
    }

    public void reAddEarningInfo(ArrayList<EarningInfo> addEarningInfo){
        EarningInfos = addEarningInfo;
        notifyItemRangeChanged(0, addEarningInfo.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return EarningInfos.size();
    }

    public class AddEarningInfoVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_ern_date, tv_ern_start_end, tv_ern_duration, tv_ern_wages;
        public AddEarningInfoVH(View itemView) {
            super(itemView);
            tv_ern_date = (TextView) itemView.findViewById(R.id.tv_ern_date);
            tv_ern_start_end = (TextView) itemView.findViewById(R.id.tv_ern_start_end_time);
            tv_ern_duration = (TextView) itemView.findViewById(R.id.tv_ern_duration);
            tv_ern_wages = (TextView) itemView.findViewById(R.id.tv_ern_wages);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            if(v.getId() == ivEdit.getId()){
//                if(onItemClickListener!=null){
//                    onItemClickListener.onEditIconClick(v,getAdapterPosition());
//
//                }
//            }


            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
//        void onDeleteIconClick(View view, int position);
//        void onEditIconClick(View view, int position);

    }

    public void SetOnItemClickListener(final EarningInfoAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
