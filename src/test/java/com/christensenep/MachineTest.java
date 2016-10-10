package com.christensenep;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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

  private List<Coin> generateMockCoins(int quarters, int dimes, int nickels, int invalids) {
    List<Coin> coins = new ArrayList<Coin>(quarters+dimes+nickels+invalids);

    for (int i = 0; i < quarters; ++i) {
      coins.add(generateMockCoin(CoinType.QUARTER));
    }

    for (int i = 0; i < dimes; ++i) {
      coins.add(generateMockCoin(CoinType.DIME));
    }

    for (int i = 0; i < nickels; ++i) {
      coins.add(generateMockCoin(CoinType.NICKEL));
    }

    for (int i = 0; i < invalids; ++i) {
      coins.add(generateMockCoin(null));
    }

    return coins;
  }

  private Product generateMockProduct(ProductType productType) {
    Product mockProduct = mock(Product.class);

    when(mockProduct.getProductType()).thenReturn(productType);

    return mockProduct;
  }

  private List<Product> generateMockProducts(int colas, int chips, int candies) {
    List<Product> products = new ArrayList<Product>(candies+chips+colas);

    for (int i = 0; i < colas; ++i) {
      products.add(generateMockProduct(ProductType.COLA));
    }

    for (int i = 0; i < chips; ++i) {
      products.add(generateMockProduct(ProductType.CHIPS));
    }

    for (int i = 0; i < candies; ++i) {
      products.add(generateMockProduct(ProductType.CANDY));
    }

    return products;
  }

  private void insertCoins(List<Coin> coins) {
    for (Coin coin : coins) {
      this.machine.insertCoin(coin);
    }
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

  @Test(expected=IllegalArgumentException.class)
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
    List<Coin> coins = generateMockCoins(2,1,0,1);
    this.insertCoins(coins);

    List<Coin> returnedCoins = this.machine.getReturnedCoins();
    assertTrue(coins.contains(returnedCoins.get(0)));
    assertEquals(1, returnedCoins.size());

    this.machine.ejectCoins();
    returnedCoins = this.machine.getReturnedCoins();
    assertTrue(returnedCoins.containsAll(coins));
    assertEquals(coins.size(), returnedCoins.size());
  }

  @Test
  public void hasNoProductsInitially() {
    for (ProductType productType : ProductType.values()) {
      assertEquals(0, this.machine.numProducts(productType));
    }
  }

  @Test
  public void hasOneCandyAfterAddingOne() {
    this.machine.addProduct(generateMockProduct(ProductType.CANDY));
    assertEquals(1, this.machine.numProducts(ProductType.CANDY));
  }

  @Test(expected=IllegalArgumentException.class)
  public void addingNullProductThrowsException() {
    this.machine.addProduct(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void addingNullProductListThrowsException() {
    this.machine.addProducts(null);
  }

  @Test
  public void hasProperNumberOfEachProductAfterAddingAListOfProducts() {
    List<Product> products = generateMockProducts(1,0,2);
    this.machine.addProducts(products);
    assertEquals(2, this.machine.numProducts(ProductType.CANDY));
    assertEquals(1, this.machine.numProducts(ProductType.COLA));
    assertEquals(0, this.machine.numProducts(ProductType.CHIPS));
  }

  @Test
  public void hasNoStoredCoinsInitially() {
    for (CoinType coinType : CoinType.values()) {
      assertEquals(0, this.machine.numStoredCoins(coinType));
    }
  }

  @Test
  public void hasOneQuarterAfterAddingOne() {
    Coin mockQuarter = generateMockCoin(CoinType.QUARTER);
    this.machine.addStoredCoin(mockQuarter);
    assertEquals(1, this.machine.numStoredCoins(CoinType.QUARTER));
  }

  @Test
  public void hasProperNumberOfCoinsAfterAddingListOfCoins() {
    List<Coin> coins = generateMockCoins(1,2,0,0);
    this.machine.addStoredCoins(coins);
    assertEquals(2, this.machine.numStoredCoins(CoinType.DIME));
    assertEquals(1, this.machine.numStoredCoins(CoinType.QUARTER));
    assertEquals(0, this.machine.numStoredCoins(CoinType.NICKEL));
  }

  @Test(expected=IllegalArgumentException.class)
  public void addingNullStoredCoinThrowsException() {
    this.machine.addStoredCoin(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void addingNullStoredCoinListThrowsException() {
    this.machine.addStoredCoins(null);
  }

  @Test
  public void weightToleranceIsZeroInitially() {
    assertEquals(0, this.machine.getWeightTolerance(), 0);
  }

  @Test
  public void diameterToleranceIsZeroInitially() {
    assertEquals(0, this.machine.getDiameterTolerance(), 0);
  }

  @Test
  public void setWeightToleranceToPositiveValue() {
    final double TOLERANCE = 0.020;
    this.machine.setWeightTolerance(TOLERANCE);
    assertEquals(TOLERANCE, this.machine.getWeightTolerance(), 0);
  }

  @Test
  public void setDiameterToleranceToPositiveValue() {
    final double TOLERANCE = 0.20;
    this.machine.setDiameterTolerance(TOLERANCE);
    assertEquals(TOLERANCE, this.machine.getDiameterTolerance(), 0);
  }

  @Test(expected=IllegalArgumentException.class)
  public void settingNegativeWeightToleranceThrowsException() {
    this.machine.setWeightTolerance(-0.5);
  }

  @Test(expected=IllegalArgumentException.class)
  public void settingNegativeDiameterToleranceThrowsException() {
    this.machine.setDiameterTolerance(-0.2);
  }

  @Test
  public void setWeightToleranceToZero() {
    this.machine.setWeightTolerance(0);
    assertEquals(0, this.machine.getWeightTolerance(), 0);
  }

  @Test
  public void setDiameterToleranceToZero() {
    this.machine.setDiameterTolerance(0);
    assertEquals(0, this.machine.getDiameterTolerance(), 0);
  }

  @Test
  public void emptyMachineDisplaysInsertCoin() {
    assertEquals("INSERT COIN", this.machine.getDisplay());
  }

  @Test
  public void machineWithChipsDisplaysExactChangeOnly() {
    this.machine.addProduct(generateMockProduct(ProductType.CHIPS));
    assertEquals("EXACT CHANGE ONLY", this.machine.getDisplay());
  }

  @Test
  public void emptyMachineDoesNotRequireExactChange() {
    assertEquals(false, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithChipsRequiresExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CHIPS));
    assertEquals(true, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithColaRequiresExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.COLA));
    assertEquals(true, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithCandyRequiresExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CANDY));
    assertEquals(true, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithColaAndNickelDoesNotRequireExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.COLA));
    this.machine.addStoredCoin(generateMockCoin(CoinType.NICKEL));
    assertEquals(false, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithCandyAndNickelDoesNotRequireExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CANDY));
    this.machine.addStoredCoin(generateMockCoin(CoinType.NICKEL));
    assertEquals(false, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithChipsAndNickelRequiresExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CHIPS));
    this.machine.addStoredCoin(generateMockCoin(CoinType.NICKEL));
    assertEquals(true, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithChipsAndDimeRequiresExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CHIPS));
    this.machine.addStoredCoin(generateMockCoin(CoinType.DIME));
    assertEquals(true, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithChipsNickelAndDimeDoesNotRequireExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CHIPS));
    this.machine.addStoredCoins(generateMockCoins(0,1,1,0));
    assertEquals(false, this.machine.exactChangeRequired());
  }

  @Test
  public void machineWithChipsAndTwoNickelsDoesNotRequireExactChange() {
    this.machine.addProduct(generateMockProduct(ProductType.CHIPS));
    this.machine.addStoredCoins(generateMockCoins(0,0,2,0));
    assertEquals(false, this.machine.exactChangeRequired());
  }

  @Test
  public void displayCurrentValueWithCoinsInserted() {
    this.machine.insertCoin(generateMockCoin(CoinType.QUARTER));
    this.machine.insertCoin(generateMockCoin(CoinType.DIME));
    assertEquals("35", this.machine.getDisplay());
  }

  @Test
  public void purchaseFailsWithNoCoins() {
    this.machine.addProducts(generateMockProducts(1,1,1));
    assertEquals(false, this.machine.purchase(ProductType.CHIPS));
    assertEquals(0, this.machine.getPurchaseTrayContents().size());
  }

  @Test
  public void purchaseFailsWithInsufficentCoins() {
    insertCoins(generateMockCoins(0,4,1,0));

    this.machine.addProducts(generateMockProducts(1,1,1));

    assertEquals(false, this.machine.purchase(ProductType.CHIPS));
    assertEquals(0, this.machine.getPurchaseTrayContents().size());
  }

  @Test
  public void purchaseFailsWithExactCoinsIfProductSoldOut() {
    insertCoins(generateMockCoins(2,0,0,0));

    assertEquals(false, this.machine.purchase(ProductType.CHIPS));
    assertEquals(0, this.machine.getPurchaseTrayContents().size());
  }

  @Test
  public void purchaseSucceedsWithExactCoins() {
    insertCoins(generateMockCoins(2,0,0,0));
    this.machine.addProducts(generateMockProducts(1,1,1));

    assertEquals(true, this.machine.purchase(ProductType.CHIPS));
    assertEquals(1, this.machine.getPurchaseTrayContents().size());
  }

}
