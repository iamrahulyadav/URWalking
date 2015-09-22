package com.example.myuse.urwalking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TopPicturesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] stores;
    ImageView topImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_pictures);

        Button backButton = (Button)findViewById(R.id.backwards);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainMenu();
            }
        });
        topImage = (ImageView) findViewById(R.id.topImage);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        stores = new String[]{"test", "boden", "tv", "lisa", "Prag Madame Taussauds"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stores);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("images");
        query.whereEqualTo("Store", stores[position]);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> imageList, ParseException e) {
                int topImageIndex = getTopImageIndex(imageList);
                ParseFile fileObject = (ParseFile) imageList.get(topImageIndex).get("Image");
                fileObject.getDataInBackground(new GetDataCallback() {

                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Log.d("test", "We've got data in data.");
                            // Decode the Byte[] into
                            // Bitmap
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                            // Set the Bitmap into the
                            // ImageView
                            topImage.setImageBitmap(bmp);

                        } else {
                            Log.d("test",
                                    "There was a problem downloading the data.");
                        }
                    }
                });

                if (e == null) {
                    Log.d("image", "Retrieved " + imageList.size() + " images");
                } else {
                    Log.d("image", "Error: " + e.getMessage());
                }
            }
        });
    }

    private int getTopImageIndex(List<ParseObject> imageList){
        int bestIndex = 0;
        double bestScore = 0;
        for (int i=0; i<imageList.size();i++){
            double score = calculateScore(imageList.get(i));
            if (bestScore <= score){
                bestIndex = i;
                bestScore = score;
            }
        }

        return bestIndex;
    }

    private double calculateScore(ParseObject image){
        int likes = (int) image.get("likes");
        int dislikes = (int) image.get("dislikes");
        int notWanted = (int) image.get("notTheWanted");
        int numberOfRates = likes+dislikes;
        long createdAt = image.getCreatedAt().getTime();
        Calendar c = Calendar.getInstance();
        long currentDate = c.getTime().getTime();
        int dayDifference = (int) ((currentDate - createdAt)/(1000*60*60*24));
        double likeValue = (double) (likes/(numberOfRates+1d));
        double notWantedValue = (double) ((numberOfRates-notWanted)/(numberOfRates+1d));
        double dayValue = (double) (1d/(dayDifference+1d));
        double score = likeValue * notWantedValue * dayValue;
        Log.d("image", "likes: "+ likes + ", dislikes "+dislikes+ ", DayDiff: "+dayDifference+" ,notWanted: "+notWanted+", numberofrates: "+numberOfRates);
        Log.d("image", "likeValue: " + likeValue + ", notWantedValue: "+notWantedValue+ ", DayValue: "+dayValue);
        Log.d("image", "score: "+score);
        return score;
    }


    public void backToMainMenu(){
        Intent intent = new Intent(this, MainMenue_Activity.class);
        intent.putExtra("email", (String) getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_pictures, menu);
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}