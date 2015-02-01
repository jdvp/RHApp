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
    private final Activity context;

//    private final String[] titles;
//    private final String[] subtext;
//    private final Integer[] imageId;

    private ArrayList<Integer> indices;
    private ArrayList<String> titles;
    private ArrayList<String> subtext;
    private ArrayList<Integer> images;

//    public CustomList(Activity context,
//                      String[] txt1, String[] txt2, Integer[] imageId) {
//        super(context, R.layout.rha_list_item, txt1);
//        this.context = context;
//        this.titles = txt1;
//        this.subtext = txt2;
//        this.imageId = imageId;
//    }

    public CustomList(Activity context, String college,
                      ArrayList<String> txt1, ArrayList<String> txt2, ArrayList<Integer> imageId) {
        super(context, R.layout.rha_list_item, txt1);
        this.context = context;
//        extractIndices(txt2, college);
//        this.titles = filterStr(txt1);
//        this.subtext = filterStr(txt2);
//        this.images = filterImg(imageId);
        titles = txt1;
        subtext = txt2;
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
        txtSub.setText(subtext.get(position));
        imageView.setImageResource(images.get(position));
        return rowView;
    }

    private void extractIndices (ArrayList<String> colleges, String collegeSelection) {
        indices = new ArrayList<>();

        for (int i = 0; i < colleges.size(); i++) {
            if (collegeSelection.equals("All Colleges") ||
                    colleges.get(i).equals(collegeSelection))
                indices.add(i);
        }
    }

    private ArrayList<String> filterStr(ArrayList<String> origList) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (Integer i : indices) {
            filteredList.add(origList.get(i));
        }
        return filteredList;
    }
    private ArrayList<Integer> filterImg(ArrayList<Integer> origList) {
        ArrayList<Integer> filteredList = new ArrayList<>();
        for (Integer i : indices) {
            filteredList.add(origList.get(i));
        }
        return filteredList;
    }
}