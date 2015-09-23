package com.example.myuse.urwalking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    TextView user1, user2, user3, user4, user5, user6, user7, user8, user9, user10;
    TextView score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Button backButton = (Button)findViewById(R.id.backwards);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainMenu();
            }
        });
        initViews();
        initHighscoreList();
    }

    private void initViews(){
        user1 = (TextView) findViewById(R.id.user1);
        user2 = (TextView) findViewById(R.id.user2);
        user3 = (TextView) findViewById(R.id.user3);
        user4 = (TextView) findViewById(R.id.user4);
        user5 = (TextView) findViewById(R.id.user5);
        user6 = (TextView) findViewById(R.id.user6);
        user7 = (TextView) findViewById(R.id.user7);
        user8 = (TextView) findViewById(R.id.user8);
        user9 = (TextView) findViewById(R.id.user9);
        user10 = (TextView) findViewById(R.id.user10);

        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);
        score4 = (TextView) findViewById(R.id.score4);
        score5 = (TextView) findViewById(R.id.score5);
        score6 = (TextView) findViewById(R.id.score6);
        score7 = (TextView) findViewById(R.id.score7);
        score8 = (TextView) findViewById(R.id.score8);
        score9 = (TextView) findViewById(R.id.score9);
        score10 = (TextView) findViewById(R.id.score10);

    }

    private void initHighscoreList(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.addDescendingOrder("score");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + userList.size() + " users");
                    user1.setText(userList.get(0).get("username").toString());
                    user2.setText(userList.get(1).get("username").toString());
                    user3.setText(userList.get(2).get("username").toString());
                    user4.setText(userList.get(3).get("username").toString());
                    user5.setText(userList.get(4).get("username").toString());
                    user6.setText(userList.get(5).get("username").toString());
                    user7.setText(userList.get(6).get("username").toString());
                    user8.setText(userList.get(7).get("username").toString());
                    user9.setText(userList.get(8).get("username").toString());
                    user10.setText(userList.get(9).get("username").toString());

                    score1.setText(userList.get(0).get("score").toString());
                    score2.setText(userList.get(1).get("score").toString());
                    score3.setText(userList.get(2).get("score").toString());
                    score4.setText(userList.get(3).get("score").toString());
                    score5.setText(userList.get(4).get("score").toString());
                    score6.setText(userList.get(5).get("score").toString());
                    score7.setText(userList.get(6).get("score").toString());
                    score8.setText(userList.get(7).get("score").toString());
                    score9.setText(userList.get(8).get("score").toString());
                    score10.setText(userList.get(9).get("score").toString());



                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
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

    public void backToMainMenu(){
        Intent intent = new Intent(this, MainMenue_Activity.class);
        intent.putExtra("email", (String) getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }
}
