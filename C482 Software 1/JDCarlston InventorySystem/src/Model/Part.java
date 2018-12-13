/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Jennifer Carlston
 */
public abstract class Part extends Item {
    
    /**
     *
     */
    public Part() { }
    
    /**
     *
     * @param id
     * @param name
     * @param price
     * @param instock
     * @param min
     * @param max
     */
    public Part(int id, String name, double price, int instock, int min, int max)
    {
        super(id, name, price, instock, min, max);
    }

    /**
     *
     * @param newPartID
     */
    public void setPartID(int newPartID)
    {
        setID(newPartID);
    }
    
    /**
     *
     * @return
     */
    public int getPartID()
    {
        return getID();        
    }
}
