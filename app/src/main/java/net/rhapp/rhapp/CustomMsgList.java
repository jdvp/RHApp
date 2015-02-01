package net.rhapp.rhapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMsgList extends ArrayAdapter<String> {

    private ArrayList<String> titles;
    private ArrayList<String> subtext;
    private ArrayList<Boolean> resolved;
    private final Activity context;


    public CustomMsgList(Activity context,
                      ArrayList<String> txt1, ArrayList<String> txt2, ArrayList<Boolean> flag) {
        super(context, R.layout.rha_list_item, txt1);
        this.context = context;
        titles = txt1;
        subtext = txt2;
        this.resolved = flag;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.msg_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.sender_name);
        TextView txtSub = (TextView) rowView.findViewById(R.id.msg_stub);
        ImageView isRes = (ImageView) rowView.findViewById(R.id.unresolved);

        txtTitle.setText(titles.get(position));
        txtSub.setText(subtext.get(position));
        if (resolved.get(position))
            isRes.setVisibility(View.INVISIBLE);
        else
            isRes.setVisibility(View.VISIBLE);
        return rowView;
    }

}
