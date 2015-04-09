package com.tdd4android.fairyfingers;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;


public class DrawActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_draw);

      FairyFingersView view = (FairyFingersView) findViewById(R.id.fingersView);
      new DecayTimer(view).start();
    }
}
