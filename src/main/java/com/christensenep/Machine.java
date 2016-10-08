package com.christensenep;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Stack;

public class Machine {
  private Stack<Coin> insertedCoins = new Stack<Coin>();
  private List<Coin> returnedCoins = new ArrayList<Coin>();
  private EnumMap<CoinType, Stack<Coin>> storedCoins;
  private EnumMap<ProductType, Stack<Product>> storedProducts;

  private double diameterTolerance = 0.0;
  private double weightTolerance = 0.0;

  public Machine() {
    initStoredProductsMap();
    initStoredCoinsMap();
  }

  private void initStoredProductsMap() {
    storedProducts = new EnumMap<ProductType, Stack<Product>>(ProductType.class);
    for (ProductType productType : ProductType.values()) {
      storedProducts.put(productType, new Stack<Product>());
    }
  }

  private void initStoredCoinsMap() {
    storedCoins = new EnumMap<CoinType, Stack<Coin>>(CoinType.class);
    for (CoinType coinType : CoinType.values()) {
      storedCoins.put(coinType, new Stack<Coin>());
    }
  }

  public double getWeightTolerance() { return this.weightTolerance; }
  public double getDiameterTolerance() { return this.diameterTolerance; }

  public void setWeightTolerance(double weightTolerance) {
    if (weightTolerance < 0.0) {
      throw new IllegalArgumentException("weightTolerance cannot be negative");
    }

    this.weightTolerance = weightTolerance;
  }

  public void setDiameterTolerance(double diameterTolerance) {
    if (diameterTolerance < 0.0) {
      throw new IllegalArgumentException("diameterTolerance cannot be negative");
    }

    this.diameterTolerance = diameterTolerance;
  }

  public void addProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("product cannot be null");
    }

    storedProducts.get(product.getProductType()).add(product);
  }

  public void addProducts(List<Product> products) {
    for (Product product : products) {
      this.addProduct(product);
    }
  }

  int numProducts(ProductType productType) {
    return storedProducts.get(productType).size();
  }

  CoinType identifyCoin(Coin coin) {
    return CoinType.identifyCoin(coin, this.getWeightTolerance(), this.getDiameterTolerance());
  }

  public void addStoredCoin(Coin coin) {
    if (coin == null) {
      throw new IllegalArgumentException("coin cannot be null");
    }

    CoinType coinType = this.identifyCoin(coin);
    if (coinType != null) {
      storedCoins.get(coinType).push(coin);
    }
  }

  public void addStoredCoins(List<Coin> coins) {
    for (Coin coin : coins) {
      this.addStoredCoin(coin);
    }
  }

  int numStoredCoins(CoinType coinType) {
    return storedCoins.get(coinType).size();
  }

  public void insertCoin(Coin coin) {
    if (coin == null) {
      throw new IllegalArgumentException("coin cannot be null");
    }

    CoinType coinType = this.identifyCoin(coin);
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
      CoinType coinType = this.identifyCoin(coin);
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
