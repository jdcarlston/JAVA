/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.ValidationException;

/**
 *
 * @author Jennifer Carlston
 */
public class Inventory {

    private final static ObservableList<Part> _allParts = FXCollections.observableArrayList();
    private final static ObservableList<Product> _allProducts = FXCollections.observableArrayList();

    /**
     *
     */
    public Inventory() {
    }

    //Inventory Part Methods

    /**
     *
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return _allParts;
    }

    //Inventory Product Methods

    /**
     *
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return _allProducts;
    }

    /**
     *
     * @return
     */
    public static int getCountParts() {
        return _allParts.size();
    }

    /**
     *
     * @return
     */
    public static int getCountProducts() {
        return _allProducts.size();
    }

    /**
     *
     * @return
     */
    public static int getNewPartID() {
        return getCountParts() + 1;
    }

    /**
     *
     * @return
     */
    public static int getNewProductID() {
        return getCountProducts() + 1;
    }

    /**
     *
     * @param part
     */
    public static void addPart(Part part) {
        _allParts.add(part);
    }

    /**
     *
     * @param productToAdd
     */
    public static void addProduct(Product productToAdd) {
        _allProducts.add(productToAdd);
    }

    /**
     *
     * @param partID
     * @return
     * @throws ValidationException
     */
    public static boolean deletePart(int partID) throws ValidationException {
        if (getCountParts() <= 1 && getCountProducts() > 0) {
            //alertInvalid("Operation", "There must be at least one part in the inventory.");
            throw new ValidationException("There must be at least one part in the inventory.");
        } else {
            for (Part p : _allParts) {
                if (p.getID() == partID) {
                    _allParts.remove(p);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param productID
     * @return
     * @throws ValidationException
     */
    public static boolean removeProduct(int productID) throws ValidationException {

        for (Product p : _allProducts) {
            if (p.getID() == productID
                    && p.getCountAssociatedParts() > 0)
                throw new ValidationException("Products with at least one part cannot be deleted.");
            
            if (p.getID() == productID) {
                _allProducts.remove(p);
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param partID
     * @return
     */
    public static Part lookupPart(int partID) {
        for (Part p : _allParts) {
            if (p.getID() == partID) {
                return p;
            }
        }

        return null;
    }

    /**
     *
     * @param productID
     * @return
     */
    public static Product lookupProduct(int productID) {
        for (Product p : _allProducts) {
            if (p.getID() == productID) {
                return p;
            }
        }

        return null;
    }

    /**
     *
     * @param searchTerm
     * @return
     * @throws ValidationException
     */
    public static ObservableList<Part> lookupParts(String searchTerm) throws ValidationException {
        //searchTerm is Int
        Integer searchInt = tryParseInt(searchTerm);
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        boolean isAdded = false;

        if (searchTerm.length() > 0 && searchInt > 0) {
            Part foundPart = lookupPart(searchInt);

            //Match by ID
            if (foundPart instanceof InhousePart) {
                isAdded = foundParts.add((InhousePart) foundPart);
            } else if (foundPart instanceof OutsourcedPart) {
                isAdded = foundParts.add((OutsourcedPart) foundPart);
            }
        } else if (searchTerm.length() > 0) {
            
            //Match Regex Patterns
            for (Part p : _allParts) {
                Pattern regexPattern = Pattern.compile(".*" + searchTerm.trim() + ".*", Pattern.CASE_INSENSITIVE);
                Matcher regexMatcherName = regexPattern.matcher(p.getName().toLowerCase());
                Matcher regexMatcherID = regexPattern.matcher(Integer.toString(p.getID()));
                
                if (regexMatcherName.matches() || regexMatcherID.matches()) {
                    if (p instanceof InhousePart) {
                        isAdded = foundParts.add((InhousePart) p);
                    } else if (p instanceof OutsourcedPart) {
                        isAdded = foundParts.add((OutsourcedPart) p);
                    }
                }
            }
        } else {
            isAdded = true;
            foundParts = getAllParts();
        }

        if (!isAdded) {
            throw new ValidationException("Part could not be found.");
        }

        return foundParts;
    }

    /**
     *
     * @param searchTerm
     * @return
     * @throws ValidationException
     */
    public static ObservableList<Product> lookupProducts(String searchTerm) throws ValidationException {
        //searchTerm is Int
        Integer searchInt = tryParseInt(searchTerm);
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        boolean isAdded = false;

        System.out.println("searchTerm: " + searchTerm);
        
        if (searchTerm.length() > 0 && searchInt > 0) {
            Product foundProduct = lookupProduct(searchInt);
            isAdded = foundProducts.add(foundProduct);
            
        } else if (searchTerm.length() > 0) {

            for (Product p : _allProducts) {
                Pattern regexPattern = Pattern.compile(".*" + searchTerm.trim() + ".*", Pattern.CASE_INSENSITIVE);
                Matcher regexMatcherName = regexPattern.matcher(p.getName());
                Matcher regexMatcherID = regexPattern.matcher(Integer.toString(p.getID()));
                
                if (regexMatcherName.matches() || regexMatcherID.matches()) {
                    isAdded = foundProducts.add(p);
                }
            }
        } else {
            isAdded = true;
            foundProducts = getAllProducts();
        }

        if (!isAdded) {
            throw new ValidationException("Product could not be found.");
        }

        return foundProducts;
    }

    /**
     *
     * @param partUpdated
     */
    public static void updatePart(Part partUpdated) {
        Part foundPart = lookupPart(partUpdated.getID());

        if (foundPart != null) {
            foundPart.setInStock(partUpdated.getInStock());
            foundPart.setMax(partUpdated.getMax());
            foundPart.setMin(partUpdated.getMin());
            foundPart.setName(partUpdated.getName());
            foundPart.setPrice(partUpdated.getPrice());
            
            if (partUpdated instanceof InhousePart) {
                InhousePart foundInhousePart = (InhousePart)foundPart;
                foundInhousePart.setMachineID(((InhousePart)partUpdated).getMachineID());
            }
            else {
                OutsourcedPart foundOutsourcedPart = (OutsourcedPart)foundPart;
                foundOutsourcedPart.setCompanyName(((OutsourcedPart)partUpdated).getCompanyName());
            }
                
        }
    }

    /**
     *
     * @param productUpdated
     */
    public static void updateProduct(Product productUpdated) {
        Product foundProduct = lookupProduct(productUpdated.getID());

        if (foundProduct != null) {
            foundProduct.setInStock(productUpdated.getInStock());
            foundProduct.setMax(productUpdated.getMax());
            foundProduct.setMin(productUpdated.getMin());
            foundProduct.setName(productUpdated.getName());
            foundProduct.setPrice(productUpdated.getPrice());
        }
    }

    private static boolean partExistsInList(int partID) {
        Part foundPart = lookupPart(partID);
        return foundPart.getID() > 0;
    }

    private static boolean productExistsInList(int productID) {
        Product foundProduct = lookupProduct(productID);
        return foundProduct.getID() > 0;
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
