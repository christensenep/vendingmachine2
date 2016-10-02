package com.christensenep;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MachineTest {
  private Machine machine;

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
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight());
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    assertEquals(0, this.machine.getInsertedValue());
    this.machine.insertCoin(mockQuarter);
    assertEquals(25, this.machine.getInsertedValue());
  }

  @Test
  public void valueIsCorrectWithQuarterAndNickelInserted() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight());
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(CoinType.NICKEL.getWeight());
    when(mockNickel.getDiameter()).thenReturn(CoinType.NICKEL.getDiameter());

    assertEquals(0, this.machine.getInsertedValue());
    this.machine.insertCoin(mockNickel);
    assertEquals(5, this.machine.getInsertedValue());
    this.machine.insertCoin(mockQuarter);
    assertEquals(30, this.machine.getInsertedValue());
  }
}
