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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javax.xml.bind.ValidationException;

/**
 * FXML Controller class
 *
 * @author Jennifer Carlston
 */
public class MainScreenController extends BaseController {

    @FXML
    private TableView<Part> tableViewParts;

    @FXML
    private TableColumn<Part, Integer> tableColumnPartID;

    @FXML
    private TableColumn<Part, String> tableColumnPartName;

    @FXML
    private TableColumn<Part, Integer> tableColumnPartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> tableColumnPartPrice;

    @FXML
    private TextField textFieldSearchParts;

    @FXML
    private TableView<Product> tableViewProducts;

    @FXML
    private TableColumn<Product, Integer> tableColumnProductID;

    @FXML
    private TableColumn<Product, String> tableColumnProductName;

    @FXML
    private TableColumn<Product, Integer> tableColumnProductInventoryLevel;

    @FXML
    private TableColumn<Product, Double> tableColumnProductPrice;

    @FXML
    private TextField textFieldSearchProducts;

    @FXML
    void clickAddPart(ActionEvent event) throws IOException {
        showFxScreen(event, "PartScreen.fxml");
    }

    @FXML
    void clickAddProduct(ActionEvent event) throws IOException {
        /*Product productToAdd = new Product();
        productToAdd.setProductID(getNewProductID());
        setActiveProduct(productToAdd);*/

        showFxScreen(event, "ProductScreen.fxml");
    }

    @FXML
    void clickDeletePart(ActionEvent event) throws IOException {
        Part partToDelete = tableViewParts.getSelectionModel().getSelectedItem();
        String itemType = "Part";

        /*if (getCountParts() <= 1 && getCountProducts() > 0) {
            alertInvalid(itemType + " Operation", "There must be at least one " + itemType + ".");
        } else */
        if (partToDelete != null) {
            Optional<ButtonType> result = alertDelete(partToDelete);

            if (result.get() == ButtonType.OK) {

                try {
                    deletePart(partToDelete.getPartID());
                    loadTableViewParts(getAllParts());
                } catch (ValidationException e) {
                    alertInvalid(itemType, e.getMessage());
                }
            }
        } else {
            alertInvalid("Selection", "Please select a {" + itemType + "} to delete.");
        }
    }

    @FXML
    void clickDeleteProduct(ActionEvent event) throws IOException {
        Product productToDelete = tableViewProducts.getSelectionModel().getSelectedItem();
        String itemType = "Product";

        if (productToDelete != null) {
            Optional<ButtonType> result = alertDelete(productToDelete);

            if (result.get() == ButtonType.OK) {
                try {
                    removeProduct(productToDelete.getProductID());
                    loadTableViewProducts(getAllProducts());
                } catch (ValidationException e) {
                    alertInvalid(itemType, e.getMessage());
                }
            }
        } else {
            alertInvalid("Selection", "Please select a {" + itemType + "} to delete.");
        }
    }

    @FXML
    void clickExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.initModality(Modality.NONE);
        alert.setTitle("Inventory Management System");
        alert.setHeaderText("Exiting IMS");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void clickModifyPart(ActionEvent event) throws IOException {
        setActivePart(tableViewParts.getSelectionModel().getSelectedItem());

        if (getActivePart() == null) {
            alertInvalid("Part", "Please select a {Part} to modify.");
        } else {
            showFxScreen(event, "PartScreen.fxml");
        }

    }

    @FXML
    void clickModifyProduct(ActionEvent event) throws IOException {
        setActiveProduct(tableViewProducts.getSelectionModel().getSelectedItem());

        if (getActiveProduct() == null) {
            alertInvalid("Product", "Please selecte a {Product} to modify");
        } else {
            showFxScreen(event, "ProductScreen.fxml");
        }
    }

    @FXML
    void clickSearchParts(ActionEvent event) {
        String searchTerm = textFieldSearchParts.getText();
        try {
            ObservableList<Part> foundParts = lookupParts(searchTerm);

            if (foundParts != null) {
                loadTableViewParts(foundParts);
            }
        } catch (ValidationException e) {
            alertInvalid("Part Search", e.getMessage());
        }
    }

    @FXML
    void clickSearchProducts(ActionEvent event) {
        String searchTerm = textFieldSearchProducts.getText();
        try {
            ObservableList<Product> foundProducts = lookupProducts(searchTerm);

            if (foundProducts != null) {
                loadTableViewProducts(foundProducts);
            }
        } catch (ValidationException e) {
            alertInvalid("Product Search", e.getMessage());
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
        setActivePart(null);
        setActiveProduct(null);

        bindTableColumnsParts();
        bindTableColumnsProducts();

        loadTableViewParts(getAllParts());
        loadTableViewProducts(getAllProducts());
    }

    private void bindTableColumnsParts() {
        tableColumnPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        tableColumnPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnPartInventoryLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        tableColumnPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    private void bindTableColumnsProducts() {
        tableColumnProductID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductID()).asObject());
        tableColumnProductName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnProductInventoryLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        tableColumnProductPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    private void loadTableViewParts(ObservableList<Part> partList) {
        tableViewParts.setItems(partList);
    }

    private void loadTableViewProducts(ObservableList<Product> productList) {
        tableViewProducts.setItems(productList);
    }
}
