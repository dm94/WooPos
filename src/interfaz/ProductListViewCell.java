package interfaz;

import entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ProductListViewCell extends ListCell<Product> {

    @FXML
    Label modelo;

    @FXML
    Label precio;

    @FXML
    Button aniadirAlCarro;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    private Controller controller;
    private Product product;

    public ProductListViewCell(Controller controller) {
        this.controller = controller;
    }

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if(empty || product == null) {
            //setText(null);
            //setGraphic(null);
            this.product = null;
        } else {
            this.product = product;
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("fxml/ProductListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            aniadirAlCarro.setOnAction(event -> anidairAlCarro());
            modelo.setText(product.getName());
            precio.setText(product.getPrice()+" €");

            setText(null);
            setGraphic(gridPane);
        }
    }

    private void anidairAlCarro() {
        if (product == null || controller == null)  return;

        controller.aniadirProducto(product);
    }
}
