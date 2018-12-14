package View_Controller;

import Model.*;
import static Model.Inventory.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import static View_Controller.MainScreenController.getActivePart;

import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javax.xml.bind.ValidationException;

/**
 *
 * @author Jennifer Carlston
 */
public class PartScreenController extends BaseController {

    private final Part activePart;

    @FXML
    private Label lblPartScreen;

    @FXML
    private Label lblPartDynamic;

    @FXML
    private RadioButton radioPartInhouse;

    @FXML
    private RadioButton radioPartOutsourced;

    @FXML
    private TextField textFieldPartID;

    @FXML
    private TextField textFieldPartName;

    @FXML
    private TextField textFieldPartInventory;

    @FXML
    private TextField textFieldPartPrice;

    @FXML
    private TextField textFieldPartMax;

    @FXML
    private TextField textFieldPartMin;

    @FXML
    private TextField textFieldPartDynamic;

    @FXML
    void clickCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel " + lblPartScreen.getText());
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to exit the " + lblPartScreen.getText() + " screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            showFxScreen(event, "MainScreen.fxml");
        }
    }

    @FXML
    void clickPartInhouse(ActionEvent event) {
            setInhouseVals();
    }

    @FXML
    void clickPartOutsourced(ActionEvent event) {
            setOutsourcedVals();
    }

    @FXML
    void clickSave(ActionEvent event) throws IOException {
        if (activePart != null) {
            clickUpdatePart(event);

        } //Add New
        else {
            clickAddPart(event);
        }
    }

    /**
     *
     */
    public PartScreenController() {
        activePart = getActivePart();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //If there is a part selected to modify
        if (activePart != null) {
            setSelectedPartTextFields();
        } //No part to modify
        else {
            lblPartScreen.setText("Add Part");

            setInhouseVals();

            //Auto-generate latest ID for adding parts
            int newID = getNewPartID();
            textFieldPartID.setText(Integer.toString(newID));
        }
    }

    private void setSelectedPartTextFields() {
        lblPartScreen.setText("Modify Part");

        textFieldPartID.setText(Integer.toString(activePart.getPartID()));
        textFieldPartName.setText(activePart.getName());
        textFieldPartInventory.setText(Integer.toString(activePart.getInStock()));
        textFieldPartPrice.setText(Double.toString(activePart.getPrice()));
        textFieldPartMax.setText(Integer.toString(activePart.getMax()));
        textFieldPartMin.setText(Integer.toString(activePart.getMin()));

        if (activePart instanceof InhousePart) {
            setInhouseVals();
            
            //Set dynamic field to Machine ID
            textFieldPartDynamic.setText(Integer.toString(((InhousePart) activePart).getMachineID()));
        } else {
            setOutsourcedVals();
            
            //Set dynamic Field to Company Name
            textFieldPartDynamic.setText(((OutsourcedPart) activePart).getCompanyName());
        }
    }

    private void setInhouseVals() {
        radioPartInhouse.setSelected(true);
        lblPartDynamic.setText("Machine ID");
        textFieldPartDynamic.setPromptText("Mach ID");
        
        if (activePart != null 
                && activePart instanceof OutsourcedPart)
            textFieldPartDynamic.setText("");
        else if (activePart != null)
        {
            InhousePart p = ((InhousePart)activePart);
            textFieldPartDynamic.setText((Integer.toString(p.getMachineID())));
        }
    }

    private void setOutsourcedVals() {
        radioPartOutsourced.setSelected(true);
        lblPartDynamic.setText("Company");
        textFieldPartDynamic.setPromptText("Company Name");      

        if (activePart != null
                && activePart instanceof InhousePart)
            textFieldPartDynamic.setText("");
        else if (activePart != null)
        {
            OutsourcedPart p = (OutsourcedPart)activePart;
            textFieldPartDynamic.setText(p.getCompanyName());
        }
    }

    private void clickAddPart(ActionEvent event) throws IOException {
        String name = textFieldPartName.getText();
        String inventory = textFieldPartInventory.getText();
        String price = textFieldPartPrice.getText();
        String min = textFieldPartMin.getText();
        String max = textFieldPartMax.getText();
        String dynamic = textFieldPartDynamic.getText();
        
        if (radioPartInhouse.isSelected()) {
            InhousePart newPart = new InhousePart();
            newPart.setPartID(getNewPartID());
            newPart.setName(name);
            newPart.setInStock(tryParseInt(inventory));
            newPart.setPrice(tryParseDouble(price));
            newPart.setMin(tryParseInt(min));
            newPart.setMax(tryParseInt(max));
            newPart.setMachineID(tryParseInt(dynamic));

            //System.out.println(newPart.getName());

            try {
                newPart.isValid();
                addPart(newPart);
                
                showFxScreen(event, "MainScreen.fxml");
            } catch (ValidationException e) {
                alertInvalid("In-House Part", e.getMessage());
            }
        } else {
            OutsourcedPart newPart = new OutsourcedPart();

            newPart.setPartID(activePart.getPartID());
            newPart.setName(name);
            newPart.setInStock(Integer.parseInt(inventory));
            newPart.setPrice(Double.parseDouble(price));
            newPart.setMin(Integer.parseInt(min));
            newPart.setMax(Integer.parseInt(max));
            newPart.setCompanyName(dynamic);

            try {
                newPart.isValid();
                addPart(newPart);
            } catch (ValidationException e) {
                alertInvalid("Outsourced Part", e.getMessage());
            }
        }
    }

    private void clickUpdatePart(ActionEvent event) throws IOException {
        boolean inhouse = radioPartInhouse.isSelected();
        Integer id = activePart.getID();
        String name = textFieldPartName.getText();
        String inventory = textFieldPartInventory.getText();
        String price = textFieldPartPrice.getText();
        String min = textFieldPartMin.getText();
        String max = textFieldPartMax.getText();
        String dynamic = textFieldPartDynamic.getText();

        if (activePart instanceof InhousePart 
                && inhouse) {
            InhousePart newPart = new InhousePart();

            newPart.setPartID(id);
            newPart.setName(name);
            newPart.setInStock(tryParseInt(inventory));
            newPart.setPrice(tryParseDouble(price));
            newPart.setMin(tryParseInt(min));
            newPart.setMax(tryParseInt(max));
            newPart.setMachineID(tryParseInt(dynamic));

            try {
                newPart.isValid();
                updatePart(newPart);
                
                showFxScreen(event, "MainScreen.fxml");
            } catch (ValidationException e) {
                alertInvalid("In-House Part", e.getMessage());
            }
        } else if (activePart instanceof OutsourcedPart) {
            OutsourcedPart newPart = new OutsourcedPart();

            newPart.setPartID(activePart.getPartID());
            newPart.setName(name);
            newPart.setInStock(tryParseInt(inventory));
            newPart.setPrice(tryParseDouble(price));
            newPart.setMin(tryParseInt(min));
            newPart.setMax(tryParseInt(max));
            newPart.setCompanyName(dynamic);

            try {
                newPart.isValid();
                updatePart(newPart);
            } catch (ValidationException e) {
                alertInvalid("Outsourced Part", e.getMessage());
            }
        }
    }
}
