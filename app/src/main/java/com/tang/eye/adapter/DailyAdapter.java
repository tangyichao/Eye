package com.tang.eye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tang.eye.R;
import com.tang.eye.model.Daily;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tangyc on 2017/5/9.
 */

public class DailyAdapter extends RecyclerView.Adapter {
    private Context context;
    private Daily daily;
    private OnItemClickListener onItemClickListener;



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DailyAdapter(Context context, Daily daily) {
        this.context = context;
        this.daily = daily;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brander, parent, false);
            viewHolder = new BranderViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontalscrollcard, parent, false);
            viewHolder = new ScrollCardViewHolder(view);
        } else if(viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_video, parent, false);
            viewHolder = new VideoViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_video, parent, false);
            viewHolder = new TextViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,int position) {
        int type=0;
        View view=null;
        if (holder instanceof BranderViewHolder) {
            type=0;
            view=((BranderViewHolder) holder).mIvBrander;
            Glide.with(context).load(daily.getItemList().get(position).getData().getCover().getFeed()).into(((BranderViewHolder) holder).mIvBrander);
        } else if (holder instanceof ScrollCardViewHolder) {
            type=1;
            view=((ScrollCardViewHolder) holder).mIvScrollCard;
            Glide.with(context).load(daily.getItemList().get(position).getData().getCover().getFeed()).into(((ScrollCardViewHolder) holder).mIvScrollCard);
        } else if(holder instanceof VideoViewHolder){
            Log.i("TAG",position+"---");
            type=2;
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            view=viewHolder.mIvVideo;
            Glide.with(context).load(daily.getItemList().get(position).getData().getCover().getFeed()).into(viewHolder.mIvVideo);
            viewHolder.mTvTitle.setText(daily.getItemList().get(position).getData().getTitle());
            viewHolder.mTvCategory.setText("#" + daily.getItemList().get(position).getData().getCategory() + " ");
            int duration = daily.getItemList().get(position).getData().getDuration();
            int minute = duration / 60;//Minute second
            int second = duration % 60;
            viewHolder.mTvDuration.setText("/ " + minute + "' " + second + "''");
        }
        final int finalType = type;
        final View finalView = view;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(finalView,finalType,daily.getItemList().get(holder.getAdapterPosition()));
            }
        });
       // onItemClickListener.onItemClick(position);
    }

    @Override
    public int getItemCount() {
        return daily.getItemList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (daily.getItemList().get(position).getType().equals("banner2")) {
            return 0;
        } else if (daily.getItemList().get(position).getType().equals("videoCollectionWithCover2")) {
            return 1;
        } else if(daily.getItemList().get(position).getType().equals("video")){
            return 2;
        }else if(daily.getItemList().get(position).getType().equals("textFooter")){
            return 3;
        }else{
            return 4;
        }
    }

    private class BranderViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvBrander;

        private BranderViewHolder(View itemView) {
            super(itemView);
            mIvBrander = (ImageView) itemView.findViewById(R.id.iv_brander);
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video)
        ImageView mIvVideo;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_duration)
        TextView mTvDuration;

        private VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private class ScrollCardViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvScrollCard;

        private ScrollCardViewHolder(View itemView) {
            super(itemView);
            mIvScrollCard = (ImageView) itemView.findViewById(R.id.iv_scrollcard);
        }
    }
    private class TextViewHolder extends RecyclerView.ViewHolder {

        private TextViewHolder(View itemView) {
            super(itemView);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int type, Daily.ItemListBean itemListBean);
    }


}
