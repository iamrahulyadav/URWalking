package com.example.myuse.urwalking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button = (Button)findViewById(R.id.email_register_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realRegister();
            }
        });
    }

    public void realRegister(){
        final ProgressDialog pgd = new ProgressDialog(RegisterActivity.this);
        pgd.setTitle("Wait");
        pgd.setMessage("wait a moment");
        pgd.show();

        //Get username and password
        TextView email = (TextView)findViewById(R.id.emailregister);
        TextView pw = (TextView)findViewById(R.id.passwordregister);
        TextView pw2 = (TextView)findViewById(R.id.passwordregisterconfirm);
        final String mail = email.getText().toString();
        String p = pw.getText().toString();
        String p2 = pw2.getText().toString();

        // Set up a new Parse user
        ParseUser user = new ParseUser();
        user.setUsername(mail);
        user.setPassword(p);
        user.put("score",0);
        if(mail.length()>0) {
            if(!(p.length()>0))
                Toast.makeText(getApplicationContext(), (CharSequence) "Passwort muss mindestens ein Zeichen enthalten", Toast.LENGTH_SHORT).show();
            else if(p.equals(p2)) {
                // Call the Parse signup method
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        pgd.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, MainMenue_Activity.class);
                        intent.putExtra("email", mail);
                        RegisterActivity.this.startActivity(intent);
                        Toast.makeText(getApplicationContext(), (CharSequence) "Registriert", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            else{
                Toast.makeText(getApplicationContext(), (CharSequence) "Bitte zweimal das gleiche Passwort eingeben", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), (CharSequence) "Bitte Email-Adresse eingeben!", Toast.LENGTH_SHORT).show();
        }
    }
}
