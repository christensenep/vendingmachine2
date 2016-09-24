package com.christensenep;

public enum CoinType {
  QUARTER(5.670, 24.26),
  DIME(2.268, 17.91),
  NICKEL(5.000, 21.21);

  private final double weight;
  private final double diameter;

  CoinType(double weight, double diameter) {
    this.weight = weight;
    this.diameter = diameter;
  }

  public static CoinType identifyCoin(Coin coin) {
    CoinType returnValue = null;

    if (coin.getWeight() == QUARTER.weight && coin.getDiameter() == QUARTER.diameter) {
      returnValue = QUARTER;
    }
    else if (coin.getWeight() == DIME.weight && coin.getDiameter() == DIME.diameter) {
      returnValue = DIME;
    }

    return returnValue;
  }
}
