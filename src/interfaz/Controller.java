package interfaz;

import com.icoderman.woocommerce.ApiVersionType;
import com.icoderman.woocommerce.WooCommerce;
import com.icoderman.woocommerce.WooCommerceAPI;
import com.icoderman.woocommerce.oauth.OAuthConfig;
import conexionWeb.GestionArticulos;
import conexionWeb.GestionPedidos;
import entities.*;
import gestion.GestionConfig;
import gestionTickets.Local;
import gestionTickets.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private ListView<Product> itemListView;

    @FXML
    private ListView<Product> itemBuyList;

    @FXML
    private MenuItem recargarProductos;

    @FXML
    private Button aniadir;

    @FXML
    private Label lSubtotal;

    @FXML
    private Label lImpuesto;

    @FXML
    private Label lTotal;

    @FXML
    private Label lEnvio;

    @FXML
    private TextField infoCargaProductos;

    @FXML
    private TextField searchBox;

    private ObservableList<Product> lista;
    private ObservableList<Product> listaCompra;
    private WooCommerce wooCommerce;
    private ArrayList<Product> productos;
    private DecimalFormat df = new DecimalFormat("#.00");
    private Config config;
    private Local local;
    private GestionArticulos gestionArticulos;
    private GestionPedidos gestionPedidos;
    private Order order;
    private Shipping shipping;

    public Controller() {
        lista = FXCollections.observableArrayList();
        listaCompra = FXCollections.observableArrayList();
        config = GestionConfig.cargarConfig();
        OAuthConfig oAuthConfig = new OAuthConfig(config.direccionWeb, config.consumer_key, config.consumer_secret);
        wooCommerce = new WooCommerceAPI(oAuthConfig, ApiVersionType.V3);
        gestionArticulos = new GestionArticulos(this,config);
        gestionPedidos = new GestionPedidos(config,wooCommerce);
    }

    public void initialize(){
        itemListView.setItems(lista);

        itemListView.setCellFactory(productListView -> new ProductListViewCell(this));
        itemBuyList.setCellFactory(productBuyListView -> new ProductBuyListView(this));
        searchBox.setOnAction(event -> buscarProducto());
        local = new Local(config.nombre,config.direccion,config.cif,config.telefono);
        order = new Order();
        gestionArticulos.start();
    }

    public void setItemList(ArrayList<Product> productos) {
        this.productos = productos;
        lista.clear();
        lista.addAll(productos);
        System.out.println(lista.size());
        actualizarEstado("Productos cargados " + productos.size());
    }

    public void actualizarListado() {
        gestionArticulos.sacarProductos();
    }

    public void aniadirAlCarro() {
        listaCompra.add(itemListView.getSelectionModel().getSelectedItem());
        itemBuyList.setItems(listaCompra);
        actualizarTotalSubTotal();
    }

    public void actualizarTotalSubTotal() {
        double total = 0;
        double impuesto = 0;
        double subtotal = 0;

        if (listaCompra != null) {
            for (Product producto : listaCompra) {
                if (producto != null) {
                    subtotal += Double.parseDouble(producto.getPrice()) * producto.getStockQuantity();
                }
            }
        }

        total = subtotal * 1.21;
        impuesto = total - subtotal;

        if (shipping != null) {
            total = total + Double.parseDouble(shipping.getShippingTotal());
            lEnvio.setText("Envío: "+ df.format(Double.parseDouble(shipping.getShippingTotal())) + "€");
        }

        lSubtotal.setText("SubTotal: "+ df.format(subtotal) + "€");
        lImpuesto.setText("Impuesto: " + df.format(impuesto) + "€");
        lTotal.setText("Total: " + df.format(total) + "€");
    }

    public void finalizarCompra() {
        actualizarTotalSubTotal();
        List<LineItem> lineItems = new ArrayList<>();

        double total = 0;
        double impuesto = 0;
        double subtotal = 0;

        if (listaCompra != null) {
            for (Product producto : listaCompra) {
                LineItem item = new LineItem();
                item.setName(producto.getName());
                item.setProductId(producto.getId());
                item.setQuantity(producto.getStockQuantity());
                item.setPrice(producto.getPrice());
                subtotal += Double.parseDouble(producto.getPrice()) * producto.getStockQuantity();
                item.setSubtotal(String.valueOf(subtotal));
                System.out.println(producto.getName());
                lineItems.add(item);
            }
        }

        total = subtotal * 1.21;
        impuesto = total - subtotal;

        if (shipping == null) {
            shipping = new Shipping();
            shipping.setFirstName("Clientes varios");
            shipping.setAddress1(config.direccion);
        } else {
            order.setShippingTotal(shipping.getShippingTotal());
        }

        order.setShipping(shipping);
        order.setLineItems(lineItems);

        //order.setTotalTax(String.valueOf(impuesto));
        //order.setTotal(String.valueOf(total));

        gestionPedidos.crearPedido(order);

        new Ticket(local,config.plantillaTicket,config.plantillaProductos,"12314",new ArrayList<>(listaCompra),total,impuesto,subtotal).print();

        limpiarCompra();
    }

    public void aniadirProducto(Product product) {
        listaCompra.add(product);
        itemBuyList.setItems(listaCompra);
        actualizarTotalSubTotal();
    }

    public void borrarDelCarro(Product product) {
        listaCompra.remove(product);
        itemBuyList.setItems(listaCompra);
        actualizarTotalSubTotal();
    }

    public void limpiarCompra() {
        shipping = null;
        listaCompra.clear();
        itemBuyList.setItems(listaCompra);
        order = new Order();
        actualizarTotalSubTotal();
    }

    public void buscarProducto() {
        if (searchBox == null || searchBox.getText().isEmpty() || productos.size() < 1) {
            setItemList(productos);
            return;
        }

        String textoABuscar = searchBox.getText().toLowerCase();

        lista.clear();
        lista.addAll(productos.stream().filter(p -> (p.getName().toLowerCase().contains(textoABuscar) || p.getSku().toLowerCase().contains(textoABuscar))).collect(Collectors.toList()));
    }

    public void actualizarEstado(String estado) {
        if (infoCargaProductos == null) return;

        infoCargaProductos.setText(estado);
    }


    public void abrirConfiguracion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/InterfazConfig.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Configuración");
            stage.show();
            ControllerConfig controllerConfig = loader.getController();
            controllerConfig.setConfig(this.config);

            scene.getWindow().setOnCloseRequest(event -> setConfig(GestionConfig.cargarConfig()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aniadirEnvio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/DialogEnvio.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Envio");
            stage.show();
            ContollerDialogEnvio contollerDialogEnvio = loader.getController();

            scene.getWindow().setOnCloseRequest(event -> shipping = contollerDialogEnvio.getShipping());
            actualizarTotalSubTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConfig(Config config) {
        this.config = config;
        System.out.println(config.cif);
    }
}
