package com.tang.eye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tang.eye.R;
import com.tang.eye.model.Daily;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by tangyc on 2017/6/5.
 */

public class HorizontalCardAdapter extends RecyclerView.Adapter {
   private  Context context;
    private List<Daily.ItemListBean.DataBean.ItemListBeanX> itemList;
    private int type;
    private DailyAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(DailyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HorizontalCardAdapter(Context context, List<Daily.ItemListBean.DataBean.ItemListBeanX> itemList, int type) {
        this.context=context;
        this.itemList=itemList;
        this.type=type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType==0)
                {
                    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
                    return new HeaderViewHolder(view);
                }else{
                    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_card, parent, false);
                    return new HorizontalCardCardViewHolder(view);
                }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  HorizontalCardCardViewHolder)
        {

            Glide.with(context).load(itemList.get(position-1).getData().getCover().getFeed()).into(      ( (HorizontalCardCardViewHolder)holder).mIvVideo);
            ( (HorizontalCardCardViewHolder)holder).mTvTitle.setText(itemList.get(position-1).getData().getTitle());
            ( (HorizontalCardCardViewHolder)holder).mTvCategory.setText("#" + itemList.get(position-1).getData().getCategory() + " ");
            int duration = itemList.get(position-1).getData().getDuration();
            int minute = duration / 60;//Minute second
            int second = duration % 60;
            ( (HorizontalCardCardViewHolder)holder).mTvDuration.setText("/ " + minute + "' " + second + "''");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Daily.ItemListBean.DataBean.ItemListBeanX itemListBeanX= itemList.get(holder.getAdapterPosition());
                    onItemClickListener.onItemClick(holder.itemView,type,itemListBeanX);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size()+1;
    }
    class HorizontalCardCardViewHolder extends RecyclerView.ViewHolder {
         @BindView(R.id.iv_video)
         ImageView mIvVideo;
         @BindView(R.id.tv_title)
         TextView mTvTitle;
         @BindView(R.id.tv_category)
         TextView mTvCategory;
         @BindView(R.id.tv_duration)
         TextView mTvDuration;
         private HorizontalCardCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
