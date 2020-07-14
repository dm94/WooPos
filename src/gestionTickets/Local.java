package gestionTickets;

public class Local {

    private String nombre;
    private String direccion;
    private String cif;
    private String telefono;

    public Local(String nombre, String direccion, String cif, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cif = cif;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
