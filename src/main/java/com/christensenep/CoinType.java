package com.christensenep;

public enum CoinType {
  QUARTER(5.670, 24.26, 25),
  DIME(2.268, 17.91, 10),
  NICKEL(5.000, 21.21, 5);

  private final double weight;
  private final double diameter;
  private final int value;

  CoinType(double weight, double diameter, int value) {
    this.weight = weight;
    this.diameter = diameter;
    this.value = value;
  }

  public double getWeight() { return this.weight; }
  public double getDiameter() { return this.diameter; }
  public int getValue() { return this.value; }

  private boolean isMatch(Coin coin, double weightTolerance, double diameterTolerance) {
    return ((Math.abs(coin.getWeight() - this.weight) <= weightTolerance) &&
            (Math.abs(coin.getDiameter() - this.diameter) <= diameterTolerance));
  }

  public static CoinType identifyCoin(Coin coin, double weightTolerance, double diameterTolerance) {
    CoinType retCoinType = null;

    for (CoinType coinType : CoinType.values()) {
      if (coinType.isMatch(coin, weightTolerance, diameterTolerance)) {
        retCoinType = coinType;
        break;
      }
    }

    return retCoinType;
  }
}
