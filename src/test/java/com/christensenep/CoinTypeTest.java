package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CoinTypeTest {
  private final double EPSILON = 0.00001;
  private final double WEIGHT_TOLERANCE = 0.020;
  private final double DIAMETER_TOLERANCE = 0.20;

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

    assertEquals(CoinType.QUARTER, CoinType.identifyCoin(mockQuarter, 0, 0));
  }

  @Test
  public void identifyDime() {
    Coin mockDime = mock(Coin.class);
    when(mockDime.getWeight()).thenReturn(CoinType.DIME.getWeight());
    when(mockDime.getDiameter()).thenReturn(CoinType.DIME.getDiameter());

    assertEquals(CoinType.DIME, CoinType.identifyCoin(mockDime, 0, 0));
  }

  @Test
  public void identifyNickel() {
    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(CoinType.NICKEL.getWeight());
    when(mockNickel.getDiameter()).thenReturn(CoinType.NICKEL.getDiameter());

    assertEquals(CoinType.NICKEL, CoinType.identifyCoin(mockNickel, 0, 0));
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
  public void identifyHeavyCoin() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight() + WEIGHT_TOLERANCE - EPSILON);
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    assertEquals(CoinType.QUARTER, CoinType.identifyCoin(mockQuarter, WEIGHT_TOLERANCE, 0));
  }

  @Test
  public void failToIdentifyTooHeavyCoin() {
    Coin mockDime = mock(Coin.class);
    when(mockDime.getWeight()).thenReturn(CoinType.DIME.getWeight() + WEIGHT_TOLERANCE + EPSILON);
    when(mockDime.getDiameter()).thenReturn(CoinType.DIME.getDiameter());

    assertEquals(null, CoinType.identifyCoin(mockDime, WEIGHT_TOLERANCE, 0));
  }

  @Test
  public void identifyLightCoin() {
    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(CoinType.NICKEL.getWeight() - WEIGHT_TOLERANCE + EPSILON);
    when(mockNickel.getDiameter()).thenReturn(CoinType.NICKEL.getDiameter());

    assertEquals(CoinType.NICKEL, CoinType.identifyCoin(mockNickel, WEIGHT_TOLERANCE, 0));
  }

  @Test
  public void failToIdentifyTooLightCoin() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight() - WEIGHT_TOLERANCE - EPSILON);
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter());

    assertEquals(null, CoinType.identifyCoin(mockQuarter, WEIGHT_TOLERANCE, 0));
  }

  @Test
  public void identifyBigCoin() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight());
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter() + DIAMETER_TOLERANCE - EPSILON);

    assertEquals(CoinType.QUARTER, CoinType.identifyCoin(mockQuarter, 0, DIAMETER_TOLERANCE));
  }

  @Test
  public void failToIdentifyTooBigCoin() {
    Coin mockDime = mock(Coin.class);
    when(mockDime.getWeight()).thenReturn(CoinType.DIME.getWeight());
    when(mockDime.getDiameter()).thenReturn(CoinType.DIME.getDiameter() + DIAMETER_TOLERANCE + EPSILON);

    assertEquals(null, CoinType.identifyCoin(mockDime, 0, DIAMETER_TOLERANCE));
  }

  @Test
  public void identifySmallCoin() {
    Coin mockNickel = mock(Coin.class);
    when(mockNickel.getWeight()).thenReturn(CoinType.NICKEL.getWeight());
    when(mockNickel.getDiameter()).thenReturn(CoinType.NICKEL.getDiameter() - DIAMETER_TOLERANCE + EPSILON);

    assertEquals(CoinType.NICKEL, CoinType.identifyCoin(mockNickel, 0, DIAMETER_TOLERANCE));
  }

  @Test
  public void failToIdentifyTooSmallCoin() {
    Coin mockQuarter = mock(Coin.class);
    when(mockQuarter.getWeight()).thenReturn(CoinType.QUARTER.getWeight());
    when(mockQuarter.getDiameter()).thenReturn(CoinType.QUARTER.getDiameter() - DIAMETER_TOLERANCE - EPSILON);

    assertEquals(null, CoinType.identifyCoin(mockQuarter, 0, DIAMETER_TOLERANCE));
  }
}
