package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.*;
import android.util.*;
import android.view.*;

import com.tdd4android.fairyfingers.core.*;

public class FairyFingersView extends View {
  private Paint paint = new Paint();
  private ColorSequence colors = new SummerPalette();
  private FairyFingersCore core = new FairyFingersCore(colors);
  private CoreMotionEvent coreMotionEvent = new CoreMotionEvent();

  public FairyFingersView(Context context) {
    super(context);
  }

  public FairyFingersView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FairyFingersView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDraw(final Canvas canvas) {
    paint.setStrokeWidth(10);
    paint.setStrokeCap(Paint.Cap.ROUND);
    CoreCanvas coreCanvas = new CoreCanvas() {
      @Override
      public void drawLine(float startX, float startY, float stopX, float stopY, int color, int alpha) {
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
      }
    };
    for (Trail trail : core.trails()) {
      trail.drawOn(coreCanvas);
    }
  }

  @Override
  public boolean onTouchEvent(final MotionEvent event) {
    coreMotionEvent.resetEvent(event.getActionMasked(), event.getActionIndex());

    for (int i = 0; i < event.getPointerCount(); i++) {
      coreMotionEvent.addPointer(event.getPointerId(i), event.getX(i), event.getY(i));
    }
    coreMotionEvent.deliverEventTo(core);
    invalidate();
    return true;
  }

  public void decay() {
    core.decay();
    invalidate();
  }
}
