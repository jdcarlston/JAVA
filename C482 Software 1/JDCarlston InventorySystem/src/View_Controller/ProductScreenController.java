/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import static Model.Inventory.*;
import static View_Controller.BaseController.alertInvalid;
import static View_Controller.MainScreenController.alertDelete;

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

import static View_Controller.MainScreenController.getActiveProduct;
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

    private Product _productToAdd;
    private Part _partToAssociate;

    /**
     *
     * @param newProduct
     */
    public void setProductToAdd(Product newProduct) {
        _productToAdd = newProduct;
    }

    /**
     *
     * @return
     */
    public Product getProductToAdd() {
        return _productToAdd;
    }

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
    private TableColumn<Part, Integer> tableColumnAddedPartID;

    @FXML
    private TableColumn<Part, String> tableColumnAddedPartName;

    @FXML
    private TableColumn<Part, Integer> tableColumnAddedPartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> tableColumnAddedPartPrice;

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

        try {
            getProductToAdd().addAssociatedPart(getPartToAssociate());
            getProductToAdd().isValid();
        } catch (ValidationException e) {
            alertInvalid("Associated Part", e.getMessage());
        }
        loadTableViewAddedParts(activeProduct.getAssociatedParts());
    }

    @FXML
    void clickCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel " + lblProductScreen.getText());
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to exit the " + lblProductScreen.getText() + " screen?");
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

        //System.out.println(activeProduct.getName() + " delete part #" + partToDelete);
        Optional<ButtonType> result = alertDelete(partToDelete, "Part");

        if (result.get() == ButtonType.OK) {

            try {
                activeProduct.removeAssociatedPart(partToDelete);
                loadTableViewAddedParts(activeProduct.getAssociatedParts());
            } catch (ValidationException e) {
                alertInvalid("Product Operation", e.getMessage());
            }
        }
    }

    @FXML
    void clickSave(ActionEvent event) throws IOException {
        if (activeProduct != null) {
            clickUpdateProduct(event);

        } //Add New
        else {
            activeProduct.setID(Inventory.getNewProductID());
            clickAddProduct(event);
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
            alertInvalid("Search Parts", e.getMessage());
        }
    }

    /**
     *
     */
    public ProductScreenController() {
        activeProduct = getActiveProduct();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bindTableColumnsAllParts();
        bindTableColumnsAddedParts();

        if (activeProduct != null) {
            setModifyProductFields();
        } else {
            lblProductScreen.setText("Add Product");

            //Auto-generate latest ID for adding parts
            int newPartID = getNewPartID();
            textFieldProductID.setText(Integer.toString(newPartID));
        }

        loadTableViewAllParts(getAllParts());
    }

    private void setModifyProductFields() {
        lblProductScreen.setText("Modify Product");

        textFieldProductID.setText(Integer.toString(activeProduct.getProductID()));
        textFieldProductName.setText(activeProduct.getName());
        textFieldProductInventory.setText(Integer.toString(activeProduct.getInStock()));
        textFieldProductPrice.setText(Double.toString(activeProduct.getPrice()));
        textFieldProductMax.setText(Integer.toString(activeProduct.getMax()));
        textFieldProductMin.setText(Integer.toString(activeProduct.getMin()));

        //Load Added Parts List
        loadTableViewAddedParts(activeProduct.getAssociatedParts());
    }

    private void clickAddProduct(ActionEvent event) throws IOException {
        setProductToAdd(getProductByTextFields());

        try {
            getProductToAdd().isValid();
            addProduct(getProductToAdd());

            showFxScreen(event, "MainScreen.fxml");
        } catch (ValidationException e) {
            alertInvalid("Product", e.getMessage());
        }
    }

    private void clickUpdateProduct(ActionEvent event) throws IOException {
        Product productToUpdate = getProductByTextFields();

        try {
            productToUpdate.isValid();
            updateProduct(productToUpdate);

            showFxScreen(event, "MainScreen.fxml");
        } catch (ValidationException e) {
            alertInvalid("Product", e.getMessage());
        }
    }

    private Product getProductByTextFields() {
        String id = textFieldProductID.getText();
        String name = textFieldProductName.getText();
        String inventory = textFieldProductInventory.getText();
        String price = textFieldProductPrice.getText();
        String min = textFieldProductMin.getText();
        String max = textFieldProductMax.getText();

        Product newProduct = new Product();

        newProduct.setID(Integer.parseInt(id));
        newProduct.setName(name);
        newProduct.setInStock(Integer.parseInt(inventory));
        newProduct.setPrice(Double.parseDouble(price));
        newProduct.setMin(Integer.parseInt(min));
        newProduct.setMax(Integer.parseInt(max));

        return newProduct;
    }

    private void bindTableColumnsAllParts() {
        tableColumnAllPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        tableColumnAllPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnAllPartInventoryLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        tableColumnAllPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    private void bindTableColumnsAddedParts() {
        tableColumnAddedPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        tableColumnAddedPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnAddedPartInventoryLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        tableColumnAddedPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    private void loadTableViewAllParts(ObservableList<Part> partList) {
        tableViewAllParts.setItems(partList);
    }

    private void loadTableViewAddedParts(ObservableList<Part> partList) {
        tableViewAssociatedParts.setItems(partList);
    }
}
