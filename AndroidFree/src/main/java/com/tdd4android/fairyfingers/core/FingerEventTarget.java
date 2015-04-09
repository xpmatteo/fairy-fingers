package com.tdd4android.fairyfingers.core;

public interface FingerEventTarget {
  void onFingerDown(int fingerId, float x, float y);
  void onFingerMove(int fingerId, float x, float y);
  void onFingerUp(int fingerId);
}
