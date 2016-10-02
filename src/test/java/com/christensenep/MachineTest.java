package com.christensenep;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MachineTest {
  private Machine machine;

  private Coin generateMockCoin(CoinType coinType) {
    Coin mockCoin = mock(Coin.class);

    if (coinType != null) {
      when(mockCoin.getWeight()).thenReturn(coinType.getWeight());
      when(mockCoin.getDiameter()).thenReturn(coinType.getDiameter());
    }
    else {
      when(mockCoin.getWeight()).thenReturn(10000.0);
      when(mockCoin.getDiameter()).thenReturn(10000.0);
    }

    return mockCoin;
  }

  @Before
  public void initialize() {
    this.machine = new Machine();
  }

  @Test
  public void valueIsZeroWithNoCoinsInserted() {
    assertEquals(0, this.machine.getInsertedValue());
  }

  @Test
  public void valueIsCorrectWithQuarterInserted() {
    Coin mockQuarter = generateMockCoin(CoinType.QUARTER);

    assertEquals(0, this.machine.getInsertedValue());
    this.machine.insertCoin(mockQuarter);
    assertEquals(25, this.machine.getInsertedValue());
  }

  @Test
  public void valueIsCorrectWithQuarterAndNickelInserted() {
    Coin mockQuarter = generateMockCoin(CoinType.QUARTER);
    Coin mockNickel = generateMockCoin(CoinType.NICKEL);

    assertEquals(0, this.machine.getInsertedValue());
    this.machine.insertCoin(mockNickel);
    assertEquals(5, this.machine.getInsertedValue());
    this.machine.insertCoin(mockQuarter);
    assertEquals(30, this.machine.getInsertedValue());
  }

  @Test
  public void valueIsCorrectWithQuarterAndNickelInsertedThenEjected() {
    Coin mockQuarter = generateMockCoin(CoinType.QUARTER);
    Coin mockNickel = generateMockCoin(CoinType.NICKEL);

    assertEquals(0, this.machine.getInsertedValue());
    this.machine.insertCoin(mockNickel);
    assertEquals(5, this.machine.getInsertedValue());
    this.machine.insertCoin(mockQuarter);
    assertEquals(30, this.machine.getInsertedValue());
    this.machine.ejectCoins();
    assertEquals(0, this.machine.getInsertedValue());
  }

  @Test(expected=NullPointerException.class)
  public void insertingNullCoinThrowsException() {
    this.machine.insertCoin(null);
  }

  @Test
  public void valueStaysZeroWhenInsertingInvalidCoin() {
    Coin mockInvalidCoin = generateMockCoin(null);

    assertEquals(0, this.machine.getInsertedValue());
    this.machine.insertCoin(mockInvalidCoin);
    assertEquals(0, this.machine.getInsertedValue());
  }

  @Test
  public void noReturnedCoinsInitially() {
    List<Coin> returnedCoins = this.machine.getReturnedCoins();
    assertEquals(0, returnedCoins.size());
  }

  @Test
  public void quarterReturnedWhenEjected() {
    Coin mockQuarter = generateMockCoin(CoinType.QUARTER);

    this.machine.insertCoin(mockQuarter);
    this.machine.ejectCoins();
    List<Coin> returnedCoins = this.machine.getReturnedCoins();
    assertEquals(mockQuarter, returnedCoins.get(0));
    assertEquals(1, returnedCoins.size());
  }

  @Test
  public void invalidCoinReturnedWhenInserted() {
    Coin mockInvalidCoin = generateMockCoin(null);

    this.machine.insertCoin(mockInvalidCoin);
    List<Coin> returnedCoins = this.machine.getReturnedCoins();
    assertEquals(mockInvalidCoin, returnedCoins.get(0));
    assertEquals(1, returnedCoins.size());
  }

  @Test
  public void returnedCoinsCorrectWhenMultipleCoinsInsertedAndEjected() {
    List<Coin> coins = Arrays.asList(generateMockCoin(CoinType.QUARTER), generateMockCoin(CoinType.DIME), generateMockCoin(null), generateMockCoin(CoinType.QUARTER));

    for (Coin coin : coins) {
      this.machine.insertCoin(coin);
    }

    List<Coin> returnedCoins = this.machine.getReturnedCoins();
    assertEquals(coins.get(2), returnedCoins.get(0));
    assertEquals(1, returnedCoins.size());

    this.machine.ejectCoins();
    returnedCoins = this.machine.getReturnedCoins();
    assertTrue(returnedCoins.containsAll(coins));
    assertEquals(coins.size(), returnedCoins.size());
  }

  @Test
  public void hasNoCandyInitially() {
    assertEquals(0, this.machine.numProducts(ProductType.CANDY));
  }

  @Test
  public void hasOneCandyAfterAddingOne() {
    this.machine.addProducts(ProductType.CANDY, 1);
    assertEquals(1, this.machine.numProducts(ProductType.CANDY));
  }

  @Test(expected=NullPointerException.class)
  public void exceptionThrownWhenAddingNullProductType() {
    this.machine.addProducts(null, 1);
  }

  @Test(expected=IllegalArgumentException.class)
  public void exceptionThrownWhenAddingNegativeNumberOfProducts() {
    this.machine.addProducts(ProductType.CANDY, -1);
  }
}
