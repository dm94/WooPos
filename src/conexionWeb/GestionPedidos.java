package conexionWeb;

import com.icoderman.woocommerce.EndpointBaseType;
import com.icoderman.woocommerce.WooCommerce;
import entities.LineItem;
import entities.Order;
import interfaz.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestionPedidos {

    private WooCommerce wooCommerce;
    private Config config;

    public GestionPedidos(Config config, WooCommerce wooCommerce) {
        this.wooCommerce = wooCommerce;
        this.config = config;
    }

    public void crearPedido(Order order) {
        try {
            Map<String, Object> orderInfo = new HashMap<>();
            orderInfo.put("payment_method","cod");
            orderInfo.put("payment_method_title","WooPOS");
            orderInfo.put("set_paid",true);

            Map<String, Object> billing = new HashMap<>();
            billing.put("first_name","Clientes Varios");
            billing.put("phone",order.getShipping().getCompany());

            orderInfo.put("billing",billing);

            Map<String, Object> shipping = new HashMap<>();
            shipping.put("first_name",order.getShipping().getFirstName());
            shipping.put("last_name",order.getShipping().getLastName());
            shipping.put("address_1",order.getShipping().getAddress1());
            shipping.put("address_2",order.getShipping().getAddress2());

            ArrayList<Map> lineItems = new ArrayList<Map>();
            for (LineItem product : order.getLineItems()) {
                Map<String, Object> lineItem = new HashMap<>();
                lineItem.put("product_id",product.getProductId());
                lineItem.put("name",product.getName());
                lineItem.put("quantity",product.getQuantity());
                lineItem.put("price",product.getPrice());
                lineItems.add(lineItem);
            }
            orderInfo.put("line_items",lineItems);

            wooCommerce.create(EndpointBaseType.ORDERS.getValue(),orderInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
