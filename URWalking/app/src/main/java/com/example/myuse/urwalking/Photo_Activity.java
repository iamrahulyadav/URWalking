package com.example.myuse.urwalking;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Photo_Activity extends AppCompatActivity{
    private Button b1,b2,b3;
    private ImageView iw;
    private ProgressBar uploadBar;
    private String m_Text = "";
    private Bitmap bitmap = null;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button_gallery);
        b3=(Button)findViewById(R.id.button_upload);
        iw= (ImageView)findViewById(R.id.imageView);
        uploadBar = (ProgressBar) findViewById(R.id.uploadBar);

        iw.setOnClickListener(new photoClicker());
        b1.setOnClickListener(new photoClicker());
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Load Gallery", Toast.LENGTH_SHORT).show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) pushPicture();
                else{
                    Toast.makeText(getApplicationContext(),("Erst einmal ein Foto machen =)"), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static final String[] shops = new String[] {
            "Alla Turca Feinkost","Apotheke","Ärzte im Donau-Gesundheitszentrum", "asiagourmet",
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

    private void pushPicture(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hier bitte das Geschäft eingeben");

        // Set up the input
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, shops);
        final AutoCompleteTextView input = new AutoCompleteTextView(this);
        input.setAdapter(adapter);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                UploadTask upload = new UploadTask();
                upload.execute(input.getText().toString());

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

    public static Bitmap decodeSampledBitmapFromFile (File imageFile,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0){
            Log.d("code", "resultcode: " + resultCode);
            if (imageFile.exists()){
                Bitmap bMap = decodeSampledBitmapFromFile(imageFile, 200, 200);
                iw.setImageBitmap(bMap);
                bitmap = decodeSampledBitmapFromFile(imageFile, 500, 500);
            }
        }
    }

    private boolean checkIfIsShop(){
        for(int i = 0; i< shops.length;i++){
            if(shops[i].equals(m_Text))return true;
        }
        return false;
    }

    class photoClicker implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg");
            Uri tempUri = Uri.fromFile(imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, 0);
        }
    }

    class UploadTask extends AsyncTask<String, Void, Void>
    {


        @Override
        protected void onPreExecute() {
            uploadBar.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Uploading...", Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(String... params) {

            m_Text = params[0];

            if (checkIfIsShop()) {
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("store" + ".bmp", image);
                // Upload the image into Parse Cloud
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                        } else {
                            Toast.makeText(getApplicationContext(), "Upload didn't work because: " + e, Toast.LENGTH_SHORT).show();
                            Log.d("Fail", "1 Did not work because: " + e);
                        }
                    }
                });
                // Create a New Class called "images" in Parse
                ParseObject imgupload = new ParseObject("images");
                // Create a column named "User" and set the string
                String username = ParseUser.getCurrentUser().getUsername();
                imgupload.put("User", username);
                // Create a column named "Image" and set the string
                imgupload.put("Image", file);
                // Create a column named "Store" and insert the image
                imgupload.put("Store", m_Text);
                // Create a column named "likes" and insert the value
                imgupload.put("likes", 0);
                // Create a column named "dislikes" and insert the value
                imgupload.put("dislikes", 0);
                // Create a column named "notTheWanted" and insert the value
                imgupload.put("notTheWanted", 0);
                // Create the class and the columns
                imgupload.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Show a simple toast message
                            Toast.makeText(Photo_Activity.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                            uploadBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Upload didn't work 2", Toast.LENGTH_SHORT).show();
                            Log.d("Fail", "2 Did not work because: " + e);
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Bitte vorhandenen Shop auswählen", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_, menu);
        return true;
    }

}


