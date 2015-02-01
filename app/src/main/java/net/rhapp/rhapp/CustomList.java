package net.rhapp.rhapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapted from http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */
public class CustomList extends ArrayAdapter<String>{

    private ArrayList<String> titles;
    private ArrayList<String> subtext1, subtext2;
    private ArrayList<Integer> images;

    private final Activity context;

//    public CustomList(Activity context,
//                      String[] txt1, String[] txt2, Integer[] imageId) {
//        super(context, R.layout.rha_list_item, txt1);
//        this.context = context;
//        this.titles = txt1;
//        this.subtext = txt2;
//        this.imageId = imageId;
//    }

    public CustomList(Activity context, String college,
                      ArrayList<String> txt1, ArrayList<String> txt2,
                      ArrayList<String> txt3, ArrayList<Integer> imageId) {
        super(context, R.layout.rha_list_item, txt1);
        this.context = context;

        titles = txt1;
        subtext1 = txt2;
        subtext2 = txt3;
        images = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rha_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.text1);
        TextView txtSub = (TextView) rowView.findViewById(R.id.text2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(titles.get(position));
        txtSub.setText(subtext1.get(position) + ", Room " + subtext2.get(position));
        imageView.setImageResource(images.get(position));
        return rowView;
    }

}