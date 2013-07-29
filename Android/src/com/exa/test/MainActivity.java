package com.exa.test;

import com.exa.constants.FreeBikeConstants;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.View.OnClickListener;
import android.content.DialogInterface;


public class MainActivity extends Activity {
	
	private Context that = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parse.initialize(this, "s", "s");
		ParseAnalytics.trackAppOpened(getIntent());

		setContentView(R.layout.activity_main);

		Button button1 = (Button)findViewById(R.id.signup);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = ((EditText) findViewById(R.id.username)).getText().toString(); 
				String password = ((EditText) findViewById(R.id.password)).getText().toString();
				if (username== null || username.length() ==  0 || password == null
						|| password.length() == 0)
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(that);
					alertDialogBuilder.setTitle("Missing values");
					alertDialogBuilder
					.setMessage("Missing value for username or password")
					.setCancelable(false)
					.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							MainActivity.this.finish();
						}
					});
					return;
				}
				String emailid = ((EditText) findViewById(R.id.emailid)).getText().toString();
				
				ParseUser user = new ParseUser();
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(emailid);


				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							Log.e(FreeBikeConstants.TAG, "Signup success");
							that.startActivity(new TrackActivity().getIntent());
						} else {
							// Sign up didn't succeed. Look at the ParseException
							// to figure out what went wrong
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(that);
							alertDialogBuilder.setTitle("Signup failed");
							alertDialogBuilder
							.setMessage(e.getMessage())
							.setCancelable(false)
							.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									MainActivity.this.finish();
								}
							});
							return;
						}
					}
				});

			}
}); 


		Button login = (Button)findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = ((EditText) findViewById(R.id.username)).getText().toString(); 
				String password = ((EditText) findViewById(R.id.password)).getText().toString();
				if (username== null || username.length() ==  0 || password == null
						|| password.length() == 0)
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(that);
					alertDialogBuilder.setTitle("Missing values");
					alertDialogBuilder
					.setMessage("Missing value for username or password")
					.setCancelable(false)
					.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							MainActivity.this.finish();
						}
					});
					return;
				}
		
				ParseUser.logInInBackground(username, password, new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
						if (e == null)
						{
							Log.d(FreeBikeConstants.TAG, "Login success");
							//that.startActivity(new TrackActivity().getIntent());
							Intent myIntent = new Intent(that, TrackActivity.class);
					        that.startActivity(myIntent);
							//startActivityForResult(myIntent, 0);
						}
						else
						{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(that);
							alertDialogBuilder.setTitle("Login Failed");
							alertDialogBuilder
							.setMessage(e.getMessage())
							.setCancelable(false)
							.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									MainActivity.this.finish();
								}
							});
							return;
						}
						
					}
				});
		
			}
}); 
}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
}    

}
