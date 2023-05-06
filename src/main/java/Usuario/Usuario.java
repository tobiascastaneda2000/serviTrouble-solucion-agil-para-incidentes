package Usuario;

import java.util.Objects;

public class Usuario {
    String nombre;
    String contrasenia;
    int intentos;
    boolean sesionAbierta;

    public Usuario(String nombre, String contrasenia) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.intentos = 3;
        this.sesionAbierta = false;
    }

    public String getNombre() {
        return this.nombre;
    }
    public String getContrasenia(){
        return this.contrasenia;
    }

    public boolean isSesionAbierta() {
        return sesionAbierta;
    }

    public void iniciarSesion(String username, String contrasenia){
        if(this.intentos==0){
            throw new RuntimeException("Se ha superado limite de intentos e inicio de sesion");
        }
        if(this.nombre.equals( username) && this.contrasenia.equals(contrasenia)){
            sesionAbierta = true;
        }
        else {
            this.intentos--;
        }
    }

}
