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

  public void addProducts(ProductType productType, int numAdded) {
    if (numAdded < 0) {
      throw new IllegalArgumentException("numAdded cannot be negative");
    }

    storedProducts.put(productType, storedProducts.get(productType) + numAdded);
  }

  int numProducts(ProductType productType) {
    return storedProducts.get(productType);
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

  public void ejectCoins() {
    this.returnedCoins.addAll(insertedCoins);
    this.insertedCoins.clear();
  }

  public List<Coin> getReturnedCoins() {
    return returnedCoins;
  }
}
