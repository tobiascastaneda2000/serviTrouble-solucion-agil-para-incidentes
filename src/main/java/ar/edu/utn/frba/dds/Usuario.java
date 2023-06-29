package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import java.util.List;
import java.util.Set;

public class Usuario {
  public String usuario;
  public String contrasenia;
  int intentos;
  boolean sesionAbierta;
  public Notificador tipoNotificador;
  Localizacion localizacionInteres;
  List<Entidad> entidadesDeInteres;
  List<TipoServicio> serviciosDeInteres;
  List<Miembro> miembros;

  public Usuario(String nombre, String contrasenia, Notificador notificador) {
    this.usuario = nombre;
    this.contrasenia = contrasenia;
    this.intentos = 0;
    this.sesionAbierta = false;
    this.tipoNotificador = notificador;
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

  public List<TipoServicio> getServiciosDeInteres(){
    return getServiciosDeInteres();
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


  public void informarIncidente(TipoServicio servicio, String observaciones) {

    List<Comunidad> comunidades = miembros.stream().map(m->m.devolverComunidad()).toList();
    comunidades.forEach(c->c.agregarIncidente(new Incidente(servicio, observaciones)));
    comunidades.forEach(c->c.notificar("Nuevo Incidente",servicio));
  }

  public void cerrarIncidente(Incidente incidente){
    List<Comunidad> comunidades = miembros.stream().map(m->m.devolverComunidad()).toList();
    comunidades.forEach(c->c.cerrarIncidente(incidente));
  }
}
