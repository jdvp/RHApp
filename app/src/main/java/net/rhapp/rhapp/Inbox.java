package net.rhapp.rhapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class Inbox extends ActionBarActivity {

    ListView msgList;
    ArrayList<String> names;
    ArrayList<String> msgs;
    ArrayList<Boolean> bools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        // set list
        msgList = (ListView) findViewById(R.id.msg_list);

        // set listener
        msgList.setOnItemClickListener(new msgListListener());

        initList();


        // Activity context, ArrayList<String> txt1, ArrayList<String> txt2, ArrayList<Boolean> flag
        CustomMsgList adapter = new CustomMsgList(Inbox.this, names, msgs, bools);
        msgList.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
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

    private class msgListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // temporary: displays a message
            Context context = getApplicationContext();
            CharSequence text = "Send message to " + (parent.getItemAtPosition(position));
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private void initList() {

    }
}
