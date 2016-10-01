package com.christensenep;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CoinTypeTest {
  @Test
  public void quartersNickelsAndDimesExist() {
    assertNotNull(CoinType.valueOf("QUARTER"));
    assertNotNull(CoinType.valueOf("DIME"));
    assertNotNull(CoinType.valueOf("NICKEL"));
  }

  @Test
  public void identifyQuarter() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(5.670);
    when(mockQuarter.getDiameter()).thenReturn(24.26);

    assertEquals(CoinType.QUARTER, CoinType.identifyCoin(mockQuarter));
  }

  @Test
  public void identifyDime() {
    Coin mockDime = mock(Coin.class);
    when(mockDime.getWeight()).thenReturn(2.268);
    when(mockDime.getDiameter()).thenReturn(17.91);

    assertEquals(CoinType.DIME, CoinType.identifyCoin(mockDime));
  }

  @Test
  public void identifyNickel() {
    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(5.000);
    when(mockNickel.getDiameter()).thenReturn(21.21);

    assertEquals(CoinType.NICKEL, CoinType.identifyCoin(mockNickel));
  }

  @Test
  public void getQuarterValue() {
    assertEquals(25, CoinType.QUARTER.getValue());
  }

  @Test
  public void getDimeValue() {
    assertEquals(10, CoinType.DIME.getValue());
  }

  @Test
  public void getNickelValue() {
    assertEquals(5, CoinType.NICKEL.getValue());
  }
}
