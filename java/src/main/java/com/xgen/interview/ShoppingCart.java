package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * @author Zoya Anwar, James Broadhead
 *
 */

public class ShoppingCart implements IShoppingCart {
    private LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
    private Pricer pricer;
    private int totalPrice = 0;
    private Properties config = new Properties();

    /**
     * Constructor for ShoppingCart
     * @param propertiesPath	the class path of the properties file
     */
    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
        this.loadProperties("/config.properties");
    }
    
    /**
     * Constructor for ShoppingCart
     * @param pricer			can call pricer to receive prices of contents
     * @param propertiesPath	the class path of the properties file
     */
    public ShoppingCart(Pricer pricer, String propertiesPath) {
        this.pricer = pricer;
        this.loadProperties(propertiesPath);
    }
    
    /**
    * Loads the user configuration file. 
    * <p>
    * The user configuration file should be stored within main/resources
    * and the propertiesPath should follow the format "/--"
    *
    * @param	propertiesPath	the class path of the properties file. 
    */
    private void loadProperties(String propertiesPath) {
        try {
            config.load(this.getClass().getResourceAsStream(propertiesPath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
    * Returns an insertion order linkedhashmap of the contents
    * of the shopping cart and the quantities of items
    *
    * @return	the contents of the shopping cart
    */
    public LinkedHashMap<String, Integer> getContents() {
        return contents;
    }

    /**
    * Returns the total number of items within the cart
    * by summing all the values in contents
    *
    * @return	total number of shopping cart items 
    */
    public int getItemCount() {
    	return contents.values()
    				   .stream()
    				   .mapToInt(Integer::intValue)
    				   .sum();	
    }

    /**
    * Returns the total price of all items within the cart.
    *
    * @return	total price 
    */
    public int getTotalPrice() {
    	return totalPrice;
    }

    /**
    * Adds n items of a single type to the shopping cart.
    * Sums running total price.
    *
    * @param	number		the quantity of items being added
    * @param	itemType	the type of item being added
    */
    public void addItem(String itemType, int number) {
        if (!contents.containsKey(itemType)) {
            contents.put(itemType, number);
        } else {
            int existing = contents.get(itemType);
            contents.put(itemType, existing + number);
        }
        totalPrice = totalPrice + (pricer.getPrice(itemType)*number);
    }

    /**
    * Prints a shopping cart receipt to the command line using user 
    * configurations for separator and price first or default if not present
    */
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
