package com.tdd4android.fairyfingers.core;

import java.util.Iterator;

public class ColorSequence {
  private int[] colors;
  private int index;

  public ColorSequence(int ... colors) {
    this.colors = colors;
  }

  public int next() {
    return colors[index++ % colors.length];
  }

  public static int rgb(int red, int green, int blue) {
    return 0xFF000000 | red << 16 | green << 8 | blue ;
  }
}
