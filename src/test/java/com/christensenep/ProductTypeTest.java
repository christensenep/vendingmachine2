package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProductTypeTest {
  @Test
  public void colaChipsAndCandyExist() {
    assertNotNull(ProductType.COLA);
    assertNotNull(ProductType.CHIPS);
    assertNotNull(ProductType.CANDY);
  }
}
