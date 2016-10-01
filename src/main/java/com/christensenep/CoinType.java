package com.christensenep;

public enum CoinType {
  QUARTER(5.670, 24.26, 25),
  DIME(2.268, 17.91, 10),
  NICKEL(5.000, 21.21, 5);

  private static double weightTolerance;
  private static double diameterTolerance;

  private final double weight;
  private final double diameter;
  private final int value;

  CoinType(double weight, double diameter, int value) {
    this.weight = weight;
    this.diameter = diameter;
    this.value = value;
  }

  public static void setWeightTolerance(double iWeightTolerance) { weightTolerance = iWeightTolerance;}
  public static void setDiameterTolerance(double iDiameterTolerance) { diameterTolerance = iDiameterTolerance; }

  public static double getWeightTolerance() { return weightTolerance; }
  public static double getDiameterTolerance() { return diameterTolerance; }

  public double getWeight() { return this.weight; }
  public double getDiameter() { return this.diameter; }
  public int getValue() { return this.value; }

  private boolean isMatch(Coin coin) {
    return ((Math.abs(coin.getWeight() - this.weight) < getWeightTolerance()) &&
            (Math.abs(coin.getDiameter() - this.diameter) < getDiameterTolerance()));
  }

  public static CoinType identifyCoin(Coin coin) {
    CoinType retCoinType = null;

    for (CoinType coinType : CoinType.values()) {
      if (coinType.isMatch(coin)) {
        retCoinType = coinType;
        break;
      }
    }

    return retCoinType;
  }
}
