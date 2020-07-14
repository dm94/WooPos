package interfaz;

import entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class ProductBuyListView extends ListCell<Product> {

    @FXML
    TextField modelo;

    @FXML
    TextField precio;

    @FXML
    Button borrarDelCarro;

    @FXML
    Label totalPrecio;

    @FXML
    private GridPane gridPane;

    @FXML
    private Spinner<Integer> spCantidad;

    private FXMLLoader mLLoader;

    private Product product;

    private Controller controller;

    public ProductBuyListView(Controller controller) {
        this.controller = controller;
    }

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if(empty || product == null) {
            setText(null);
            setGraphic(null);
            this.product = null;
        } else {
            this.product = product;
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("fxml/ProductBuyListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.modelo.setText(product.getName());
            this.precio.setText(product.getPrice());

            this.borrarDelCarro.setOnAction(event -> borrarDelCarro());
            this.modelo.setOnAction(event -> actualizarProducto());
            this.precio.setOnAction(event -> actualizarProducto());
            this.spCantidad.valueProperty().addListener((obs, oldValue, newValue) -> actualizarProducto());
            setText(null);
            setGraphic(this.gridPane);
            actualizarProducto();
        }

    }

    public void actualizarProducto() {
        if (product != null) {
            product.setName(modelo.getText());
            product.setPrice(precio.getText());
            product.setStockQuantity(spCantidad.getValue());
            if (!precio.getText().isEmpty()) {
                totalPrecio.setText(String.valueOf(Double.parseDouble(precio.getText())*Double.parseDouble(spCantidad.getValue().toString())));
            }
            controller.actualizarTotalSubTotal();
        }
    }

    private void borrarDelCarro() {
        if (product == null || controller == null) return;

        controller.borrarDelCarro(product);
    }

}
