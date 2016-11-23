package com.reddit.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.reddit.R;
import com.reddit.jsonmodels.Children;
import com.reddit.jsonmodels.ChildrenData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by surajmuralidharagupta on 11/20/16.
 */
public class FeedAdapter extends BaseAdapter {
    private ArrayList<Children> redditFeeds;
    private LayoutInflater inflater = null;
    private Context mContext;

    public FeedAdapter(Context context, ArrayList<Children> feedSource) {
        super();
        this.redditFeeds = feedSource;
        this.mContext = context;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (redditFeeds == null) return 0;
        return redditFeeds.size();
    }

    @Override
    public ChildrenData getItem(int position) {
        return redditFeeds.get(position).getData();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        TitleViewHolder holder;
        ChildrenData redditFeed = getItem(position);
        if (convertView == null) {
            vi = inflater.inflate(R.layout.feeds_list_item, null);
            holder = new TitleViewHolder();
            holder.title = (TextView) vi.findViewById(R.id.title);
            holder.title = (TextView) vi.findViewById(R.id.title);
            holder.title.setTypeface(getTypeFaceForTitle());
            holder.authoredBy = (TextView) vi.findViewById(R.id.author);
            holder.authoredBy.setTypeface(getTypeFaceForComments());
            holder.imageView = (ImageView) vi.findViewById(R.id.feed_ikon);
            holder.comments = (TextView) vi.findViewById(R.id.total_comments);
            holder.comments.setTypeface(getTypeFaceForComments());
            vi.setTag(holder);
        } else {
            holder = (TitleViewHolder) vi.getTag();
        }
        holder.title.setText(redditFeed.getTitle());
        holder.authoredBy.setText("Authored by " + redditFeed.getAuthor());
        holder.comments.setText(redditFeed.getNum_comments() + " Comments");
        UrlImageViewHelper.setUrlDrawable(holder.imageView, redditFeed.getThumbnail());
        return vi;
    }

    public static class TitleViewHolder {
        public TextView title, authoredBy, comments;
        public ImageView imageView;
    }

    public void refreshAdapter(ArrayList<Children> feedSource) {
        this.redditFeeds = feedSource;
        notifyDataSetChanged();
    }

    public Typeface getTypeFaceForTitle() {
        Typeface regularTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                "font/RobotoCondensed-Regular.ttf");
        return regularTypeFace;
    }

    public Typeface getTypeFaceForComments() {
        Typeface regularTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                "font/Roboto-Light.ttf");
        return regularTypeFace;
    }

}
