package com.example.myuse.urwalking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Profile_Activity extends Activity {
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        getUser();
        Button button = (Button)findViewById(R.id.backwards);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFromProfile();
            }
        });
        Button button2 = (Button)findViewById(R.id.changeMailButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMail();
            }
        });
    }

    public void backFromProfile(){
        Intent intent = new Intent(this, MainMenue_Activity.class);
        intent.putExtra("email", (String) getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }

    private void changeMail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hier die Email-Adresse eingeben");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                ParseUser user = ParseUser.getCurrentUser();
                user.setEmail(m_Text);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Ihre Mailadresse ist nun: " + m_Text, Toast.LENGTH_SHORT).show();
                            TextView mailAdress = (TextView)findViewById(R.id.profileEmail);
                            mailAdress.setText(m_Text);
                        } else {
                            Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void getUser(){
        String user = ParseUser.getCurrentUser().getUsername();
        String mail = ParseUser.getCurrentUser().getEmail();
        String id = ParseUser.getCurrentUser().getObjectId();
        TextView mailAdress = (TextView)findViewById(R.id.profileEmail);
        mailAdress.setText(mail);
        TextView userName = (TextView)findViewById(R.id.username);
        userName.setText(user);

        getScore(id);
    }

    private void getScore(String id){
        int score;
        score = ParseUser.getCurrentUser().getInt("score");
        TextView scoreView = (TextView)findViewById(R.id.scoreUser);
        scoreView.setText(score+"");
    }
}
