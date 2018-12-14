/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.xml.bind.ValidationException;
/**
 *
 * @author Jennifer Carlston
 */
public class InhousePart extends Part {
    private int _machineID;
    
    /**
     *
     */
    public InhousePart() { }

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param instock
     * @param min
     * @param max
     */
    public InhousePart(int id, String name, double price, int instock, int min, int max)
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
     * @param machineid
     */
    public InhousePart(int id, String name, double price, int instock, int min, int max, int machineid)
    {
        super(id, name, price, instock, min, max);
        
        _machineID = machineid;
    }
    
    /**
     *
     * @param newMachineID
     */
    public void setMachineID(int newMachineID)
    {
        _machineID = newMachineID;
    }
    public void setMachineID(String newMachineID)
    {
        //MachineID is not required
        if (newMachineID.length() > 0)
            _machineID = tryParseInt(newMachineID);
    }
    
    /**
     *
     * @return
     */
    public int getMachineID()
    {
        return _machineID;        
    }    
     
    public boolean isValid() throws ValidationException {  
        boolean isValid = super.isValid();
        
        if (getMachineID() < 0) {
            throw new ValidationException("{Machine ID} for this item must be 0 or greater.");
        }
        
        return isValid;
    }
}
