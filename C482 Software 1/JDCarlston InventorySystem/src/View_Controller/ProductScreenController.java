/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import static Model.Inventory.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.xml.bind.ValidationException;

/**
 * FXML Controller class
 *
 * @author Jennifer Carlston
 */
public class ProductScreenController extends BaseController {

    private final Product activeProduct;
    //private Product _productToUpsert = new Product(); 

    private Part _partToAssociate;

    /**
     *
     * @param newPart
     */
    public void setPartToAssociate(Part newPart) {
        _partToAssociate = newPart;
    }

    /**
     *
     * @return
     */
    public Part getPartToAssociate() {
        return _partToAssociate;
    }

    @FXML
    private Label lblProductScreen;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField textFieldProductID;

    @FXML
    private TextField textFieldProductName;

    @FXML
    private TextField textFieldProductInventory;

    @FXML
    private TextField textFieldProductPrice;

    @FXML
    private TextField textFieldProductMax;

    @FXML
    private TextField textFieldProductMin;

    @FXML
    private Button btnSearchParts;

    @FXML
    private TextField textFieldSearchParts;

    @FXML
    private Button btnAddPart;

    @FXML
    private TableView<Part> tableViewAssociatedParts;

    @FXML
    private TableColumn<Part, Integer> tableColumnAssociatedPartID;

    @FXML
    private TableColumn<Part, String> tableColumnAssociatedPartName;

    @FXML
    private TableColumn<Part, Integer> tableColumnAssociatedPartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> tableColumnAssociatedPartPrice;

    @FXML
    private Button btnDeletePart;

    @FXML
    private TableView<Part> tableViewAllParts;

    @FXML
    private TableColumn<Part, Integer> tableColumnAllPartID;

    @FXML
    private TableColumn<Part, String> tableColumnAllPartName;

    @FXML
    private TableColumn<Part, Integer> tableColumnAllPartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> tableColumnAllPartPrice;

    @FXML
    void clickAddPart(ActionEvent event) {
        setPartToAssociate(tableViewAllParts.getSelectionModel().getSelectedItem());

        if (getPartToAssociate() == null) {
            alertInvalid("Part", "Please select a {Part} to associate.");
        } else {
            activeProduct.addAssociatedPart(getPartToAssociate());
            loadTableViewAssociatedParts(activeProduct.getAssociatedParts());
        }

    }

    @FXML
    void clickCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel " + lblProductScreen.getText());
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to exit the {" + lblProductScreen.getText() + "} screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    //This just "deletes" the associated part from the active Product parts list
    //Function Name refers to UI text
    @FXML
    void clickDeletePart(ActionEvent event) throws IOException {
        Part partToDelete = (tableViewAssociatedParts.getSelectionModel().getSelectedItem());

        if (partToDelete == null) {
            alertInvalid("Part", "Please select a {Part} to dissociate.");
        } else {
            Optional<ButtonType> result = alertDelete(partToDelete);

            if (result.get() == ButtonType.OK) {

                try {
                    activeProduct.removeAssociatedPart(partToDelete);
                    loadTableViewAssociatedParts(activeProduct.getAssociatedParts());
                } catch (ValidationException e) {
                    alertInvalid("Product Operation", e.getMessage());
                }
            }
        }
    }

    @FXML
    void clickSave(ActionEvent event) throws IOException {
        if (activeProduct.getProductID() != getNewProductID()) {

            saveModifiedProduct(event);

        } //Add New
        else {
            saveNewProduct(event);
        }
    }

    @FXML
    void clickSearchParts(ActionEvent event) {
        String searchTerm = textFieldSearchParts.getText();
        try {
            ObservableList<Part> foundParts = lookupParts(searchTerm);

            if (foundParts != null) {
                loadTableViewAllParts(foundParts);
            }
        } catch (ValidationException e) {
            alertInvalid("Part Search", e.getMessage());
        }
    }

    /**
     *
     */
    public ProductScreenController() {

        if (MainScreenController.getActiveProduct() != null
                && MainScreenController.getActiveProduct().getProductID() > 0) {
            activeProduct = MainScreenController.getActiveProduct();
        } else {
            activeProduct = new Product();
            activeProduct.setProductID(getNewProductID());
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (MainScreenController.getActiveProduct() == null
                || MainScreenController.getActiveProduct().getProductID() <= 0) {
            activeProduct.setProductID(getNewProductID());
        }

        setTextFields();
        setScreenLabel();

        bindTableColumnsAllParts();
        bindTableColumnsAssociatedParts();

        loadTableViewAssociatedParts(activeProduct.getAssociatedParts());
        loadTableViewAllParts(getAllParts());
    }

    private void setScreenLabel() {
        if (activeProduct.getProductID() == getNewProductID()) {
            lblProductScreen.setText("Add Product");
        } else {
            lblProductScreen.setText("Modify Product");
        }
    }

    private void setTextFields() {
        textFieldProductID.setText(Integer.toString(activeProduct.getProductID()));

        if (activeProduct.getProductID() != getNewProductID()) {
            textFieldProductName.setText(activeProduct.getName());
            textFieldProductInventory.setText(Integer.toString(activeProduct.getInStock()));
            textFieldProductPrice.setText(Double.toString(activeProduct.getPrice()));
            textFieldProductMax.setText(Integer.toString(activeProduct.getMax()));
            textFieldProductMin.setText(Integer.toString(activeProduct.getMin()));
        }

        //Load Added Parts List
        loadTableViewAssociatedParts(activeProduct.getAssociatedParts());
    }

    private void saveNewProduct(ActionEvent event) throws IOException {
        setProductFromTextFields();

        try {
            activeProduct.isValid();
            addProduct(activeProduct);

            showFxScreen(event, "MainScreen.fxml");
        } catch (ValidationException e) {
            alertInvalid("Product", e.getMessage());
        }
    }

    private void saveModifiedProduct(ActionEvent event) throws IOException {
        setProductFromTextFields();

        try {
            activeProduct.isValid();
            updateProduct(activeProduct);

            showFxScreen(event, "MainScreen.fxml");
        } catch (ValidationException e) {
            alertInvalid("Product", e.getMessage());
        }
    }

    private void setProductFromTextFields() {
        String id = textFieldProductID.getText();
        String name = textFieldProductName.getText();
        String inventory = textFieldProductInventory.getText();
        String price = textFieldProductPrice.getText();
        String min = textFieldProductMin.getText();
        String max = textFieldProductMax.getText();

        activeProduct.setProductID(Item.tryParseInt(id));
        activeProduct.setName(name);
        activeProduct.setInStock(Item.tryParseInt(inventory));
        activeProduct.setPrice(Item.tryParseDouble(price));
        activeProduct.setMin(Item.tryParseInt(min));
        activeProduct.setMax(Item.tryParseInt(max));
    }

    private void bindTableColumnsAllParts() {
        tableColumnAllPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        tableColumnAllPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnAllPartInventoryLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        tableColumnAllPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    private void bindTableColumnsAssociatedParts() {
        tableColumnAssociatedPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        tableColumnAssociatedPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnAssociatedPartInventoryLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        tableColumnAssociatedPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    private void loadTableViewAllParts(ObservableList<Part> partList) {
        tableViewAllParts.setItems(partList);
    }

    private void loadTableViewAssociatedParts(ObservableList<Part> partList) {
        tableViewAssociatedParts.setItems(partList);
    }
}
