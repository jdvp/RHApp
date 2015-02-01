package net.rhapp.rhapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
    private List<Map<String, String>> practiceList;
    SimpleAdapter simpleAdpt;
    ListView rhaList;

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

        // trying out lists
        practiceList = new ArrayList<>();
        initList();
        simpleAdpt = new SimpleAdapter(this, filteredPracticeList(),
                android.R.layout.simple_list_item_2, new String[] {"name", "college"},
                new int[] {android.R.id.text1, android.R.id.text2});
        rhaList.setAdapter(simpleAdpt);

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
        }

        return super.onOptionsItemSelected(item);
    }

    private class collegeSelectListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            collegeSelection = (String) parent.getItemAtPosition(position);


//            simpleAdpt = new SimpleAdapter(getBaseContext(), practiceList,
//                    android.R.layout.simple_list_item_1, new String[] {collegeSelection},
//                    new int[] {android.R.id.text1});
            simpleAdpt = new SimpleAdapter(getBaseContext(), filteredPracticeList(),
                    android.R.layout.simple_list_item_2, new String[] {"name", "college"},
                    new int[] {android.R.id.text1, android.R.id.text2});
            rhaList.setAdapter(simpleAdpt);

            // temporary: displays a message
            Context context = getApplicationContext();
            CharSequence text = "Viewing all from " + collegeSelection;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
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
            Context context = getApplicationContext();
            CharSequence text = "Send message to " + ((HashMap) parent.getItemAtPosition(position)).get("name");
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //TODO: go to activity that sends a message to a particular RHA
        }
    }

    public void goToSendMessageToAll (View view) {
        Intent msgToAllIntent = new Intent(this, SendMessageToAll.class);
        msgToAllIntent.putExtra("college", collegeSelection);
        startActivity(msgToAllIntent);
    }

    private HashMap<String, String> dummyID (String college, String name, String color) {
        HashMap<String, String> thing = new HashMap<>();
        thing.put("college", college);
        thing.put("name", name);
        thing.put("color", color);
        return thing;
    }


    private void initList() {
        practiceList.add(dummyID("Wiess", "Jess", "white"));
        practiceList.add(dummyID("Wiess", "GaYoung", "pink"));
        practiceList.add(dummyID("Duncan", "Karan", "blue"));
    }

    // filters the list of RHAs by college
    private List<Map<String, String>> filteredPracticeList() {

        List<Map<String, String>> filtered = new ArrayList<>();

        for (Map<String, String> map : practiceList) {

            if (collegeSelection.equals("All Colleges") ||
                    map.get("college").equals(collegeSelection))
                filtered.add(map);
        }

        return filtered;

    }

}
