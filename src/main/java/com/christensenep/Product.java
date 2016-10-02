package com.christensenep;

public class Product {
  private ProductType productType;

  public Product(ProductType productType) {
    this.productType = productType;
  }

  public ProductType getProductType() { return this.productType; }
}
