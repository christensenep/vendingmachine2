package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {
  @Test
  public void getProductType() {
    Product product = new Product(ProductType.CANDY);
    assertEquals(ProductType.CANDY, product.getProductType());
  }
}
