package com.tang.eye.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tang.eye.C;
import com.tang.eye.MainActivity;
import com.tang.eye.R;
import com.tang.eye.VideoDescActivity;
import com.tang.eye.model.Daily;

import java.util.List;

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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_collection, parent, false);
            viewHolder = new ItemCollectionViewHolder(view);
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
        if (holder instanceof ItemCollectionViewHolder) {
            RecyclerView mRvHorizontalCard=((ItemCollectionViewHolder) holder).mRvHorizontalCard;
            List<Daily.ItemListBean.DataBean.ItemListBeanX> itemList =daily.getItemList().get(position).getData().getItemList();
            RecyclerView.LayoutManager layout=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            mRvHorizontalCard.setLayoutManager(layout);
            HorizontalCardAdapter adapter=new HorizontalCardAdapter(context,itemList,0);
            mRvHorizontalCard.setAdapter(adapter);
            TextView mTvTitle=((ItemCollectionViewHolder) holder).mTvTitle;
            mTvTitle.setText(daily.getItemList().get(position).getData().getHeader().getTitle());

        } else {
            if (holder instanceof ScrollCardViewHolder) {
                RecyclerView mRvHorizontalCard = ((ScrollCardViewHolder) holder).mRvHorizontalCard;
                List<Daily.ItemListBean.DataBean.ItemListBeanX> itemList = daily.getItemList().get(position).getData().getItemList();
                RecyclerView.LayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                mRvHorizontalCard.setLayoutManager(layout);

                HorizontalCardAdapter adapter = new HorizontalCardAdapter(context, itemList, 1);
                mRvHorizontalCard.setAdapter(adapter);
                Glide.with(context).load(daily.getItemList().get(position).getData().getHeader().getCover()).into(((ScrollCardViewHolder) holder).mIvCover);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int type, Object itemListBean) {
                        Intent intent = new Intent();
                        intent.setClass(context, VideoDescActivity.class);
                        intent.putExtra("ItemList", (Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean);
                        ((MainActivity)context).startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) context, view, "shareAnim").toBundle());
                    }
                });
            } else if (holder instanceof VideoViewHolder) {
                Log.i("TAG", position + "---");
                final VideoViewHolder viewHolder = (VideoViewHolder) holder;
                Glide.with(context).load(daily.getItemList().get(position).getData().getCover().getFeed()).crossFade().into(viewHolder.mIvVideo);
                viewHolder.mTvTitle.setText(daily.getItemList().get(position).getData().getTitle());
                viewHolder.mTvCategory.setText("#" + daily.getItemList().get(position).getData().getCategory() + " ");
                int duration = daily.getItemList().get(position).getData().getDuration();
                int minute = duration / 60;//Minute second
                int second = duration % 60;
                viewHolder.mTvDuration.setText("/ " + minute + "' " + second + "''");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Bitmap bitmap= ((BitmapDrawable)viewHolder.mIvVideo.getBackground()).getBitmap();
//                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                            @Override
//                            public void onGenerated(Palette palette) {
//                                Palette.Swatch a=palette.getLightVibrantSwatch();
//                                if(a!=null){
//                                   C.bgColor= a.getBodyTextColor();
//                                }
//
//                            }
//                        });
                        onItemClickListener.onItemClick(((VideoViewHolder) holder).itemView, 2, daily.getItemList().get(holder.getAdapterPosition()));
                    }
                });
            }
        }

       // onItemClickListener.onItemClick(position);
    }

    @Override
    public int getItemCount() {
        return daily.getItemList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (daily.getItemList().get(position).getType().equals("videoCollectionOfFollow")) {
            return 0;
        } else if (daily.getItemList().get(position).getType().equals("videoCollectionWithCover")) {
            return 1;
        } else if(daily.getItemList().get(position).getType().equals("video")){
            return 2;
        }else if(daily.getItemList().get(position).getType().equals("textFooter")){
            return 3;
        }else{
            return 4;
        }
    }

    private class ItemCollectionViewHolder extends RecyclerView.ViewHolder {
       TextView mTvTitle;
        private RecyclerView mRvHorizontalCard;
        private ItemCollectionViewHolder(View itemView) {
            super(itemView);
            mRvHorizontalCard = (RecyclerView) itemView.findViewById(R.id.rv_horizontal_card);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
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
        private RecyclerView mRvHorizontalCard;
        private ImageView mIvCover;
        private ScrollCardViewHolder(View itemView) {
            super(itemView);
            mRvHorizontalCard = (RecyclerView) itemView.findViewById(R.id.rv_horizontal_card);
            mIvCover= (ImageView) itemView.findViewById(R.id.iv_cover);
        }
    }
    private class TextViewHolder extends RecyclerView.ViewHolder {

        private TextViewHolder(View itemView) {
            super(itemView);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int type, Object itemListBean);
    }


}
