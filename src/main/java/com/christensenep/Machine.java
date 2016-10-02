package com.christensenep;

import java.util.Stack;

public class Machine {
  private Stack<Coin> insertedCoins = new Stack<Coin>();

  int getInsertedValue() {
    int insertedValue = 0;

    for (Coin coin : insertedCoins) {
      CoinType coinType = CoinType.identifyCoin(coin, 0, 0);
      if (coinType != null) {
        insertedValue += coinType.getValue();
      }
    }

    return insertedValue;
  }

  public void insertCoin(Coin coin) {
    insertedCoins.add(coin);
  }

  public void ejectCoins() {
    this.insertedCoins.clear();
  }
}
