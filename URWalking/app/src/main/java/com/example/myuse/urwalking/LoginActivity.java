package com.example.myuse.urwalking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "A9qtbvCLt8mcxQVtfPWjawH4oieydqk69dr54cR6", "P4ifu3C6ywLgZaiiH8yMcleqC3EDsN3I0CVBW7gS");
        Button button = (Button)findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        final ProgressDialog pgd = new ProgressDialog(LoginActivity.this);
        pgd.setTitle("Wait");
        pgd.setMessage("wait a moment");
        pgd.show();

        //Get username and password
        TextView email = (TextView)findViewById(R.id.email);
        TextView pw = (TextView)findViewById(R.id.password);
        final String mail = email.getText().toString();
        final String p    = pw.getText().toString();

        if(mail.length()>0) {
            if(p.length()>0) {
                // Call the Parse login method
                ParseUser.logInInBackground(mail, p, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        pgd.dismiss();
                        if(e!=null){
                            Toast.makeText(getApplicationContext(), (CharSequence) "Haben Sie sich schon registriert?", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, MainMenue_Activity.class);
                            intent.putExtra("email", mail);
                            LoginActivity.this.startActivity(intent);
                            Toast.makeText(getApplicationContext(), (CharSequence) "Eingeloggt", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                pgd.dismiss();
                Toast.makeText(getApplicationContext(), (CharSequence) "Bitte Passwort eingeben", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            pgd.dismiss();
            Toast.makeText(getApplicationContext(), (CharSequence) "Bitte Email-Adresse eingeben!", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }


}
