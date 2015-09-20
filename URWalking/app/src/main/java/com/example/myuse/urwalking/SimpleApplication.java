package com.example.myuse.urwalking;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by MyUse on 20.09.2015.
 */
public class SimpleApplication extends Application{
    public void OnCreate() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "A9qtbvCLt8mcxQVtfPWjawH4oieydqk69dr54cR6", "P4ifu3C6ywLgZaiiH8yMcleqC3EDsN3I0CVBW7gS");


    }
}
