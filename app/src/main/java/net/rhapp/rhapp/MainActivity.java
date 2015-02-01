package net.rhapp.rhapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
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


        HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy()).build();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        CasClient c = new CasClient(client,"https://netid.rice.edu/cas/");
        try {
            c.login("https%3A%2F%2Fowlspace-ccm.rice.edu%2Fsakai-login-tool%2Fcontainer","","");
        } catch (CasAuthenticationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CasProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
        Intent RHAListIntent = new Intent(this, RHAList.class);
        startActivity(RHAListIntent);
    }
}
