package com.christensenep;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CoinTypeTest {
  @Before
  public void initialize() {
    CoinType.setWeightTolerance(0.020);
  }

  @Test
  public void quartersNickelsAndDimesExist() {
    assertNotNull(CoinType.valueOf("QUARTER"));
    assertNotNull(CoinType.valueOf("DIME"));
    assertNotNull(CoinType.valueOf("NICKEL"));
  }

  @Test
  public void identifyQuarter() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight());
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    assertEquals(CoinType.QUARTER, CoinType.identifyCoin(mockQuarter));
  }

  @Test
  public void identifyDime() {
    Coin mockDime = mock(Coin.class);
    when(mockDime.getWeight()).thenReturn(CoinType.DIME.getWeight());
    when(mockDime.getDiameter()).thenReturn(CoinType.DIME.getDiameter());

    assertEquals(CoinType.DIME, CoinType.identifyCoin(mockDime));
  }

  @Test
  public void identifyNickel() {
    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(CoinType.NICKEL.getWeight());
    when(mockNickel.getDiameter()).thenReturn(CoinType.NICKEL.getDiameter());

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

  @Test
  public void getCoinTypeWeight() {
    assertNotEquals(0, CoinType.QUARTER.getWeight(), 0);
  }

  @Test
  public void getCoinTypeDiameter() {
    assertNotEquals(0, CoinType.QUARTER.getDiameter(), 0);
  }

  @Test
  public void getAndSetWeightTolerance() {
    CoinType.setWeightTolerance(5.50);
    assertEquals(5.50, CoinType.getWeightTolerance(), 0);
  }

  @Test
  public void getAndSetDiameterTolerance() {
    CoinType.setDiameterTolerance(3.30);
    assertEquals(3.30, CoinType.getDiameterTolerance(), 0);
  }

  @Test
  public void identifyHeavyCoin() {
    double epsilon = 0.00001;
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight() + CoinType.getWeightTolerance() - epsilon);
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    assertEquals(CoinType.QUARTER, CoinType.identifyCoin(mockQuarter));
  }

  @Test
  public void failToIdentifyTooHeavyCoin() {
    double epsilon = 0.00001;
    Coin mockDime = mock(Coin.class);
    when(mockDime.getWeight()).thenReturn(CoinType.DIME.getWeight() + CoinType.getWeightTolerance() + epsilon);
    when(mockDime.getDiameter()).thenReturn(CoinType.DIME.getDiameter());

    assertEquals(null, CoinType.identifyCoin(mockDime));
  }

  @Test
  public void identifyLightCoin() {
    double epsilon = 0.00001;
    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(CoinType.NICKEL.getWeight() - CoinType.getWeightTolerance() + epsilon);
    when(mockNickel.getDiameter()).thenReturn(CoinType.NICKEL.getDiameter());

    assertEquals(CoinType.NICKEL, CoinType.identifyCoin(mockNickel));
  }

  @Test
  public void failToIdentifyTooLightCoin() {
    double epsilon = 0.00001;
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight() - CoinType.getWeightTolerance() - epsilon);
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    assertEquals(null, CoinType.identifyCoin(mockQuarter));
  }
}
