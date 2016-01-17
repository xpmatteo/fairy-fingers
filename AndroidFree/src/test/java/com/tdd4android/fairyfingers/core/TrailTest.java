package com.tdd4android.fairyfingers.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrailTest {
  private final int COLOR = 0xFF123456;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private CoreCanvas coreCanvas = context.mock(CoreCanvas.class);
  private Trail trail = new Trail(COLOR, 10f, 20f);


  @Test
  public void drawNoSegments() throws Exception {
    context.checking(new Expectations() {{
      never(coreCanvas);
    }});

    trail.drawOn(coreCanvas);
  }

  @Test
   public void drawOneSegment() throws Exception {
    context.checking(new Expectations() {{
      oneOf(coreCanvas).drawLine(10f, 20f, 30f, 40f, COLOR, 255);
    }});

    trail.addPoint(30f, 40f);
    trail.drawOn(coreCanvas);
  }

  @Test
  public void drawMoreSegments() throws Exception {
    context.checking(new Expectations() {{
      oneOf(coreCanvas).drawLine(10f, 20f, 100f, 400f, COLOR, 255);
      oneOf(coreCanvas).drawLine(100f, 400f, 200f, 500f, COLOR, 255);
      oneOf(coreCanvas).drawLine(200f, 500f, 300f, 600f, COLOR, 255);
    }});

    trail.addPoint(100f, 400f);
    trail.addPoint(200f, 500f);
    trail.addPoint(300f, 600f);
    trail.drawOn(coreCanvas);
  }

  @Test
  public void testLineDecay() throws Exception {
    context.checking(new Expectations() {{
      oneOf(coreCanvas).drawLine(10f, 20f, 100f, 400f, COLOR, 205);
      oneOf(coreCanvas).drawLine(100f, 400f, 200f, 500f, COLOR, 230);
      oneOf(coreCanvas).drawLine(200f, 500f, 300f, 600f, COLOR, 255);
    }});

    trail.addPoint(100f, 400f);
    trail.decay();
    trail.addPoint(200f, 500f);
    trail.decay();
    trail.addPoint(300f, 600f);
    trail.drawOn(coreCanvas);
  }
}
