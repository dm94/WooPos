package gestionTickets;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;
import entities.Product;
import interfaz.Config;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Ticket {

    private Local local;
    private String plantilla;
    private String plantillaProductos;
    private String numTicket;
    private Date fecha;
    private ArrayList<Product> productos;
    private double total = 0;
    private double impuesto = 0;
    private double subtotal = 0;
    private DecimalFormat df = new DecimalFormat("#.00");

    public Ticket(Local local, String plantilla, String plantillaProductos, String numTicket, ArrayList<Product> productos, double total, double impuesto, double subtotal) {
        this.local = local;
        this.plantilla = plantilla;
        this.plantillaProductos = plantillaProductos;
        this.numTicket = numTicket;
        this.productos = productos;
        this.fecha = new Date();
        this.total = total;
        this.impuesto = impuesto;
        this.subtotal = subtotal;
    }

    public void generarTicket() {
        plantilla = plantilla.replace("{{nombre}}",local.getNombre());
        plantilla = plantilla.replace("{{direccion}}",local.getDireccion());
        plantilla = plantilla.replace("{{cif}}",local.getCif());
        plantilla = plantilla.replace("{{telefono}}",local.getTelefono());
        plantilla = plantilla.replace("{{numTicket}}",numTicket);
        plantilla = plantilla.replace("{{fecha}}",new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fecha));
        plantilla = plantilla.replace("{{subTotal}}",df.format(subtotal));
        plantilla = plantilla.replace("{{impuesto}}",df.format(impuesto));
        plantilla = plantilla.replace("{{total}}",df.format(total));
        plantilla = plantilla.replace("{{productos}}",generarListadoProductos());
    }

    public String generarListadoProductos() {
        String listado = "";

        for (Product p : productos) {
            String plantillaProductosCopia = plantillaProductos;
            if (p.getName().length() > 20) {
                p.setName(p.getName().substring(0,20));
            }
            plantillaProductosCopia = plantillaProductosCopia.replace("{{producto}}",p.getName().toUpperCase());
            plantillaProductosCopia = plantillaProductosCopia.replace("{{cantidad}}",p.getStockQuantity().toString());
            plantillaProductosCopia = plantillaProductosCopia.replace("{{precio}}",p.getPrice());
            listado += plantillaProductosCopia + "\n";
        }

        return listado;
    }

    public void print() {
        generarTicket();

        System.out.println(plantilla);

        try {
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
            PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
            PrintService selectedPrintService = ServiceUI.printDialog(null, 150, 150, printServices, defaultPrintService, null, attrib);
            if (selectedPrintService == null) {
                selectedPrintService = defaultPrintService;
            }

            PrinterOutputStream printerOutputStream = new PrinterOutputStream(selectedPrintService);
            EscPos escpos = new EscPos(printerOutputStream);
            escpos.writeLF(plantilla);
            escpos.feed(5);
            escpos.cut(EscPos.CutMode.FULL);
            escpos.close();
            printerOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
