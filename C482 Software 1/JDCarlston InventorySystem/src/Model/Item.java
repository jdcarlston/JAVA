/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.NumberFormat;
import javax.xml.bind.ValidationException;

/**
 *
 * @author Jennifer Carlston
 */
public abstract class Item {

    private Integer _ID = 0;
    private String _name;
    private Double _price;
    private Integer _inStock = 0;
    private Integer _min;
    private Integer _max;

    /**
     *
     */
    public Item() {

    }

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param instock
     * @param min
     * @param max
     */
    public Item(int id, String name, double price, int instock, int min, int max) {
        _ID = id;
        _name = clean(name);
        _price = price;
        _inStock = instock;
        _min = min;
        _max = max;
    }

    /**
     *
     * @param newID
     */
    protected void setID(Integer newID) {
        _ID = newID;
    }

    /**
     *
     * @return
     */
    protected int getID() {
        return _ID;
    }

    /**
     *
     * @param newName
     */
    public void setName(String newName) {
        _name = clean(newName);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return _name;
    }

    /**
     *
     * @param newPrice
     */
    public void setPrice(Double newPrice) {
        _price = newPrice;
    }
    public void setPrice(String newPrice) {
        _price = tryParseDouble(clean(newPrice));
    }

    /**
     *
     * @return
     */
    public double getPrice() {
        return _price;
    }

    public String getPriceAsString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String f = formatter.format(_price);
        return f;
    }
    /**
     *
     * @param newInStock
     */
    public void setInStock(Integer newInStock) {
        _inStock = newInStock;
    }
    public void setInStock(String newInStock) {
        _inStock = tryParseInt(clean(newInStock));
    }

    /**
     *
     * @return
     */
    public int getInStock() {
        return _inStock;
    }

    /**
     *
     * @param newMin
     */
    public void setMin(Integer newMin) {
        _min = newMin;
    }
    public void setMin(String newMin) {
        _min = tryParseInt(clean(newMin));
    }
    
    /**
     *
     * @return
     */
    public int getMin() {
        return _min;
    }

    /**
     *
     * @param newMax
     */
    public void setMax(Integer newMax) {
        _max = newMax;
    }
    public void setMax(String newMax) {
        _max = tryParseInt(clean(newMax));
    }
    
    /**
     *
     * @return
     */
    public int getMax() {
        return _max;
    }

    /**
     *
     * @param stringToClean
     * @return
     */
    public static String clean(String stringToClean) {
        String cleanString = stringToClean.trim();

        //Add regex pattern cleaning if necessary
        return cleanString;
    }

    /**
     *
     * @return
     * @throws ValidationException
     */
    public boolean isValid() throws ValidationException {
        if (getID() <= 0) {
            throw new ValidationException("{ID} must be greater than 0.");
        }

        if (getName().length() == 0) {
            throw new ValidationException("{Name} cannot be empty.");
        }

        if (getInStock() < 0) {
            throw new ValidationException("{In Stock} for this item cannot be less than zero.");
        }

        if (getPrice() < 0) {
            throw new ValidationException("{Price} must be greater than $0");
        }

        if (getMin() < 0) {
            throw new ValidationException("Minimum {Inv} Level must be greater than 0.");
        }

        if (getMin() > getMax()) {
            throw new ValidationException("Minimum {Inv} Level must be less than {Max}.");
        }

        if (getInStock() < getMin() || getInStock() > getMax()) {
            throw new ValidationException("Current {Inv} Level must be between the {Min} and {Max}.");
        }

        return true;
    }
    
    /**
     *
     * @param value
     * @return
     */
    protected static Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     *
     * @param value
     * @return
     */
     static Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

}
