package interfaz;

public class Config {

    public String direccionWeb ="";
    public String consumer_key = "";
    public String consumer_secret = "";
    public boolean autoDetectImpresora = false;
    public String nombre = "Esto es el nombre";
    public String direccion = "Esto es la direccion";
    public String cif = "xxxxxxxxy";
    public String telefono = "xxx xxx xxx";

    public String plantillaTicket =  "https://github.com/dm94/WooPos \n" +
            "{{nombre}}\n"+
            "{{direccion}}\n"+
            "Tel: {{telefono}}\n"+
            "Nº: {{numTicket}} \t Fecha: {{fecha}} \n"+
            "====================================================\n"+
            "Producto \t\t\t Cantidad \t Precio" + "\n" +
            "{{productos}}\n"+
            "====================================================\n"+
            "\t SUBTOTAL: {{subTotal}}€\n"+
            "\t IVA: {{impuesto}}€\n"+
            "\t TOTAL: {{total}}€\n"+
            "====================================================\n"+
            "Conserve este ticket. Plazo maximo 15 días para cambiar desde su compra\n"+
            "No se devolvera el dinero en efectivo, se hará vale. \n"+
            "Los articulos deben estar en perfecto estado y sin uso. \n"+
            "{{separador}}\n"+
            "\n"+
            "\n";

    public String plantillaProductos = "{{producto}} \t \t {{cantidad}} \t {{precio}}";
}
