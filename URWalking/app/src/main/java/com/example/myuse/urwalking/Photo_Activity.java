package com.example.myuse.urwalking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class Photo_Activity extends Activity {
    Button b1,b2,b3;
    ImageView iw;
    private String m_Text = "";
    static int CAM_REQUEST;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button_gallery);
        b3=(Button)findViewById(R.id.button_upload);
        iw= (ImageView)findViewById(R.id.imageView);


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
                if(bitmap!=null)pushPicture();
            }
        });
    }

    private void pushPicture(){
        setInTitle();
    }

    private void setInTitle(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hier bitte das Geschäft eingeben");

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

                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile(m_Text+".bmp",image);
                // Upload the image into Parse Cloud
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Upload didn't work", Toast.LENGTH_SHORT).show();
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
                // Create the class and the columns
                imgupload.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Show a simple toast message
                            Toast.makeText(Photo_Activity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Upload didn't work", Toast.LENGTH_SHORT).show();
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CAM_REQUEST){
            Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
            iw.setImageBitmap(thumbnail);
            bitmap = thumbnail;
        }
    }

    class photoClicker implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAM_REQUEST);
        }
    }


}
