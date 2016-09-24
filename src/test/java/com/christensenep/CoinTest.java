package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinTest {
  @Test
  public void getCoinWeight() {
    Coin coin = new Coin(10.0);
    assertEquals(10.0, coin.getWeight(), 0);
  }
}