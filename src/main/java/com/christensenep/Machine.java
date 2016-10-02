package com.christensenep;

public class Machine {
  private int insertedValue = 0;

  public int getInsertedValue() {
    return insertedValue;
  }

  public void insertCoin(Coin coin) {
    CoinType coinType = CoinType.identifyCoin(coin, 0, 0);

    if (coinType != null) {
      this.insertedValue += coinType.getValue();
    }
  }
}
