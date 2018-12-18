/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.NumberFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.ValidationException;

/**
 *
 * @author Jennifer Carlston
 */
public class Product //extends Item 
{
    private Integer _ID = 0;
    private String _name;
    private Double _price;
    private Integer _inStock = 0;
    private Integer _min;
    private Integer _max;


    /**
     *
     */
    public Product() { }
    
    /**
     *
     * @param id
     * @param name
     * @param price
     * @param instock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int instock, int min, int max)
    {
        //super(id, name, price, instock, min, max);
        _ID = id;
        _name = clean(name);
        _price = price;
        _inStock = instock;
        _min = min;
        _max = max;
    }

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param instock
     * @param min
     * @param max
     * @param parts
     */
    public Product(int id, String name, double price, int instock, int min, int max, ObservableList<Part> parts)
    {
        //super(id, name, price, instock, min, max);
        this(id, name, price, instock, min, max);
        
        _associatedParts = parts;
    }

    private ObservableList<Part> _associatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param newProductID
     */
    public void setProductID(int newProductID) {
        _ID = newProductID;
    }
    public void setProductID(String newProductID) {
        _ID = tryParseInt(newProductID);
    }
    /**
     *
     * @return
     */
    public int getProductID() {
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

    /**
     *
     * @param newPrice
     */
    public void setPrice(String newPrice) {
        _price = tryParseDouble(clean(newPrice.replace("$", "")));
    }

    /**
     *
     * @return
     */
    public double getPrice() {
        return _price;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param newInStock
     */
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

    /**
     *
     * @param newMin
     */
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

    /**
     *
     * @param newMax
     */
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
     * @return
     */
    public String getItemType() {
        return "Product";
    }
    /**
     *
     * @return
     */
    public ObservableList<Part> getAssociatedParts() {
        return _associatedParts;
    }

    /**
     *
     * @return
     */
    public int getCountAssociatedParts() {
        return _associatedParts.size();
    }

    /**
     *
     * @param partToAdd
     */
    public void addAssociatedPart(Part partToAdd) {
        _associatedParts.add(partToAdd);
    }


    /**
     *
     * @param partToRemove
     * @throws ValidationException
     */
    public void removeAssociatedPart(Part partToRemove) throws ValidationException {
        //if (getCountAssociatedParts() > 1)
            _associatedParts.remove(partToRemove);
        //else
        //    throw new ValidationException("Products must have at least one part.");
    }
    
    /**
     *
     */
    public void removeAllAssociatedParts() {
        _associatedParts = FXCollections.observableArrayList();
    }

    /**
     *
     * @param partIDToLookup
     * @return
     */
    public Part lookupAssociatedPart(int partIDToLookup) {
        for (Part p : _associatedParts) {
            if (p.getPartID() == partIDToLookup) {
                return p;
            }
        }

        return null;
    }

    /**
     *
     * @return
     * @throws ValidationException
     */
    public boolean isValid() throws ValidationException {

        double totalPartsPrice = 0.00;

        for (Part p : getAssociatedParts()) {
            totalPartsPrice += p.getPrice();
        }

        // a product must have at least one part
        /*if (getCountAssociatedParts() < 1) {
            throw new ValidationException("The product must contain at least 1 part.");
        }*/

        // the sum of parts must be less than the price of the product
        if (totalPartsPrice >= getPrice()) {
            throw new ValidationException("The product price must be greater than total cost of associated parts.");
        }

        //return super.isValid();
        
        if (getProductID() <= 0) {
            throw new ValidationException("{ID} must be greater than 0.");
        }

        if (getName().length() == 0) {
            throw new ValidationException("{Name} cannot be empty.");
        }

        if (getInStock() < 0) {
            throw new ValidationException("{Inv} for this item cannot be less than zero.");
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
     * @param value
     * @return
     */
    public static Integer tryParseInt(String value) {
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
    public static Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
