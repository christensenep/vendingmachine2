package com.christensenep;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Stack;

public class Machine {
  private Stack<Coin> insertedCoins = new Stack<Coin>();
  private List<Coin> returnedCoins = new ArrayList<Coin>();
  private EnumMap<ProductType, Integer> storedProducts;

  public Machine() {
    storedProducts = new EnumMap<ProductType, Integer>(ProductType.class);
    for (ProductType productType : ProductType.values()) {
      storedProducts.put(productType, 0);
    }
  }

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

  int numProducts(ProductType productType) {
    return storedProducts.get(productType);
  }

  public void addProducts(ProductType productType, int numAdded) {
    storedProducts.put(productType, storedProducts.get(productType) + numAdded);
  }

  public void insertCoin(Coin coin) {
    if (coin == null) {
      throw new NullPointerException();
    }

    CoinType coinType = CoinType.identifyCoin(coin, 0, 0);
    if (coinType != null) {
      insertedCoins.add(coin);
    }
    else {
      returnedCoins.add(coin);
    }
  }

  public void ejectCoins() {
    this.returnedCoins.addAll(insertedCoins);
    this.insertedCoins.clear();
  }

  public List<Coin> getReturnedCoins() {
    return returnedCoins;
  }
}
