package com.tdd4android.fairyfingers.core;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FairyFingersCoreTest {
  FairyFingersCore core = new FairyFingersCore(new ColorSequence(0xFF0000FF));

  @Test
  public void noLinesAtStart() throws Exception {
    assertEquals(0, core.lines().size());
  }

  @Test
  public void startOneLine() throws Exception {
    core.onFingerDown(0, 10.0f, 100.0f);

    assertEquals(1, core.lines().size());
    assertEquals("(10.0,100.0)", core.lines(0).toString());
  }

  @Test
  public void oneLineDownMove() throws Exception {
    core.onFingerDown(0, 10.0f, 110.0f);
    core.onFingerMove(0, 20.0f, 120.0f);
    core.onFingerMove(0, 30.0f, 130.0f);

    assertEquals("(10.0,110.0)->(20.0,120.0)->(30.0,130.0)", core.lines(0).toString());
  }

  @Test
  public void oneLineDownMoveUp() throws Exception {
    core.onFingerDown(0, 10.0f, 100.0f);
    core.onFingerMove(0, 20.9f, 120.0f);
    core.onFingerMove(0, 30.0f, 130.0f);
    core.onFingerUp(0);

    assertEquals(1, core.lines().size());
    assertEquals("(10.0,100.0)->(20.9,120.0)->(30.0,130.0)", core.lines(0).toString());
  }

  @Test
  public void dashDash() throws Exception {
    core.onFingerDown(0, 10.0f, 110.0f);
    core.onFingerMove(0, 20.0f, 120.0f);
    core.onFingerUp(0);

    core.onFingerDown(0, 210.0f, 310.0f);
    core.onFingerMove(0, 220.0f, 320.0f);
    core.onFingerUp(0);

    assertEquals("(10.0,110.0)->(20.0,120.0)", core.lines(0).toString());
    assertEquals("(210.0,310.0)->(220.0,320.0)", core.lines(1).toString());
  }

  @Test
  public void twoFingersStaggered() throws Exception {
    core.onFingerDown(0, 1f, 2f);
    core.onFingerMove(0, 3f, 4f);

    core.onFingerDown(1, 100f, 200f);
    core.onFingerMove(0, 5f, 6f);
    core.onFingerMove(1, 300f, 400f);

    core.onFingerUp(0);

    core.onFingerMove(1, 500f, 600f);
    core.onFingerUp(1);

    assertEquals(2, core.lines().size());
    assertEquals("(1.0,2.0)->(3.0,4.0)->(5.0,6.0)", core.lines(0).toString());
    assertEquals("(100.0,200.0)->(300.0,400.0)->(500.0,600.0)", core.lines(1).toString());
  }

  @Test
  public void oldLineDeletion() throws Exception {
    core.onFingerDown(0, 10.0f, 110.0f);
    core.onFingerMove(0, 20.0f, 120.0f);
    core.onFingerUp(0);

    assertEquals(1, core.lines().size());

    halfDecay();

    core.onFingerDown(0, 100.0f, 110.0f);
    core.onFingerMove(0, 200.0f, 120.0f);
    core.onFingerUp(0);

    assertEquals(2, core.lines().size());

    halfDecay();

    assertEquals(1, core.lines().size());

    halfDecay();

    assertEquals(0, core.lines().size());
  }

  private void halfDecay() {
    for (int i = 0; i < 6; i++) core.decay();
  }

}
