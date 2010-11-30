package edu.csufresno.mycsufi;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.ImageView;
import android.widget.Toast;

public class relativeLogin extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_login);
        //ImageView image = (ImageView) findViewById(R.id.seal);

        //This is used to grab data from the user text field when you press enter in the password field
        final EditText usertext = (EditText) findViewById(R.id.user);
        final EditText passwordtext = (EditText) findViewById(R.id.password);
        passwordtext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Waiting for the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  //Performed action when "enter" pressed
                  Toast.makeText(relativeLogin.this, usertext.getText(), Toast.LENGTH_SHORT).show();
                  Toast.makeText(relativeLogin.this, passwordtext.getText(), Toast.LENGTH_SHORT).show();
                  return true;
                }
                return false;
            }
        });
        
        
        //If the login button is pressed instead of using the enter key
        final Button loginbtn = (Button) findViewById(R.id.login);
        loginbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Perform action on click
                Toast.makeText(relativeLogin.this, "Logging In ... ", Toast.LENGTH_LONG).show();
            }
        });
        
        
        //Checkbox Monitor
        final CheckBox rememberbox = (CheckBox) findViewById(R.id.rememberme);
        rememberbox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Perform action on clicks depending on whether it's now checked
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(relativeLogin.this, "Remember Me Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(relativeLogin.this, "Remember Me Not selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    
    }
    
}