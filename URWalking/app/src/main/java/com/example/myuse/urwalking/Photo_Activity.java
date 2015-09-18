package com.example.myuse.urwalking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Photo_Activity extends Activity {
    Button b1,b2;
    ImageView iw;
    static int CAM_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button_gallery);
        iw= (ImageView)findViewById(R.id.imageView);

        b1.setOnClickListener(new photoClicker());
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load gallery
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CAM_REQUEST){
            Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
            iw.setImageBitmap(thumbnail);
        }
    }

    class photoClicker implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            //TODO: geht nicht, wieso?
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAM_REQUEST);
        }
    }


}
