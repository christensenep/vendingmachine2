package com.christensenep;

public enum CoinType {
  QUARTER,
  DIME,
  NICKEL;

  public static CoinType identifyCoin(Coin coin) {
    return QUARTER;
  }
}
