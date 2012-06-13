package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kiki
 * Date: 13/06/12
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class CustomAdapter extends BaseAdapter {

    private List<Item> mItems;
    private Context mContext;

    public CustomAdapter(Context context, List<Item> items) {
        mItems = items;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mItems != null)
            return mItems.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mItems != null)
            return mItems.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list, null);
        }

        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        title.setText(mItems.get(i).getHeadline());
        description.setText(mItems.get(i).getDescription());

        return rowView;
    }
}
