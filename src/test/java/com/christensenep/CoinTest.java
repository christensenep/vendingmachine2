package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinTest {
  @Test
  public void getCoinWeight() {
    Coin coin = new Coin(10.0, 15.0);
    assertEquals(10.0, coin.getWeight(), 0);
  }

  @Test
  public void getCoinDiameter() {
    Coin coin = new Coin(10.0, 15.0);
    assertEquals(15.0, coin.getDiameter(), 0);
  }
}
