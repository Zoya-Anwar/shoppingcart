package com.xgen.interview;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ShoppingCartTest {

	private Pricer pricer = new Pricer();
	private ShoppingCart sc;
	
	@Before
	public void beforeTests() {
		sc = new ShoppingCart(new Pricer());
	}
	
    @Test
    public void canAddAnItem() {
        sc.addItem("apple", 1);
        assertTrue(sc.getContents().containsKey("apple"));
        assertEquals(1, sc.getItemCount());
    }

    @Test
    public void canAddMoreThanOneItem() {
        sc.addItem("apple", 2);
        assertTrue(sc.getContents().containsKey("apple"));
        assertEquals(2, sc.getItemCount());
    }

    @Test
    public void canAddDifferentItems() {
        sc.addItem("apple", 2);
        sc.addItem("banana", 1);
        
        assertTrue(sc.getContents().containsKey("apple"));
        assertTrue(sc.getContents().containsKey("banana"));
        assertEquals(3, sc.getItemCount());
    }

    @Test
    public void doesntExplodeOnMysteryItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        sc.addItem("crisps", 2);

        assertTrue(sc.getContents().containsKey("crisps"));
        assertEquals(2, sc.getItemCount());
    }
}


