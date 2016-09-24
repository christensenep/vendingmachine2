package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CoinTypeTest {
  @Test
  public void quartersNickelsAndDimesExist() {
    assertNotNull(CoinType.valueOf("QUARTER"));
    assertNotNull(CoinType.valueOf("DIME"));
    assertNotNull(CoinType.valueOf("NICKEL"));
  }
}
