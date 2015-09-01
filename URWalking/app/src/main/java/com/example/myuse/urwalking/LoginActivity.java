package com.example.myuse.urwalking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    AutoCompleteTextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new Intent(this, MainMenue_Activity.class);
        email = (AutoCompleteTextView) findViewById(R.id.email);
    }

    public void login(View v) {
        String mail = email.getText().toString();
        if(mail != null &&mail.length()>0) {
            Intent intent = new Intent(this, MainMenue_Activity.class);
            intent.putExtra("email",mail);
            this.startActivity(intent);
            Toast.makeText(getApplicationContext(), (CharSequence) "Eingeloggt", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), (CharSequence) "Bitte Email-Adresse eingeben!", Toast.LENGTH_SHORT).show();
        }


    }


}
