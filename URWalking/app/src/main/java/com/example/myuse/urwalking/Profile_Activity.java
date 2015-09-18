package com.example.myuse.urwalking;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Profile_Activity extends AppCompatActivity {

    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        counter = (TextView)findViewById(R.id.counter);
        getScore();
        TextView mailAdress = (TextView)findViewById(R.id.profileEmail);
        mailAdress.setText(getIntent().getCharSequenceExtra("email"));
    }
    public void backFromProfile(View v){
        Intent intent = new Intent(this, MainMenue_Activity.class);
        intent.putExtra("email",(String)getIntent().getCharSequenceExtra("email"));
        this.startActivity(intent);
    }

    private void getScore(){
        String Message = "";
        try{
            FileInputStream fis = openFileInput("score");
            InputStreamReader isr = new InputStreamReader((fis));
            BufferedReader br = new BufferedReader(isr);
            StringBuffer stringBuffer= new StringBuffer();
            while((Message = br.readLine())!=null){
                stringBuffer.append(Message+"\n");
            }
            Message = stringBuffer+"";
        }catch(Exception e){

        }
        ((TextView)findViewById(R.id.counter)).setText(Message);
        int counter = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_, menu);
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
