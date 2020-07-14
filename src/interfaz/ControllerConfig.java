package interfaz;

import gestion.GestionConfig;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerConfig {

    private Config config;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtCif;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextArea txtPlantillaTicket;

    @FXML
    private TextField txtPlantillaProducto;

    @FXML
    private TextField txtDireccionWeb;

    @FXML
    private TextField txtConsumerKey;

    @FXML
    private TextField txtConsumerSecret;

    @FXML
    private Button btGuardarConfig;

    @FXML
    private Button btCancelar;

    public void initialize(){
        btGuardarConfig.setOnAction(event -> guardarConfig());
        btCancelar.setOnAction(event -> { ((Stage) btCancelar.getScene().getWindow()).close();});
    }

    public void setConfig(Config config) {
        this.config = config;
        recargarCampos();
    }

    private void recargarCampos(){
        if (config == null) return;

        txtNombre.setText(config.nombre);
        txtDireccion.setText(config.direccion);
        txtCif.setText(config.cif);
        txtTelefono.setText(config.telefono);
        txtPlantillaTicket.setText(config.plantillaTicket);
        txtPlantillaProducto.setText(config.plantillaProductos);
        txtDireccionWeb.setText(config.direccionWeb);
        txtConsumerKey.setText(config.consumer_key);
        txtConsumerSecret.setText(config.consumer_secret);
    }

    public void guardarConfig() {
        config.nombre = txtNombre.getText();
        config.direccion = txtDireccion.getText();
        config.cif = txtCif.getText();
        config.telefono = txtTelefono.getText();
        config.plantillaTicket = txtPlantillaTicket.getText();
        config.plantillaProductos = txtPlantillaProducto.getText();
        config.direccionWeb = txtDireccionWeb.getText();
        config.consumer_key = txtConsumerKey.getText();
        config.consumer_secret = txtConsumerSecret.getText();

        GestionConfig.guardarConfig(config);
        ((Stage) btGuardarConfig.getScene().getWindow()).close();
    }

}
