package conexionWeb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.Product;
import gestion.GestionConfig;
import interfaz.Config;
import interfaz.Controller;

import java.util.ArrayList;

public class GestionArticulos extends Thread {
    private ArrayList<Product> productos;
    private Controller controller;
    private Config config;

    public GestionArticulos(Controller controller, Config config) {
        this.controller = controller;
        this.config = config;
    }

    public void run(){
        sacarProductos();
    }

    public void sacarProductos() {
        productos = new ArrayList<>();
        System.out.println("Sacando productos de la web");
        int page = 1;
        JsonParser parser = new JsonParser();
        JsonElement jsonelement;
        Gson gson = new Gson();
        boolean hayProductos = false;
        do {
            try {
                hayProductos = false;
                String direccion = config.direccionWeb+"/wp-json/wc/v3/products?page="+page+"&per_page=100&consumer_key="+config.consumer_key+"&consumer_secret="+config.consumer_secret;
                jsonelement = parser.parse(GestionConfig.devolverJson(direccion));

                if (jsonelement.isJsonArray()) {
                    JsonArray array = jsonelement.getAsJsonArray();
                    java.util.Iterator<JsonElement> iter = array.iterator();
                    while (iter.hasNext()) {
                        JsonElement producto = iter.next();
                        Product product = gson.fromJson(producto, Product.class);
                        if (product != null && !product.getSku().contains("EXT1") && !product.getSku().contains("DP")){
                            productos.add(product);
                        }
                        hayProductos = true;
                    }
                }
                controller.setItemList(productos);
                page++;
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (hayProductos);
        controller.setItemList(productos);
    }
}
