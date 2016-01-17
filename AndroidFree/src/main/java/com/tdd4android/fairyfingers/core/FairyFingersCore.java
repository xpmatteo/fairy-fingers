package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore implements FingerEventTarget {
  private Map<Integer, Trail> openTrails = new HashMap<Integer, Trail>();
  private List<Trail> closedTrails = new ArrayList<Trail>();
  private ColorSequence colors;

  public FairyFingersCore(ColorSequence colors) {
    this.colors = colors;
  }

  public void onFingerMove(int pointerId, float x, float y) {
    Trail trail = openTrails.get(pointerId);
    trail.addPoint(x, y);
  }

  public void onFingerUp(int pointerId) {
    Trail trail = removeOpenLine(pointerId);
    closedTrails.add(trail);
  }

  public void onFingerDown(int fingerId, float x, float y) {
    openTrails.put(fingerId, new Trail(newColor(), x, y));
  }

  private int newColor() {
    return colors.next();
  }

  public List<Trail> trails() {
    List<Trail> trails = new ArrayList<Trail>();
    trails.addAll(closedTrails);
    trails.addAll(openTrails.values());
    return trails;
  }

  private void deleteEmptyLines() {
    for (Iterator<Trail> it = closedTrails.iterator(); it.hasNext(); ) {
      Trail trail = it.next();
      if (trail.emptyLine()) {
        it.remove();
      }
    }
  }

  public Trail trails(int index) {
    return trails().get(index);
  }

  private Trail removeOpenLine(int pointerId) {
    Trail trail = openTrails.remove(pointerId);
    if (null == trail)
      throw new IllegalStateException("Could not find open trail with id " + pointerId);
    return trail;
  }

  public void decay() {
    for (Trail trail : trails()) {
      trail.decay();
    }
    deleteEmptyLines();
  }
}
