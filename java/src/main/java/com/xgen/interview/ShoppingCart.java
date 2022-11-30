package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {
    private LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
    private Pricer pricer;
    private int totalPrice = 0;
    private Properties config = new Properties();

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
        this.loadProperties("/config.properties");
    }
    
    public ShoppingCart(Pricer pricer, String propertiesPath) {
        this.pricer = pricer;
        this.loadProperties(propertiesPath);
    }
    
    private void loadProperties(String propertiesPath) {
        try {
            config.load(this.getClass().getResourceAsStream(propertiesPath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public LinkedHashMap<String, Integer> getContents() {
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
        totalPrice = totalPrice + (pricer.getPrice(itemType)*number);
    }

    public void printReceipt() {
    	String separator = " " + config.getProperty("separator", "-") + " ";
        Object[] keys = contents.keySet().toArray();
        
        for (int i = 0; i < Array.getLength(keys) ; i++) {
            Integer price = pricer.getPrice((String)keys[i]) * contents.get(keys[i]);
            float priceFloat = price / 100;
            String priceString = String.format("€%.2f", priceFloat);
        	if (config.getProperty("priceFirst", "False").equals("False")) {
                System.out.println(keys[i] + separator + contents.get(keys[i]) + separator + priceString);
        	}
            else {
                System.out.println(priceString + separator + keys[i] + separator + contents.get(keys[i]));
            }
        }
        
        float totalPriceFloat = totalPrice / 100;
        String totalPriceString = String.format("€%.2f", totalPriceFloat);
        if (config.getProperty("priceFirst", "False").equals("False")) { 
            System.out.println("Total" + separator + totalPriceString);
        } 
        else {
            System.out.println(totalPriceString + separator + "Total");
        }
    }

}
