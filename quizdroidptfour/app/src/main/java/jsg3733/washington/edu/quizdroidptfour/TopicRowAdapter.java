package jsg3733.washington.edu.quizdroidptfour;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jsg3733 on 2/16/15.
 */
public class TopicRowAdapter extends ArrayAdapter<TopicRow> {

    Context context;
    int layoutResourceId;
    List<TopicRow> data;
    //TopicRow data[] = null;

    public TopicRowAdapter(Context context, int layoutResourceId, List<TopicRow> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TopicRowHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TopicRowHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtShortDesc = (TextView) row.findViewById(R.id.txtShortDesc);

            row.setTag(holder);
        }
        else
        {
            holder = (TopicRowHolder)row.getTag();
        }

        //TopicRow listrow = data[position];
        TopicRow listrow = data.get(position);
        holder.txtTitle.setText(listrow.title);
        holder.imgIcon.setImageResource(listrow.icon);
        holder.txtShortDesc.setText(listrow.shortDesc);

        return row;
    }

    static class TopicRowHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtShortDesc;
    }
}