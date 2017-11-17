package com.example.alex.allthewage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Alex on 11/1/2017.
 */

public class paychecks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paychecks);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        //Creates a spinner and loads it for the page

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paycheck_choices, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
       // Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        final Button button = (Button) findViewById(R.id.pay_employees);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(
                        getApplicationContext(),
                        "You Clicked : " + button,
                        Toast.LENGTH_SHORT
                ).show();
                //super.onCreate(savedInstanceState);
                setContentView(R.layout.paychecks);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                // Initialize Link
                HashMap<String, String> linkInitializeOptions = new HashMap<String,String>();
                linkInitializeOptions.put("key", "0a8665a68e9bc1e02edf51aea606fe");
                linkInitializeOptions.put("product", "auth");
                linkInitializeOptions.put("apiVersion", "v2"); // set this to "v1" if using the legacy Plaid API
                linkInitializeOptions.put("env", "App");
                linkInitializeOptions.put("clientName", "Test App");
                linkInitializeOptions.put("selectAccount", "true");
                linkInitializeOptions.put("webhook", "http://requestb.in");
                linkInitializeOptions.put("baseUrl", "https://cdn.plaid.com/link/v2/stable/link.html");
                // If initializing Link in PATCH / update mode, also provide the public_token
                // linkInitializeOptions.put("public_token", "PUBLIC_TOKEN")

                // Generate the Link initialization URL based off of the configuration options.
                final Uri linkInitializationUrl = generateLinkInitializationUrl(linkInitializeOptions);

                // Modify Webview settings - all of these settings may not be applicable
                // or necesscary for your integration.
                final WebView plaidLinkWebview = (WebView) findViewById(R.id.paychecksMenu);
                WebSettings webSettings = plaidLinkWebview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                WebView.setWebContentsDebuggingEnabled(true);

                // Initialize Link by loading the Link initiaization URL in the Webview
                plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

                // Override the Webview's handler for redirects
                // Link communicates success and failure (analogous to the web's onSuccess and onExit
                // callbacks) via redirects.
                plaidLinkWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // Parse the URL to determine if it's a special Plaid Link redirect or a request
                        // for a standard URL (typically a forgotten password or account not setup link).
                        // Handle Plaid Link redirects and open traditional pages directly in the  user's
                        // preferred browser.
                        Uri parsedUri = Uri.parse(url);
                        if (parsedUri.getScheme().equals("plaidlink")) {
                            String action = parsedUri.getHost();
                            HashMap<String, String> linkData = parseLinkUriData(parsedUri);

                            if (action.equals("connected")) {
                                // User successfully linked
                                Log.d("Public token: ", linkData.get("public_token"));
                                Log.d("Account ID: ", linkData.get("account_id"));
                                Log.d("Account name: ", linkData.get("account_name"));
                                Log.d("Institution type: ", linkData.get("institution_type"));
                                Log.d("Institution name: ", linkData.get("institution_name"));

                                // Reload Link in the Webview
                                // You will likely want to transition the view at this point.
                                plaidLinkWebview.loadUrl(linkInitializationUrl.toString());
                            } else if (action.equals("exit")) {
                                // User exited
                                // linkData may contain information about the user's status in the Link flow,
                                // the institution selected, information about any error encountered,
                                // and relevant API request IDs.
                                Log.d("User status in flow: ", linkData.get("status"));
                                // The request ID keys may or may not exist depending on when the user exited
                                // the Link flow.
                                Log.d("Link request ID: ", linkData.get("link_request_id"));
                                Log.d("API request ID: ", linkData.get("plaid_api_request_id"));

                                // Reload Link in the Webview
                                // You will likely want to transition the view at this point.
                                plaidLinkWebview.loadUrl(linkInitializationUrl.toString());
                            } else {
                                Log.d("Link action detected: ", action);
                            }
                            // Override URL loading
                            return true;
                        } else if (parsedUri.getScheme().equals("https") ||
                                parsedUri.getScheme().equals("http")) {
                            // Open in browser - this is most  typically for 'account locked' or
                            // 'forgotten password' redirects
                            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            // Override URL loading
                            return true;
                        } else {
                            // Unknown case - do not override URL loading
                            return false;
                        }
                    }
                });
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        parent.getItemAtPosition(pos);
        if (pos == 1) {
            ListPopupWindow look = new ListPopupWindow(getApplicationContext());
            look.setContentWidth(10);
            look.setHeight(20);
            look.show();
            Toast.makeText(
                    getApplicationContext(),
                    "You Clicked : " + parent.getItemAtPosition(pos),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.homeMenu:
                Intent empIntent = new Intent(paychecks.this, employer_home.class);
                startActivity(empIntent);
                return true;
            case R.id.employeesMenu:
                Intent paycheckIntent = new Intent(paychecks.this, employee_info.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.sethoursMenu:
                Intent setHoursIntent = new Intent (paychecks.this, set_hours.class);
                startActivity(setHoursIntent);
                return true;
            case R.id.setpayrateMenu:
                Intent setPayIntent = new Intent (paychecks.this, set_pay_rate.class);
                startActivity(setPayIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent (paychecks.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent (paychecks.this, help.class);
                startActivity(helpIntent);
                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(paychecks.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }
        return true;
    }


    protected void on(Bundle savedInstanceState) {

    }

    // Generate a Link initialization URL based on a set of configuration options
    public Uri generateLinkInitializationUrl(HashMap<String,String>linkOptions) {
        Uri.Builder builder = Uri.parse(linkOptions.get("baseUrl"))
                .buildUpon()
                .appendQueryParameter("isWebview", "true")
                .appendQueryParameter("isMobile", "true");
        for (String key : linkOptions.keySet()) {
            if (!key.equals("baseUrl")) {
                builder.appendQueryParameter(key, linkOptions.get(key));
            }
        }
        return builder.build();
    }

    // Parse a Link redirect URL querystring into a HashMap for easy manipulation and access
    public HashMap<String,String> parseLinkUriData(Uri linkUri) {
        HashMap<String,String> linkData = new HashMap<String,String>();
        for(String key : linkUri.getQueryParameterNames()) {
            linkData.put(key, linkUri.getQueryParameter(key));
        }
        return linkData;
    }
}




