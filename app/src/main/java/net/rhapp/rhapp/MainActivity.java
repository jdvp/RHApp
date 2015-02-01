package net.rhapp.rhapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.rhapp.casauthentication.CasAuthenticationException;
import net.rhapp.casauthentication.CasClient;
import net.rhapp.casauthentication.CasProtocolException;

import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.impl.client.DefaultRedirectStrategy;
import ch.boye.httpclientandroidlib.impl.client.HttpClientBuilder;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add a font and use it to set the title
        Typeface blockFonts = Typeface.createFromAsset(getAssets(),"fonts/BreeSerif-Regular.ttf");
        TextView txtSampleTxt = (TextView) findViewById(R.id.titleText);
        txtSampleTxt.setTypeface(blockFonts);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void goToRHAList (View view) {

        HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy()).build();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        CasClient c = new CasClient(client,"https://netid.rice.edu/cas/");
        try {
            EditText username = (EditText) findViewById(R.id.netidField);
            EditText password = (EditText) findViewById(R.id.passwordField);
            if(c.login("https://rhapp.rhapp.net", username.getText().toString(), password.getText().toString()))
            {
                Intent RHAListIntent = new Intent(this, RHAList.class);
                startActivity(RHAListIntent);
            }
            c.logout();
        } catch (CasAuthenticationException e) {
            Context context = getApplicationContext();
            CharSequence text = "Incorrect username/password";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            e.printStackTrace();
        } catch (CasProtocolException e) {
            e.printStackTrace();
        }
    }
}
