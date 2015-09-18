package com.example.myuse.urwalking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    AutoCompleteTextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (AutoCompleteTextView) findViewById(R.id.email_register);
    }

    public void realRegister(View v){
        String mail = email.getText().toString();
        if(mail != null &&mail.length()>0) {
            Log.println(1, "gog", "real");
            EditText password =(EditText)findViewById(R.id.password2);
            EditText password2 =(EditText)findViewById(R.id.password_confirm);
            String pw1 = password.getText().toString();
            String pw2 = password2.getText().toString();
            if(pw1.equals(null))
                Toast.makeText(getApplicationContext(), (CharSequence) "Passwort muss mindestens ein Zeichen enthalten", Toast.LENGTH_SHORT).show();
            else if(pw1.equals(pw2)) {
                Intent intent = new Intent(this, MainMenue_Activity.class);
                intent.putExtra("email", mail);
                this.startActivity(intent);
                Toast.makeText(getApplicationContext(), (CharSequence) "Registriert", Toast.LENGTH_SHORT).show();
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
