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
import android.widget.ProgressBar;
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
    Bitmap bmp;

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
        stores = new String[]{"Alla Turca Feinkost","Apotheke","Ärzte im Donau-Gesundheitszentrum", "asiagourmet",
                "Bäcker Bachmeier","Bäckerei Schifferl","Bäckerei Wünsche im Edeka","Barbershop","Base/E-Plus Shop","Baumgartner Optik",
                "Bears&Friends","Benetton", "Betty Barclay Store","Bijou Brigitte","Biomarkt Neuhoff","Blumen Sitzberger",
                "Bücher Pustet","Bücher Pustet Extra","Bunte Truhe","C&A","Café & Bäckerei Zink","Café & Konditorei Lederer",
                "Café Centro","Café Latte","CBR","Christ Juweliere & Uhrmacher","Cookmal!",
                "Copy & paper","Das Hörhaus – Terzo-Zentrum","Dean & David","Depot","DER Reisebüro","DER Reisebüro in der Galeria Kaufhof",
                "Deutsche Post & Postbankfinanzcenter","Die Grüne Bar","dm drogeriemarkt","Douglas", "E-Center EDEKA",
                "Ebner Backwaren","Eiscafé Center Italia","ENGEL Briefmarken und Münzen Gold- und Silberankauf","Equivalenza",
                "ESPRIT","eterna","Fisch Maier","Fitness-Studio – Die Insel","Fonds Laden","Fotohaus Zacharias","Freie Automatentankstelle",
                "Friseur Klier in der Galeria Kaufhof","Galeria Kaufhof","GameStop","Geox Shop","Gerry Weber","Glöckl Biergarten",
                "Glöckl der regensburger GmbH","Goldschmiede Pfeiffer","H&M Hennes & Mauritz","HAARMODEN helga dantlinger friseure",
                "Hongkong-City","Hornung Tee- und Schokoladenhaus","Hussel Confiserie","idee. Creativmarkt","Intersport Tahedl",
                "Jack Wolfskin Store","K&L Ruppert","Kinderclub Paletti","Kräuterhex","Landspezialitäten Kruschwitz",
                "Leder Streck","Leo's Pure Jeans","LiZaa","Luisa","Mango","McDonald's","Metzgerei Gierstorfer",
                "Metzgerei Krain","mister*lady young fashion","Modern Hair","More & More","Müller Drogeriemarkt","Musikus",
                "Nagelstudio L.A. Nails","Nanu-Nana","New Yorker","Nicklas Textilpflege","nook","NORDSEE","O2 Shop",
                "Only","Optik Schwarz","Orsay","Palmers","Papeterie Nagel","Peek & Cloppenburg","Penni Moden",
                "Pöllinger Leder & Tracht","Pressezentrum & Mittelbayerischer Kartenvorverkauf",
                "RAPID Key & Go","Rehorik Feinkost","RENO Schuh GmbH","River","Runners Point","Salon Reitter",
                "Sanitätshaus Reiss","Saturn","Scarpa Schuhe","Seceda","Selmair Spielzeug","Sergent Major Kindermode",
                "SIEGERT Herrenausstatter","Sky Service-Point","Softeisstand","Sparda Bank Ostbayern eG",
                "Sparkasse Regensburg","Street One","Subway","Sutor Schuhe","Swarovski","T-Premium Shop",
                "Tabak Götz","Tally Weijl","Tchibo","Telekom Shop","Thalia","Thomas Cook","Thomas Schuhe","Tretter Schuhe",
                "Triumph","TUI ReiseCenter","Ulla Popken","Vilsmeier Reformhaus","Vinzenz Murr","Vodafone","WMF"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stores);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if (bmp != null) bmp.recycle();
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
                            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

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
        //double likeValue = (double) (likes/(numberOfRates+1d));
        double likeValue = getLikeValue(likes, numberOfRates);
        double notWantedValue = (double) ((numberOfRates-notWanted)/(numberOfRates+0.1d));
        double dayValue = (double) (1d/(dayDifference+0.1d));
        double score = likeValue * notWantedValue * dayValue;
        Log.d("image", "likes: "+ likes + ", dislikes "+dislikes+ ", DayDiff: "+dayDifference+" ,notWanted: "+notWanted+", numberofrates: "+numberOfRates);
        Log.d("image", "likeValue: " + likeValue + ", notWantedValue: "+notWantedValue+ ", DayValue: "+dayValue);
        Log.d("image", "score: "+score);
        return score;
    }

    private double getLikeValue(int likes, int rates){
        double z = 1.96;
        if (rates == 0) {rates += 1;}
        double p = (double)(likes/rates);

        return (double) (p + z*z/(2*rates) - z * Math.sqrt((p*(1-p)+z*z/(4*rates))/rates))/(1+z*z/rates);
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
