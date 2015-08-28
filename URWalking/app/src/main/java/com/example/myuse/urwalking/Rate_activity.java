package com.example.myuse.urwalking;

import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

/*
*   Activity in der Bilder bewertet werden können
 */
public class Rate_activity extends AppCompatActivity {
  // int[] images = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four};
  // ImageView current;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_activity);


        //TODO: Das ImageView dazu bringen das Bild zu wechseln, stürtzt jedes mal ab..
        //ImageView current = (ImageView)findViewById(R.id.currentImage);
        //current.setImageDrawable(getResources().getDrawable(R.drawable.three, getTheme()));

    }


    /*
    *   Zukünftiger Start um Bilder aus dem Internet zu ziehen
    */
    private void fillPictureArray() {
    }


    /*
    *   Zukünftiger Start um nächstes Bild auszuwählen
    */
    private void nextpicture(){

    }

    public void nicePhoto(View v) {
       nextpicture();
    }
    public void badPhoto(View v) {
       nextpicture();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate_activity, menu);
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
}
