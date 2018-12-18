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
        return getAllParts().size();
    }

    /**
     *
     * @return
     */
    public static int getCountProducts() {
        return getAllProducts().size();
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
        getAllParts().add(part);
    }

    /**
     *
     * @param productToAdd
     */
    public static void addProduct(Product productToAdd) {
        getAllProducts().add(productToAdd);
    }

    /**
     *
     * @param partID
     * @return
     * @throws ValidationException
     */
    public static boolean deletePart(int partID) throws ValidationException {
        if (getCountParts() <= 1
                && getCountProducts() > 0) {
            throw new ValidationException("There must be at least one {Part} in the IMS.");
        } else {

            //Loop through all parts in inventory
            for (Part part : getAllParts()) {
                if (part.getPartID() == partID) {

                    //Loop through all products, remove part from product if possible
                    for (Product product : getAllProducts()) {

                        //Check if the product has part associated
                        if (product.getAssociatedParts().contains(part)
                                && product.getCountAssociatedParts() > 1) {

                            //Dissociate Part from Product
                            product.getAssociatedParts().remove(part);

                        } else if (product.getAssociatedParts().contains(part)) {
                            throw new ValidationException(
                                    "Please remove {Part #"
                                    + partID + "} from  {Product #" 
                                    + product.getProductID() + "} first."
                                    + "\n\n{Product #"
                                    + product.getProductID() 
                                    + "} must have at least one part to be valid.");
                        }
                    }

                    //Remove Part from IMS
                    getAllParts().remove(part);

                    return true;
                }
            }

            return false;
        }
    }

    /**
     *
     * @param productID
     * @return
     * @throws ValidationException
     */
    public static boolean removeProduct(int productID) throws ValidationException {

        for (Product p : getAllProducts()) {
            if (p.getProductID() == productID
                    && p.getCountAssociatedParts() > 0) {
                throw new ValidationException("{Products} with at least one {Part} cannot be deleted.");
            }

            if (p.getProductID() == productID) {
                getAllProducts().remove(p);
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
        for (Part p : getAllParts()) {
            if (p.getPartID() == partID) {
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
        for (Product p : getAllProducts()) {
            if (p.getProductID() == productID) {
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
        //if searchTerm is Int
        Integer searchInt = Part.tryParseInt(searchTerm);
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        boolean isAdded = false;

        if (searchTerm.length() > 0
                && searchInt > 0) {

            Part foundPart = lookupPart(searchInt);

            if (foundPart != null) {
                isAdded = foundParts.add(foundPart);
            }

        } else if (searchTerm.length() > 0) {

            //Match Regex Patterns
            for (Part p : getAllParts()) {

                searchTerm = ".*" + searchTerm.trim() + ".*";
                Pattern regexPattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);
                Matcher regexMatcherName = regexPattern.matcher(p.getName().toLowerCase());
                Matcher regexMatcherID = regexPattern.matcher(Integer.toString(p.getPartID()));

                if (regexMatcherName.matches()
                        || regexMatcherID.matches()) {
                    isAdded = foundParts.add(p);
                }
            }
        } else {
            isAdded = true;
            foundParts = getAllParts();
        }

        if (!isAdded) {
            throw new ValidationException("{Part} matching {" + searchTerm + "} could not be found.");
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
        Integer searchInt = Product.tryParseInt(searchTerm);
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        boolean isAdded = false;

        if (searchTerm.length() > 0
                && searchInt > 0) {
            Product foundProduct = lookupProduct(searchInt);
            isAdded = foundProducts.add(foundProduct);

        } else if (searchTerm.length() > 0) {

            for (Product p : getAllProducts()) {

                searchTerm = ".*" + searchTerm.trim() + ".*";
                Pattern regexPattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);
                Matcher regexMatcherName = regexPattern.matcher(p.getName());
                Matcher regexMatcherID = regexPattern.matcher(Integer.toString(p.getProductID()));

                if (regexMatcherName.matches()
                        || regexMatcherID.matches()) {
                    isAdded = foundProducts.add(p);
                }
            }
        } else {
            isAdded = true;
            foundProducts = getAllProducts();
        }

        if (!isAdded) {
            throw new ValidationException("{Product} matching {" + searchTerm + "} could not be found.");
        }

        return foundProducts;
    }

    /**
     *
     * @param partUpdated
     */
    public static void updatePart(Part partUpdated) {
        Part foundPart = lookupPart(partUpdated.getPartID());
        int index = getAllParts().indexOf(foundPart);

        if (foundPart != null) {
            getAllParts().set(index, partUpdated);
        }
    }

    /**
     *
     * @param productUpdated
     */
    public static void updateProduct(Product productUpdated) {
        Product foundProduct = lookupProduct(productUpdated.getProductID());
        int index = getAllProducts().indexOf(foundProduct);

        if (foundProduct != null) {
            getAllProducts().set(index, productUpdated);
        }
    }
}
