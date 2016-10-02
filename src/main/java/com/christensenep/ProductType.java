package com.christensenep;

public enum ProductType {
  COLA(100),
  CHIPS(50),
  CANDY(65);

  private final int value;

  ProductType(int value) {
    this.value = value;
  }

  public int getValue() { return this.value; }
}
