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
import com.parse.SaveCallback;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    TextView user1, user2, user3, user4, user5, user6, user7, user8, user9;
    TextView score1, score2, score3, score4, score5, score6, score7, score8, score9;

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

        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);
        score4 = (TextView) findViewById(R.id.score4);
        score5 = (TextView) findViewById(R.id.score5);
        score6 = (TextView) findViewById(R.id.score6);
        score7 = (TextView) findViewById(R.id.score7);
        score8 = (TextView) findViewById(R.id.score8);
        score9 = (TextView) findViewById(R.id.score9);

    }

    private void initHighscoreList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("scores");
        query.addDescendingOrder("score");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + userList.size() + " users");
                    user1.setText(userList.get(0).getString("username"));
                    user2.setText(userList.get(1).getString("username"));
                    user3.setText(userList.get(2).getString("username"));
                    user4.setText(userList.get(3).getString("username"));
                    user5.setText(userList.get(4).getString("username"));
                    user6.setText(userList.get(5).getString("username"));
                    user7.setText(userList.get(6).getString("username"));
                    user8.setText(userList.get(7).getString("username"));
                    user9.setText(userList.get(8).getString("username"));

                    score1.setText(userList.get(0).get("score").toString());
                    score2.setText(userList.get(1).get("score").toString());
                    score3.setText(userList.get(2).get("score").toString());
                    score4.setText(userList.get(3).get("score").toString());
                    score5.setText(userList.get(4).get("score").toString());
                    score6.setText(userList.get(5).get("score").toString());
                    score7.setText(userList.get(6).get("score").toString());
                    score8.setText(userList.get(7).get("score").toString());
                    score9.setText(userList.get(8).get("score").toString());

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

    public void backToMainMenu(){
        Intent intent = new Intent(this, MainMenue_Activity.class);
        intent.putExtra("email", (String) getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }
}
