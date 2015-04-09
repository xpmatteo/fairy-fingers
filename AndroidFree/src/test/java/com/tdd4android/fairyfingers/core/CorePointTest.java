package com.tdd4android.fairyfingers.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CorePointTest {
  CorePoint p = new CorePoint(0f, 0f);

  @Test
  public void decay() throws Exception {
    assertEquals(255, p.alpha);
    p.decay();
    assertEquals(255 - 25, p.alpha);
  }

  @Test
  public void decayToNothing() throws Exception {
    for (int i = 0; i < 100; i++) {
      p.decay();
    }    
    assertEquals(0, p.alpha);
  }
}
