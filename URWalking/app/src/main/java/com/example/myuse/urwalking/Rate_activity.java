package com.example.myuse.urwalking;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

/*
*   Activity in der Bilder bewertet werden können
 */
public class Rate_activity extends AppCompatActivity {
    ImageView current;
    ProgressBar progress;
    CheckBox check;
    TextView header;
    String currentName;
    ParseObject loadedPic;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_activity);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        check = (CheckBox)findViewById(R.id.checkBox);
        header = (TextView)findViewById(R.id.headerRate);
        current = (ImageView)findViewById(R.id.currentImage);
        nextpicture();
        progress.setVisibility(View.INVISIBLE);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
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
        Random random = new Random();
        int randomStore = random.nextInt(shops.length) + 1;
        currentName= shops[randomStore];
        header.setText(shops[randomStore]);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("images");
        query.whereEqualTo("Store", shops[randomStore]);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> imageList, ParseException e) {
                if (e == null) {
                    int topImageIndex = getRandomImageIndex(imageList);
                    ParseFile fileObject = (ParseFile) imageList.get(topImageIndex).get("Image");
                    loadedPic = imageList.get(topImageIndex);
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                current.setImageBitmap(bmp);
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
        int randomIndex = random.nextInt(imageList.size());
        return randomIndex;
    }

    private void Rate(){
        Algorithm(ParseUser.getCurrentUser().getInt("score"));
    }



    private int Algorithm(int score){
        score+=20;final int finalScore = score;
        ParseUser user = ParseUser.getCurrentUser();
        user.put("score", finalScore);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                } else {
                    Toast.makeText(getApplicationContext(), "hat nicht geworked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return score;
    }

    public void nicePhoto(View v) {
        int currentLikes = loadedPic.getInt("likes");
        loadedPic.put("likes",currentLikes+1);//add to likes 1
        if(check.isActivated()){//add to notthewanted 1
            checkPhoto();
        }
        loadedPic.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
       nextpicture();
    }
    public void badPhoto(View v) {
        int currentDisLikes = loadedPic.getInt("dislikes");
        loadedPic.put("dislikes",currentDisLikes+1);//add to dislikes 1
        if(check.isActivated()){//add to notthewanted 1
            checkPhoto();
        }
        loadedPic.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
       nextpicture();
    }

    private void checkPhoto(){
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
