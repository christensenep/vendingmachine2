package com.christensenep;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Stack;

public class Machine {
  private List<Coin> insertedCoins = new ArrayList<Coin>();
  private List<Coin> returnedCoins = new ArrayList<Coin>();
  private List<Product> purchaseTrayContents = new ArrayList<Product>();
  private EnumMap<CoinType, Stack<Coin>> storedCoins;
  private EnumMap<ProductType, Stack<Product>> storedProducts;

  private double diameterTolerance = 0.0;
  private double weightTolerance = 0.0;

  private String tempMessage = null;

  public Machine() {
    initStoredProductsMap();
    initStoredCoinsMap();
  }

  private void initStoredProductsMap() {
    this.storedProducts = new EnumMap<ProductType, Stack<Product>>(ProductType.class);
    for (ProductType productType : ProductType.values()) {
      this.storedProducts.put(productType, new Stack<Product>());
    }
  }

  private void initStoredCoinsMap() {
    this.storedCoins = new EnumMap<CoinType, Stack<Coin>>(CoinType.class);
    for (CoinType coinType : CoinType.values()) {
      this.storedCoins.put(coinType, new Stack<Coin>());
    }
  }

  public List<Coin> getReturnedCoins() { return this.returnedCoins; }
  public List<Product> getPurchaseTrayContents() { return this.purchaseTrayContents; }

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

    this.storedProducts.get(product.getProductType()).push(product);
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
    return this.storedProducts.get(productType).size();
  }

  boolean hasProduct(ProductType productType) {
    return this.numProducts(productType) > 0;
  }

  boolean hasAnyProducts() {
    boolean result = false;
    for (ProductType productType : ProductType.values()) {
      if (this.hasProduct(productType)) {
        result = true;
        break;
      }
    }

    return result;
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
      this.storedCoins.get(coinType).push(coin);
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
    return this.storedCoins.get(coinType).size();
  }

  public void insertCoin(Coin coin) {
    if (coin == null) {
      throw new IllegalArgumentException("coin cannot be null");
    }

    CoinType coinType = this.identifyCoin(coin);
    if (coinType != null) {
      this.insertedCoins.add(coin);
    }
    else {
      this.returnedCoins.add(coin);
    }
  }

  int getInsertedValue() {
    int insertedValue = 0;

    for (Coin coin : this.insertedCoins) {
      CoinType coinType = this.identifyCoin(coin);
      if (coinType != null) {
        insertedValue += coinType.getValue();
      }
    }

    return insertedValue;
  }

  public void ejectCoins() {
    this.returnedCoins.addAll(this.insertedCoins);
    this.insertedCoins.clear();
  }

  void storeInsertedCoins() {
    this.addStoredCoins(this.insertedCoins);
    this.insertedCoins.clear();
  }

  public boolean purchase(ProductType productType) {
    boolean successful = false;
    int excessValue = this.getInsertedValue() - productType.getValue();

    if (!this.hasProduct(productType)) {
      this.tempMessage = "SOLD OUT";
    }
    else if (excessValue > 0 && this.exactChangeRequired()) {
      this.tempMessage = "EXACT CHANGE ONLY";
    }
    else if (excessValue < 0) {
      this.tempMessage = "PRICE";
    }
    else
    {
      this.storeInsertedCoins();
      this.makeChange(excessValue);
      this.purchaseTrayContents.add(this.storedProducts.get(productType).pop());
      this.tempMessage = "THANK YOU";
      successful = true;
    }

    return successful;
  }

  void makeChange(int changeValue) {
    if (changeValue % 5 != 0) {
      throw new IllegalArgumentException("changeValue must be divisible by 5");
    }
    for (CoinType coinType : CoinType.values()) {
      while (changeValue >= coinType.getValue() && this.numStoredCoins(coinType) > 0) {
        this.returnedCoins.add(this.storedCoins.get(coinType).pop());
        changeValue -= coinType.getValue();
      }
    }
  }

  boolean exactChangeRequired() {
    boolean exactChange = false;

    if (this.hasAnyProducts()) {
      if (this.numStoredCoins(CoinType.NICKEL) == 0) {
        exactChange = true;
      }
    }

    if (this.hasProduct(ProductType.CHIPS)) {
      if (this.numStoredCoins(CoinType.NICKEL) < 2 && this.numStoredCoins(CoinType.DIME) == 0) {
        exactChange = true;
      }
    }

    return exactChange;
  }

  public String getDisplay() {
    String displayString;

    if (this.tempMessage != null) {
      displayString = this.tempMessage;
      this.tempMessage = null;
    }
    else if (this.getInsertedValue() > 0) {
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
