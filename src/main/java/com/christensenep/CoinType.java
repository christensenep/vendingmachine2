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

  private boolean isMatch(Coin coin) {
    return ((coin.getWeight() == this.weight) && (coin.getDiameter() == this.diameter));
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
