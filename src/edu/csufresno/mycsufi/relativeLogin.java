package edu.csufresno.mycsufi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class relativeLogin extends Activity {
	
	private static final int DIALOG_GETCLASS_KEY = 0;
	
	private StudentClassSchedule studentClassSchedule = new StudentClassSchedule();
	private DBAdapter dba = new DBAdapter(this);
	private String username = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_login);

		final EditText usertext = (EditText) findViewById(R.id.user);
		final EditText passwordtext = (EditText) findViewById(R.id.password);

		// Grab user entries on Enter
		passwordtext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// Waiting for the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

					// Performed action when "enter" pressed
					fetchSchedule(usertext, passwordtext);
					return true;
				}
				return false;
			}
		});

		// If the login button is pressed instead of using the enter key
		final Button loginbtn = (Button) findViewById(R.id.login);
		loginbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				fetchSchedule(usertext, passwordtext);
			}
		});

		// Checkbox Monitor
		final CheckBox rememberbox = (CheckBox) findViewById(R.id.rememberme);
		rememberbox.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks depending on whether it's now
				// checked
				if (((CheckBox) v).isChecked()) {
					Toast.makeText(relativeLogin.this, "Save password.",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(relativeLogin.this,
							"Forget password.", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	private void fetchSchedule(EditText usertext, EditText passwordtext) {
		username = usertext.getText().toString();
		Toast.makeText(relativeLogin.this,
				"Fetching " + username + "'s Schedule",
				Toast.LENGTH_SHORT).show();
		
		//showDialog(DIALOG_GETCLASS_KEY); 
		studentClassSchedule.loadFromServer(username.toString(),
				passwordtext.getText().toString());
		//dismissDialog(DIALOG_GETCLASS_KEY);

		if (studentClassSchedule.getClasses().size() > 0) {
			Toast.makeText(relativeLogin.this, "Success.", Toast.LENGTH_SHORT)
					.show();
			
			// Drop schedule table if existing
			// dba.dropStudentClassTable(); functionality not yet provided.
			
			// Create table and insert new schedule.
			dba.open();
			dba.insert_ToDatabase(dba, studentClassSchedule.getClasses());
			dba.close();
			Intent intent = new Intent(relativeLogin.this, MyCSUFi.class);
			startActivity(intent);
		} else {
			Toast.makeText(relativeLogin.this, "Fetch Fail.",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
		ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Fetching " + username + "'s Schedule");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        return dialog;
    }
}