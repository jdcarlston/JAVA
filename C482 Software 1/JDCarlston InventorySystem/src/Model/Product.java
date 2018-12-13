/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.ValidationException;

/**
 *
 * @author Jennifer Carlston
 */
public class Product extends Item {

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
        super(id, name, price, instock, min, max);
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
        super(id, name, price, instock, min, max);
        
        _associatedParts = parts;
    }

    private ObservableList<Part> _associatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param newProductID
     */
    public void setProductID(int newProductID) {
        setID(newProductID);
    }

    /**
     *
     * @return
     */
    public int getProductID() {
        return getID();
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
     * @throws ValidationException
     */
    public void addAssociatedPart(Part partToAdd) throws ValidationException {
        partToAdd.isValid();
        _associatedParts.add(partToAdd);
    }

    /**
     *
     * @param partToRemove
     * @throws ValidationException
     */
    public void removeAssociatedPart(Part partToRemove) throws ValidationException {
        if (getCountAssociatedParts() > 1)
            _associatedParts.remove(partToRemove);
        else
            throw new ValidationException("Products must have at least one part.");
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
            if (p.getID() == partIDToLookup) {
                return p;
            }
        }

        return null;
    }

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
        if (totalPartsPrice > getPrice()) {
            throw new ValidationException("The product price must be greater than total cost of associated parts.");
        }

        return super.isValid();
    }
}
