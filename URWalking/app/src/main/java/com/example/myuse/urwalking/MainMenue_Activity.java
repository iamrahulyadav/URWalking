package com.example.myuse.urwalking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainMenue_Activity extends AppCompatActivity {


    /*
    *       Hauptmenü -> gibt schon immer die Emailadresse an jede Funktion weiter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menue_);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menue_, menu);
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

    public void perform_end(View v)
    {
        finish();
    }
    public void rate(View v)
    {
       Toast.makeText(getApplicationContext(), (CharSequence) "Bewerten Sie Bilder um Punkte zu erhalten", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Rate_activity.class);
        intent.putExtra("email", (String) getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }
    public void help(View v)
    {
        Intent intent = new Intent(this, Help_Activity.class);
        intent.putExtra("email",(String)getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
        Toast.makeText(getApplicationContext(), (CharSequence) "Erhalten Sie Hilfe", Toast.LENGTH_SHORT).show();
    }
    public void photo(View v)
    {
        Toast.makeText(getApplicationContext(), (CharSequence) "Machen Sie Fotos um Punkte zu erhalten", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Photo_Activity.class);
        intent.putExtra("email", (String) getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }
    public void profile(View v)
    {
        Intent intent = new Intent(this, Profile_Activity.class);
        intent.putExtra("email",(String)getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
        Toast.makeText(getApplicationContext(), (CharSequence)"Hier ist Ihr Profil", Toast.LENGTH_SHORT).show();
    }

    public void topPictures(View v)
    {
        Intent intent = new Intent(this, TopPicturesActivity.class);
        intent.putExtra("email",(String)getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
        Toast.makeText(getApplicationContext(), (CharSequence)"Hier sind die besten Bilder", Toast.LENGTH_SHORT).show();
    }
}
