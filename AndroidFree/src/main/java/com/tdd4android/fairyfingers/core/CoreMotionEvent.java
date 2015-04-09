package com.tdd4android.fairyfingers.core;

public class CoreMotionEvent {
  // Constants copied from android.view.MotionEvent
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;
  public static final int ACTION_POINTER_DOWN     = 5;
  public static final int ACTION_POINTER_UP       = 6;

  // Data copied from the Android MotionEvent
  private int action;
  private int pointerCount;
  private int actionIndex;
  private PointerInfo[] pointers = new PointerInfo[100]; // 100 should be enough

    public void resetEvent(int actionMasked, int index) {
        action = actionMasked;
        actionIndex = index;
        pointerCount = 0;
    }

    public void addPointer(int pointerId, float x, float y) {
        pointers[pointerCount].pointerId = pointerId;
        pointers[pointerCount].x = x;
        pointers[pointerCount].y = y;
        pointerCount++;
    }

    public class PointerInfo {
    public int pointerId;
    public float x, y;
  }

  public CoreMotionEvent() {
    for (int i = 0; i < pointers.length; i++) {
      pointers[i] = new PointerInfo();
    }
  }


  public void deliverEventTo(FingerEventTarget target) {
    switch (action) {
      case ACTION_MOVE:
        for (int pointerIndex = 0; pointerIndex < pointerCount; pointerIndex++) {
          int pointerId = pointers[pointerIndex].pointerId;
          target.onFingerMove(pointerId, pointers[pointerIndex].x, pointers[pointerIndex].y);
        }
        break;
      case ACTION_DOWN:
      case ACTION_POINTER_DOWN:
        int pointerId = pointers[actionIndex].pointerId;
        target.onFingerDown(pointerId, pointers[actionIndex].x, pointers[actionIndex].y);
        break;
      case ACTION_UP:
      case ACTION_POINTER_UP:
        target.onFingerUp(pointers[actionIndex].pointerId);
        break;
      default:
        throw new UnrecognizedActionException();
    }
  }

  public class UnrecognizedActionException extends RuntimeException {
  }
}
