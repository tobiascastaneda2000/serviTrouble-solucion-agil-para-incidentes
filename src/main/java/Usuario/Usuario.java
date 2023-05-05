package Usuario;

public class Usuario {
    String nombre;
    String contrasenia;

    public Usuario(String nombre, String contrasenia) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return this.nombre;
    }
    public String getContrasenia(){
        return this.contrasenia;
    }
}
