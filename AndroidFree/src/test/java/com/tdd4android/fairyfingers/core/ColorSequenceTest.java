package com.tdd4android.fairyfingers.core;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ColorSequenceTest {

  @Test
  public void makeColorFromRgb() throws Exception {
    assertEquals(0xFF010203, ColorSequence.rgb(1, 2, 3));

  }

  @Test
  public void oneColor() throws Exception {
    ColorSequence sequence = new ColorSequence(0x1111111);
    assertEquals(0x1111111, sequence.next());
    assertEquals(0x1111111, sequence.next());
    assertEquals(0x1111111, sequence.next());
    assertEquals(0x1111111, sequence.next());
  }

  @Test
  public void alternateColors() throws Exception {
    ColorSequence sequence = new ColorSequence(1, 2, 3);
    assertEquals(1, sequence.next());
    assertEquals(2, sequence.next());
    assertEquals(3, sequence.next());
    assertEquals(1, sequence.next());
    assertEquals(2, sequence.next());
    assertEquals(3, sequence.next());
  }

}
