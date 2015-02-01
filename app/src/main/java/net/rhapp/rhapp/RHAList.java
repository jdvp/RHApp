package net.rhapp.rhapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RHAList extends ActionBarActivity {

    private String collegeSelection;
    private ListView rhaList;
    private List<Map<String, String>> practiceList;

    private ArrayList<String> names, colleges, rooms, netids;
    private ArrayList<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhalist);

        // set stuff
        Spinner collegeSelectSpinner = (Spinner) findViewById(R.id.collegeSelection);
        rhaList = (ListView) findViewById(R.id.listOfRHAs);
        collegeSelection = "All Colleges";

        // set listeners
        collegeSelectSpinner.setOnItemSelectedListener(new collegeSelectListener());
        rhaList.setOnItemClickListener(new rhaListListener());

//        // trying out lists
        practiceList = new ArrayList<>();
        initList();
//        simpleAdpt = new SimpleAdapter(this, filteredPracticeList(),
//                android.R.layout.simple_list_item_2, new String[] {"name", "college"},
//                new int[] {android.R.id.text1, android.R.id.text2});
//        rhaList.setAdapter(simpleAdpt);


        // ignore this crap
//        CustomList adapter = new CustomList(RHAList.this, web, web2, imageId);

        CustomList adapter = new CustomList(RHAList.this, collegeSelection, names, colleges, rooms, images);
        rhaList.setAdapter(adapter);

    }

    public RHAList(){}

    public RHAList(String huh){
        practiceList = new ArrayList<>();
        netids = new ArrayList<String>();
        initList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rhalist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.to_inbox) {
            goToInbox();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class collegeSelectListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            collegeSelection = (String) parent.getItemAtPosition(position);

            // temporary: displays a message
            Context context = getApplicationContext();
            CharSequence text = "Viewing all from " + collegeSelection;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


//            simpleAdpt = new SimpleAdapter(getBaseContext(), practiceList,
//                    android.R.layout.simple_list_item_1, new String[] {collegeSelection},
//                    new int[] {android.R.id.text1});
//            simpleAdpt = new SimpleAdapter(getBaseContext(), filteredPracticeList(),
//                    android.R.layout.simple_list_item_2, new String[] {"name", "college"},
//                    new int[] {android.R.id.text1, android.R.id.text2});
//            rhaList.setAdapter(simpleAdpt);

            ArrayList<Integer> indices = extractIndices(colleges, collegeSelection);
            CustomList adapter = new CustomList(RHAList.this, collegeSelection,
                    filterStr(names, indices), filterStr(colleges, indices),
                    filterStr(rooms, indices), filterImg(images, indices));
            rhaList.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            collegeSelection = "All Colleges";
        }
    }

    private class rhaListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // temporary: displays a message
//            Context context = getApplicationContext();
//            CharSequence text = "Send message to " + (parent.getItemAtPosition(position));
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();

            goToSendMessageToRHA((String) parent.getItemAtPosition(position));
        }
    }

    public void goToSendMessageToAll (View view) {

        // don't go if All Colleges is selected
        if (collegeSelection.equals("All Colleges")) {
            Context context = getApplicationContext();
            CharSequence text = "Can't send to all; Please select a college";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Intent msgToAllIntent = new Intent(this, SendMessage.class);
            msgToAllIntent.putExtra("college", collegeSelection);
            startActivity(msgToAllIntent);
        }
    }

    public void goToSendMessageToRHA (String rhaName) {
        Intent msgToAllIntent = new Intent(this, SendMessage.class);
        msgToAllIntent.putExtra("rha", rhaName);
        startActivity(msgToAllIntent);
    }

    public void goToInbox () {
        Intent inboxIntent = new Intent(this, Inbox.class);
        startActivity(inboxIntent);
    }

    public void goToInbox (View view) {
        Intent inboxIntent = new Intent(this, Inbox.class);
        startActivity(inboxIntent);
    }

    // create RHAs
    private HashMap<String, String> dummyID (String college, String name, String room, String netID, int img) {
        HashMap<String, String> thing = new HashMap<>();
        thing.put("college", college);
        thing.put("name", name);

        names.add(name);
        colleges.add(college);
        rooms.add(room);
        netids.add(netID);
        images.add(img);

        return thing;
    }

    // init list of RHAs
    private void initList() {
        names = new ArrayList<String>();
        colleges = new ArrayList<String>();
        rooms = new ArrayList<String>();
        images = new ArrayList<>();
        netids = new ArrayList<String>();

        practiceList.add(dummyID("Wiess", "Jessica Dawson", "420", "",R.drawable.empty_rha_img));
        practiceList.add(dummyID("Wiess", "GaYoung Park", "321", "Debug",R.drawable.empty_rha_img));
        practiceList.add(dummyID("Duncan", "Karan Thakker", "234", "krt2",R.drawable.empty_rha_img));
    }

    private ArrayList<Integer> extractIndices (ArrayList<String> colleges, String collegeSelection) {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < colleges.size(); i++) {
            if (collegeSelection.equals("All Colleges") ||
                    colleges.get(i).equals(collegeSelection))
                indices.add(i);
        }
        return indices;
    }
    private ArrayList<String> filterStr(ArrayList<String> origList, ArrayList<Integer> indices) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (Integer i : indices) {
            filteredList.add(origList.get(i));
        }
        return filteredList;
    }
    private ArrayList<Integer> filterImg(ArrayList<Integer> origList, ArrayList<Integer> indices) {
        ArrayList<Integer> filteredList = new ArrayList<>();
        for (Integer i : indices) {
            filteredList.add(origList.get(i));
        }
        return filteredList;
    }

    public boolean contains(String username){
        Log.v("RHALIST CONTAINS FUCNTION", netids.toString());
        return netids.contains(username);
    }

}
