package android.wipro.com.telstra.demoapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.wipro.com.telstra.demoapp.R;
import android.wipro.com.telstra.demoapp.model.NewsFeedItem;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by priyag on 13-May-16.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private ArrayList<NewsFeedItem> mDataSet = new ArrayList<NewsFeedItem>();
    private Context mContext;

    public NewsFeedAdapter(Context context) {
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTitle;
        protected TextView mDesc;
        protected ImageView mImage;

        public ViewHolder(View view) {
            super(view);
            mTitle =  (TextView) view.findViewById(R.id.row_title);
            mDesc = (TextView)  view.findViewById(R.id.row_desc);
            mImage = (ImageView)  view.findViewById(R.id.row_image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_row_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsFeedItem newsFeed = (NewsFeedItem)mDataSet.get(position);
        if(newsFeed == null){
            return;
        }
        ((ViewHolder)holder).mTitle.setText(newsFeed.title);
        ((ViewHolder) holder).mDesc.setText(newsFeed.description);

        if(null != newsFeed.imageHref && newsFeed.imageHref.length() > 0){
            Picasso.with(mContext)
                    .load(newsFeed.imageHref)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.error)
                    .into(((ViewHolder) holder).mImage);
        }
        else{
            ((ViewHolder)holder).mImage.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.default_image));
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        resetHolder(holder);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        if(mDataSet != null)
            return mDataSet.size();
        else
            return 0;
    }

    public void setDataSet(ArrayList<NewsFeedItem> feeds){
        mDataSet = feeds;
    }

    private void resetHolder(RecyclerView.ViewHolder holder){
        ((ViewHolder)holder).mImage.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.default_image));
    }
}
