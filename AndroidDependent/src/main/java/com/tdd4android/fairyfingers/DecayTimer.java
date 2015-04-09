package com.tdd4android.fairyfingers;

import android.os.Handler;
import android.view.View;

import com.tdd4android.fairyfingers.core.FairyFingersCore;

public class DecayTimer {
  public static final long REFRESH_INTERVAL = 100;

  private final FairyFingersView view;
  private Handler handler = new Handler();

  public DecayTimer(FairyFingersView view) {
    this.view = view;
  }

  Runnable updateView = new Runnable() {
    @Override
    public void run() {
      view.decay();
      handler.postDelayed(updateView, REFRESH_INTERVAL);
    }
  };

  public void start() {
    handler.post(updateView);
  }

  public void stop() {
    handler.removeCallbacks(updateView);
  }
}
