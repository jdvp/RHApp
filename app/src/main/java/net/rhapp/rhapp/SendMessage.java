package net.rhapp.rhapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class SendMessage extends ActionBarActivity {

    private String user;
    private boolean isAnon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        user = getIntent().getStringExtra("netid");
        isAnon = false;

        setRecipientDisplay();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_message, menu);
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

    private void setRecipientDisplay() {
        // display recipient
        if (getIntent().hasExtra("college")) {
            String recipients = getIntent().getStringExtra("college");
            TextView recipientDisplay = (TextView)findViewById(R.id.textView);
            if (recipients.equals("All Colleges"))
                recipientDisplay.setText("To: All RHAs");
            else
                recipientDisplay.setText("To: " + recipients + " RHAs");
        }
        else if (getIntent().hasExtra("rha")) {
            String recipient = getIntent().getStringExtra("rha");
            TextView recipientDisplay = (TextView) findViewById(R.id.textView);
            recipientDisplay.setText("To: " + recipient);
        }
    }

    public void sendMessage(View view) {
        String response = "";

        // get message text
        EditText msg   = (EditText)findViewById(R.id.msg);
        String msgText = msg.getText().toString();

        // set params
        String[] params = {user, "abc0", msgText, String.valueOf(isAnon)};

        try {
            response = new POSTSender().execute(params).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!response.equals("OK")) {
            // create dialog (toast is just temporary)
            Context context = getApplicationContext();
            CharSequence text = "Error sending message.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void flipAnon(View view) {
        isAnon = !isAnon;
    }
}
