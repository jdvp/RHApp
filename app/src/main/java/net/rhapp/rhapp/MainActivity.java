package net.rhapp.rhapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.rhapp.casauthentication.CasAuthenticationException;
import net.rhapp.casauthentication.CasClient;
import net.rhapp.casauthentication.CasProtocolException;

import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.impl.client.DefaultRedirectStrategy;
import ch.boye.httpclientandroidlib.impl.client.HttpClientBuilder;


public class MainActivity extends ActionBarActivity {

    private static final String LOGIN_KEY = "USER_LOGGED_IN";
    private static final String USER_KEY = "USERNAME";
    private static final String RHA_KEY="USER_IS_A_RHA";
    private boolean DEBUG = true;
    private boolean logIn = false;
    private String username = "";
    private boolean isRha = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add a font and use it to set the title
        Typeface blockFonts = Typeface.createFromAsset(getAssets(),"fonts/BreeSerif-Regular.ttf");
        TextView txtSampleTxt = (TextView) findViewById(R.id.titleText);
        txtSampleTxt.setTypeface(blockFonts);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            boolean login = savedInstanceState.getBoolean(LOGIN_KEY);
            if(login){
                username = savedInstanceState.getString(USER_KEY);
                isRha = savedInstanceState.getBoolean(RHA_KEY);
                LoggedIn();
            }

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

        if (DEBUG) {
            logIn = true;
            username = "Debug";
            LoggedIn();
            if(isRha)
                loginAsRHA(view);
            else
                nextPage();
        } else {

            HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy()).build();


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            CasClient c = new CasClient(client, "https://netid.rice.edu/cas/");
            try {
                EditText uname = (EditText) findViewById(R.id.netidField);
                EditText password = (EditText) findViewById(R.id.passwordField);
                if (c.login("https://rhapp.rhapp.net", uname.getText().toString(), password.getText().toString())) {
                    logIn = true;
                    username = uname.getText().toString();
                    LoggedIn();
                    if(isRha)
                        loginAsRHA(view);
                    else
                        nextPage();

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

    public void LoggedIn(){
        RHAList rhas = new RHAList("this is ghetto");
        if(rhas.contains(username))
            isRha=true;
        LinearLayout rl = (LinearLayout) findViewById(R.id.mainScreenLayout);
        rl.removeView(findViewById(R.id.netidField));
        rl.removeView(findViewById(R.id.passwordField));
        rl.removeView(findViewById(R.id.buttonBar));


        LinearLayout.LayoutParams lpv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView welcome = new TextView(this);
        welcome.setText("Welcome, " + username + "!");
        welcome.setLayoutParams(lpv);
        welcome.setTextSize(24);
        rl.addView(welcome);

        LinearLayout buttonConsole = new LinearLayout(this);
        buttonConsole.setOrientation(LinearLayout.HORIZONTAL);

        Button logout = new Button(this);
        logout.setText("Logout");
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logOut();
            }
        });

        final Button nextPage =  new Button(this);
        nextPage.setText("Next Page");

        nextPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isRha)
                    loginAsRHA(v);
                else
                    nextPage();
            }
        });


        buttonConsole.addView(logout, lpv);
        buttonConsole.addView(nextPage, lpv);

        rl.addView(buttonConsole);

        welcome.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    public void logOut(){
        logIn = false;
        username = "";

        setContentView(R.layout.activity_main);
    }

    public void nextPage(){
        Intent RHAListIntent = new Intent(this, RHAList.class);
        RHAListIntent.putExtra("netid", username);
        startActivity(RHAListIntent);
    }

    public void loginAsRHA(View view){

        Intent Inbox = new Intent(this, Inbox.class);
        Inbox.putExtra("netid", username);
        startActivity(Inbox);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("LOGGEDIN", logIn);
        savedInstanceState.putString("USERNAME",username);
        super.onSaveInstanceState(savedInstanceState);
    }
}
