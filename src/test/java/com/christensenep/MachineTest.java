package com.christensenep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MachineTest {
  @Test
  public void valueIsZeroWithNoCoinsInserted() {
    Machine machine = new Machine();
    assertEquals(0, machine.getInsertedValue());
  }
}
