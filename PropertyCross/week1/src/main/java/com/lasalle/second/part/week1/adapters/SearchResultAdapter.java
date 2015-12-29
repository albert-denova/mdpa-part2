package com.lasalle.second.part.week1.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.model.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albert.denova on 28/12/15.
 */
public class SearchResultAdapter extends BaseAdapter {

    private static LayoutInflater layoutInflater = null;
    private Activity containerActivity;
    private List<Property> propertiesList;

    public SearchResultAdapter(Activity containerActivity, List<Property> propertiesList) {
        layoutInflater = (LayoutInflater) containerActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.propertiesList = propertiesList;
        this.containerActivity = containerActivity;
    }

    @Override
    public int getCount() {
        return propertiesList.size();
    }

    @Override
    public Object getItem(final int position) {
        return null;
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.search_result_item, null);

        Property property = propertiesList.get(position);

        setViewImage(rowView);
        setViewTitle(rowView, property);
        setViewSubtitle(rowView, property);
        setViewPrice(rowView, property);

        return rowView;
    }

    protected void setViewImage(View rowView) {
        ImageView imageView = (ImageView) rowView.findViewById(R.id.search_result_image);
        if(Math.random() % 2 == 0) {
            imageView.setImageResource(R.drawable.flat_sample_image);
        }
        else {
            imageView.setImageResource(R.drawable.flat_sample_image_2);
        }
    }

    protected void setViewTitle(View rowView, Property property) {
        TextView titleTextView = (TextView) rowView.findViewById(R.id.search_result_title);
        titleTextView.setText(property.getName());
    }

    protected void setViewSubtitle(View rowView, Property property) {
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.search_result_subtitle);

        List<Object> objectList = new ArrayList<>();
        objectList.add(property.getSquareFootage());

        subtitleTextView.setText(
                String.format(containerActivity.getString(R.string.results_subtitle_text_sale),
                        objectList.toArray()));
    }

    protected void setViewPrice(View rowView, Property property) {
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.search_result_price);

        List<Object> objectList = new ArrayList<>();
        objectList.add(property.getPrice());

        subtitleTextView.setText(
                String.format(containerActivity.getString(R.string.results_price),
                        objectList.toArray()));
    }
}
