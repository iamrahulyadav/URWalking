package com.example.myuse.urwalking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;
import java.util.Random;

/*
*   Activity in der Bilder bewertet werden können
 */
public class Rate_activity extends AppCompatActivity {
    private ImageView current;
    private Bitmap bmp;
    private ProgressBar progress;
    private CheckBox check;
    private TextView header;
    private String currentName;
    private ParseObject loadedPic;
    private boolean loading = false;

    private final int rateScoreNumber = 20;
    private final int photoPropScoreNumber = 100;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_activity);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        check = (CheckBox)findViewById(R.id.checkBox);
        header = (TextView)findViewById(R.id.headerRate);
        current = (ImageView)findViewById(R.id.currentImage);
        nextpicture();
    }

    /*
    *   Zukünftiger Start um nächstes Bild auszuwählen
    */
    private void nextpicture(){
        Random random = new Random();
        int randomStore = random.nextInt(shops.length) + 1;
        currentName= shops[randomStore];
        progress.setVisibility(View.VISIBLE);
        current.setVisibility(View.INVISIBLE);
        header.setText(currentName);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("images");
        query.whereEqualTo("Store", currentName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> imageList, ParseException e) {
                if (e == null) {
                    int topImageIndex = getRandomImageIndex(imageList);
                    ParseFile fileObject = (ParseFile) imageList.get(topImageIndex).get("Image");
                    loadedPic = imageList.get(topImageIndex);
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                if(null!=bmp) {
                                    bmp.recycle();
                                }
                                bmp = BitmapFactory.decodeByteArray(data,0, data.length);
                                current.setVisibility(View.VISIBLE);
                                current.setImageBitmap(bmp);
                                progress.setVisibility(View.INVISIBLE);
                            } else {
                                Toast.makeText(getApplicationContext(), "Konnte nicht geladen werden", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Konnte nicht geladen werden", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Rate();
    }

    private int getRandomImageIndex(List<ParseObject> imageList){
        Random random = new Random();
        return random.nextInt(imageList.size());
    }

    private void Rate(){
        String userName = ParseUser.getCurrentUser().getUsername();//get the username
        ParseQuery<ParseObject> query = ParseQuery.getQuery("scores");
        query.whereEqualTo("username", userName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {//if everything worked well / user was found...
                    ParseObject scoreObject = scoreList.get(0);
                    int score = scoreObject.getInt("score");    //get the score
                    score += rateScoreNumber;
                    scoreObject.put("score", score);
                    scoreObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                        }
                    });
                }
            }
        });
    }

    private void sumUserPoints(final int multiplicator){    /* sums up the points of the user of a picture */
        String userName = loadedPic.getString("User");      //get the name of the user of the rated picture
        ParseQuery<ParseObject> query = ParseQuery.getQuery("scores");
        query.whereEqualTo("username", userName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {                           //if everything worked well / user was found...
                    ParseObject scoreObject = scoreList.get(0);
                    int score = scoreObject.getInt("score");       //get the score of proprietor of the picture
                    score += photoPropScoreNumber * multiplicator;               //set it up by x times the multiplicator
                    scoreObject.put("score", score);                //then save it in the user object
                    scoreObject.saveInBackground(new SaveCallback() {//and finally in the database
                        @Override
                        public void done(ParseException e) {
                        }
                    });
                }
            }
        });
    }



    public void nicePhoto(View v) {  // sums up the like number
        if(!loading) {
            int currentLikes = loadedPic.getInt("likes");
            loadedPic.put("likes", currentLikes + 1);//add to likes 1
            if (check.isChecked()) {//add to notthewanted 1
                checkPhoto();
                sumUserPoints(1);
            }
            else {
                sumUserPoints(2);
            }
            pictureRating();
        }
    }
    public void badPhoto(View v) { // sums up the dislike number
        if(!loading) {
            int currentDisLikes = loadedPic.getInt("dislikes");
            loadedPic.put("dislikes", currentDisLikes + 1);//add to dislikes 1
            if (check.isChecked()) {//add to notthewanted 1
                checkPhoto();
            }
            else {
                sumUserPoints(1);
            }
            pictureRating();
        }
    }

    private void pictureRating(){           // checks if picture is wanted or not, saves the picture and load the next
        loadedPic.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            }
        });
        nextpicture();
    }




    private void checkPhoto(){                          // sums up the notWanted number
        int currentNots = loadedPic.getInt("notTheWanted");
        loadedPic.put("notTheWanted",currentNots+1);//add to dislikes 1
    }

    private static final String[] shops = new String[] {
            "Alla Turca Feinkost","Apotheke im Donau-Einkaufszentrum","Ärzte im Donau-Gesundheitszentrum", "asiagourmet",
            "Bäcker Bachmeier","Bäckerei Schifferl","Bäckerei Wünsche im Edeka","Barbershop","Base/E-Plus Shop","Baumgartner Optik",
            "Bears&Friends","Benetton", "Betty Barclay Store","Bijou Brigitte","Biomarkt Neuhoff","Blumen Sitzberger",
            "Bücher Pustet","Bücher Pustet Extra","Bunte Truhe","C&A","Café & Bäckerei Zink","Café & Konditorei Lederer",
            "Café Centro","Café Latte","Softeis-Automaten","CBR","Christ Juweliere & Uhrmacher","Cookmal!",
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

}
