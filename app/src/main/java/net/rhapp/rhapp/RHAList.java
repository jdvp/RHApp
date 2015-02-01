package net.rhapp.rhapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class RHAList extends ActionBarActivity {

    private String collegeSelection;
    private Spinner collegeSelectSpinner;
    private ListView rhaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhalist);

        // set stuff
        collegeSelectSpinner = (Spinner) findViewById(R.id.collegeSelection);
        rhaList = (ListView) findViewById(R.id.listOfRHAs);

        // set listeners
        collegeSelectSpinner.setOnItemSelectedListener(new collegeSelectListener());
       rhaList.setOnItemClickListener(new rhaListListener());

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
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    }
//
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
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            collegeSelection = "All";
        }
    }

    private class rhaListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // temporary: displays a message
            Context context = getApplicationContext();
            CharSequence text = "Send message to " + (String) parent.getItemAtPosition(position);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //TODO: go to activity that sends a message to a particular RHA
        }
    }

    public void goToSendMessageToAll (View view) {
        Intent MsgToAllIntent = new Intent(this, SendMessageToAll.class);
        startActivity(MsgToAllIntent);
    }
}
