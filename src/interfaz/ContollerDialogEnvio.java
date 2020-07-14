package interfaz;

import entities.Shipping;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContollerDialogEnvio {

    @FXML
    TextField txtNombreEnvio;

    @FXML
    TextField txtDireccionEnvio;

    @FXML
    TextField txtDireccion2Envio;

    @FXML
    TextField txtEnvioTelefono;

    @FXML
    TextField txtEnvioPrecio;

    @FXML
    Button btGuardarEnvio;

    private Shipping shipping;

    public void initialize(){
        btGuardarEnvio.setOnAction(event ->  crearEnvio());
    }

    private void crearEnvio() {
        shipping = new Shipping();
        shipping.setFirstName(txtNombreEnvio.getText());
        shipping.setAddress1(txtDireccionEnvio.getText());
        shipping.setAddress2(txtDireccion2Envio.getText());
        shipping.setCompany(txtEnvioTelefono.getText());
        shipping.setShippingTotal(txtEnvioPrecio.getText());

        ((Stage) btGuardarEnvio.getScene().getWindow()).close();
    }

    public Shipping getShipping() {
        return shipping;
    }
}
