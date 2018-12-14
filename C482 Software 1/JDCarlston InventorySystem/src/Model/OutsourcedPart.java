/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.*;

/**
 *
 * @author Jennifer Carlston
 */
public class OutsourcedPart extends Part {

    private String _companyName;

    /**
     *
     */
    public OutsourcedPart() { }

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param instock
     * @param min
     * @param max
     */
    public OutsourcedPart(int id, String name, double price, int instock, int min, int max) {
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
     * @param companyname
     */
    public OutsourcedPart(int id, String name, double price, int instock, int min, int max, String companyname) {
        super(id, name, price, instock, min, max);

        setCompanyName(companyname);
    }

    /**
     *
     * @param newCompanyName
     */
    public void setCompanyName(String newCompanyName) {
        _companyName = newCompanyName;
    }

    /**
     *
     * @return
     */
    public String getCompanyName() {
        return _companyName;
    }
}
