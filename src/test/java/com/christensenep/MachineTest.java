package com.christensenep;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
}
