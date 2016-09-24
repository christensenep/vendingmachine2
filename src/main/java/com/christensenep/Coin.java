package com.christensenep;

public class Coin {
  private final double weight;
  private final double diameter;

  public Coin(double weight, double diameter) {
    this.weight = weight;
    this.diameter = diameter;
  }

  public double getWeight() { return this.weight; }
  public double getDiameter() { return this.diameter; }
}
