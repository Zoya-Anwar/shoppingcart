package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {
    private HashMap<String, Integer> contents = new HashMap<>();
    private Pricer pricer;
    private int totalPrice = 0;

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
    }
    
    public HashMap<String, Integer> getContents() {
        return contents;
    }
    
    public int getItemCount() {
    	return contents.values()
    				   .stream()
    				   .mapToInt(Integer::intValue)
    				   .sum();	
    }
    
    public int getTotalPrice() {
    	return totalPrice;
    }

    public void addItem(String itemType, int number) {
        if (!contents.containsKey(itemType)) {
            contents.put(itemType, number);
        } else {
            int existing = contents.get(itemType);
            contents.put(itemType, existing + number);
        }
    }

    public void printReceipt() {
        Object[] keys = contents.keySet().toArray();

        for (int i = 0; i < Array.getLength(keys) ; i++) {
            Integer price = pricer.getPrice((String)keys[i]) * contents.get(keys[i]);
            float priceFloat = price / 100;
            String priceString = String.format("€%.2f", priceFloat);

            System.out.println(keys[i] + " - " + contents.get(keys[i]) + " - " + priceString);
        }
        
        
    }

}
