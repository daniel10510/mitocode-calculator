package com.mitocode.calculator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculatorTest {

	@Test
    public void testAdd() {
        SimpleCalculatorController sc = new SimpleCalculatorController();
        int num1=2;
        int num2=2;
        String a = sc.add(num1, num2);
        System.out.println(a);
        assertTrue(a.equals(String.format("%d + %d = %d", num1, num2, (4))));
    }
	
	@Test
    public void testSub() {
        SimpleCalculatorController sc = new SimpleCalculatorController();
        int num1=5;
        int num2=2;
        String a = sc.sub(num1, num2);
        assertTrue(a.equals(String.format("%d - %d = %d", num1, num2, (3))));
    }
	
	@Test
    public void testMul() {
        SimpleCalculatorController sc = new SimpleCalculatorController();
        int num1=2;
        int num2=2;
        String a = sc.mul(num1, num2);
        assertTrue(a.equals(String.format("%d x %d = %d", num1, num2, (4))));
    }
	
	@Test
    public void testDiv() {
        SimpleCalculatorController sc = new SimpleCalculatorController();
        int num1=2;
        int num2=2;
        String a = sc.div(num1, num2);
        assertTrue(a.equals(String.format("%d / %d = %d", num1, num2, (1))));
    }
}
