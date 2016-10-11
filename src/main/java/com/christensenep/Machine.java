package com.christensenep;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Stack;

public class Machine {
  private Stack<Coin> insertedCoins = new Stack<Coin>();
  private List<Coin> returnedCoins = new ArrayList<Coin>();
  private List<Product> purchaseTrayContents = new ArrayList<Product>();
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
    if (products == null) {
      throw new IllegalArgumentException("products cannot be null");
    }

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
    if (coins == null) {
      throw new IllegalArgumentException("coins cannot be null");
    }

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

  public List<Product> getPurchaseTrayContents() {
    return purchaseTrayContents;
  }

  public boolean purchase(ProductType productType) {
    boolean success = false;

    if (this.numProducts(productType) > 0 && this.getInsertedValue() >= productType.getValue()) {
      this.purchaseTrayContents.add(this.storedProducts.get(productType).pop());
      success = true;
    }

    return success;
  }

  void makeChange(int changeValue) {
    while (changeValue > 0) {
      this.returnedCoins.add(this.storedCoins.get(CoinType.NICKEL).pop());
      changeValue -= 5;
    }
  }

  boolean exactChangeRequired() {
    boolean exactChangeRequired = false;

    if (this.numProducts(ProductType.CANDY) > 0 || this.numProducts(ProductType.COLA) > 0 || this.numProducts(ProductType.CHIPS) > 0) {
      if (this.numStoredCoins(CoinType.NICKEL) == 0) {
        exactChangeRequired = true;
      }
    }

    if (this.numProducts(ProductType.CHIPS) > 0) {
      if (this.numStoredCoins(CoinType.NICKEL) == 1 && this.numStoredCoins(CoinType.DIME) == 0) {
        exactChangeRequired = true;
      }
    }

    return exactChangeRequired;
  }

  public String getDisplay() {
    String displayString;

    if (this.getInsertedValue() > 0) {
      displayString = Integer.toString(this.getInsertedValue());
    }
    else if (this.exactChangeRequired()) {
      displayString = "EXACT CHANGE ONLY";
    }
    else {
      displayString = "INSERT COIN";
    }

    return displayString;
  }
}
