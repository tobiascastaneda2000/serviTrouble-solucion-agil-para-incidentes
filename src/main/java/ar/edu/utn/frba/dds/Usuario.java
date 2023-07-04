package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.validaciones_password.SesionYaEstaAbiertaException;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
  public String usuario;
  public String contrasenia;

  public String correo;

  int intentos;
  boolean sesionAbierta;
  //public Notificador tipoNotificador;
  Localizacion localizacionInteres;
  List<Entidad> entidadesDeInteres;
  List<TipoServicio> serviciosDeInteres = new ArrayList<>();
  //List<Miembro> miembros;

  public Usuario(String nombre, String contrasenia, String correo) {
    this.usuario = nombre;
    this.contrasenia = contrasenia;
    this.intentos = 0;
    this.sesionAbierta = false;
    this.correo = correo;
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

  public List<TipoServicio> getServiciosDeInteres() {
    return this.serviciosDeInteres;
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


/*
  List<Comunidad> comunidadesPertenecientes(){
    return miembros.stream().map(m->m.devolverComunidad()).toList();
  }*/

  public List<Comunidad> comunidadesPertenecientes() {
    return RepositorioComunidades.getInstance().getComunidades().stream().filter(c -> c.contieneUsuario(this)).toList();
  }

  public String getCorreo() {
    return this.correo;
  }
}
