package Usuario;

public class Usuario {
    String usuario;
    String contrasenia;
    int intentos;
    boolean sesionAbierta;

    public Usuario(String nombre, String contrasenia) {
        this.usuario = nombre;
        this.contrasenia = contrasenia;
        this.intentos = 3;
        this.sesionAbierta = false;
    }

    public String getUsername() {
        return this.usuario;
    }
    public int getIntentos(){return  this.intentos;}
    public String getContrasenia(){
        return this.contrasenia;
    }
    public boolean isSesionAbierta() {
        return sesionAbierta;
    }


    public void iniciarSesion(String username, String contrasenia){
        validarCantidadIntentos();
        if(coincideUsuarioYContrasenia(username, contrasenia)){
            validarSesionAbierta();
            sesionAbierta = true;
        }
        else {
            this.intentos--;
        }
    }

    private void validarSesionAbierta() {
        if(isSesionAbierta()){
            throw new SesionYaEstaAbiertaException("La sesion correspondiente ya se encuentra iniciada");
        }
    }

    private boolean coincideUsuarioYContrasenia(String username, String contrasenia) {
        return getUsername().equals(username) && getContrasenia().equals(contrasenia);
    }

    private void validarCantidadIntentos() {
        if(getIntentos()<=0){
            throw new MaxCantIntentosInicioSesionException("Se ha superado limite de intentos e inicio de sesion, espere unos minutos..");
        }
    }

}
