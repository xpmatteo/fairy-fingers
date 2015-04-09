package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore implements FingerEventTarget {
  private Map<Integer, Line> openLines = new HashMap<Integer, Line>();
  private List<Line> closedLines = new ArrayList<Line>();
  private ColorSequence colors;

  public FairyFingersCore(ColorSequence colors) {
    this.colors = colors;
  }

  public void onFingerMove(int pointerId, float x, float y) {
    Line line = openLines.get(pointerId);
    line.addPoint(x, y);
  }

  public void onFingerUp(int pointerId) {
    Line line = removeOpenLine(pointerId);
    closedLines.add(line);
  }

  public void onFingerDown(int fingerId, float x, float y) {
    openLines.put(fingerId, new Line(newColor(), x, y));
  }

  private int newColor() {
    return colors.next();
  }

  public List<Line> lines() {
    List<Line> lines = new ArrayList<Line>();
    lines.addAll(closedLines);
    lines.addAll(openLines.values());
    return lines;
  }

  private void deleteEmptyLines() {
    for (Iterator<Line> it = closedLines.iterator(); it.hasNext(); ) {
      Line line = it.next();
      if (line.emptyLine()) {
        it.remove();
      }
    }
  }

  public Line lines(int index) {
    return lines().get(index);
  }

  private Line removeOpenLine(int pointerId) {
    Line line = openLines.remove(pointerId);
    if (null == line)
      throw new IllegalStateException("Could not find open line with id " + pointerId);
    return line;
  }

  public void decay() {
    for (Line line : lines()) {
      line.decay();
    }
    deleteEmptyLines();
  }
}
