package ar.edu.utn.frba.dds.usuario;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import ar.edu.utn.frba.dds.usuario.exceptions.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.usuario.exceptions.SesionYaEstaAbiertaException;

import java.util.List;

public class Usuario {
  public String usuario;
  public String contrasenia;
  int intentos;
  boolean sesionAbierta;

  Localizacion localizacionInteres;
  List<Entidad> entidadesDeInteres;
  List<TipoServicio> serviciosDeInteres;

  public Usuario(String nombre, String contrasenia) {
    this.usuario = nombre;
    this.contrasenia = contrasenia;
    this.intentos = 0;
    this.sesionAbierta = false;
  }

  public String getUsername() {
    return this.usuario;
  }

  public int getIntentos() {
    return this.intentos;
  }

  public String getContrasenia() {
    return this.contrasenia;
  }

  public boolean isSesionAbierta() {
    return sesionAbierta;
  }


  public void iniciarSesion(String username, String contrasenia) {
    validarSesionAbierta();
    validarCantidadIntentos();
    if (coincideUsuarioYContrasenia(username, contrasenia)) {
      sesionAbierta = true;
    } else {
      this.intentos++;
    }
  }

  private void validarSesionAbierta() {
    if (isSesionAbierta()) {
      throw new SesionYaEstaAbiertaException("La sesion correspondiente ya se encuentra iniciada");
    }
  }

  private boolean coincideUsuarioYContrasenia(String username, String contrasenia) {
    return getUsername().equals(username) && getContrasenia().equals(contrasenia);
  }

  private void validarCantidadIntentos() {
    if (getIntentos() >= 3) {
      throw new MaxCantIntentosInicioSesionException("Se ha superado limite de intentos e inicio de sesion, espere unos minutos..");
    }
  }


  //public accederServiciosCercanos(){}
  //Debe devolver los servicios que tiene en la misma localizacion

}
