package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioLocalizacion;
import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.validaciones_password.SesionYaEstaAbiertaException;
import ar.edu.utn.frba.dds.notificador.Horario;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Usuario {
  public String usuario;
  public String contrasenia;

  public String correo;

  int intentos;
  boolean sesionAbierta;

  List<Entidad> estidadesInteres;

  List<Servicio> serviciosDeInteres;

  private Set<Horario> horariosPlanificados;

  public void setMedioNotificador(MedioNotificador medioNotificador) {
    this.medioNotificador = medioNotificador;
  }

  public MedioNotificador getMedioNotificador() {
    return medioNotificador;
  }

  public MedioNotificador medioNotificador;

  public void setServicioLocalizacion(ServicioLocalizacion servicioLocalizacion) {
    this.servicioLocalizacion = servicioLocalizacion;
  }

  public ServicioLocalizacion servicioLocalizacion;

  public Usuario(String nombre, String contrasenia, String correo) {
    this.usuario = nombre;
    this.contrasenia = contrasenia;
    this.intentos = 0;
    this.sesionAbierta = false;
    this.correo = correo;
  }

  public void setHorariosPlanificados(Set<Horario> horariosPlanificados) {
    this.horariosPlanificados = horariosPlanificados;
  }

  public Set<Horario> getHorariosPlanificados() {
    return horariosPlanificados;
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

  public Localizacion getLocalizacionInteres() throws IOException {
    //Necesitariamos pasarle como parametro al miembro o algun dato del mismo

    return this.servicioLocalizacion.getDepartamentos().stream().toList().get(0);
  }

  public boolean isSesionAbierta() {
    return sesionAbierta;
  }

  //INICIO DE SESION
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



  public void ejecutarNotificaciones() {

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

  public List<Servicio> serviciosDeInteres() {
    return this.serviciosDeInteres();
  }

  public void notificar(Incidente incidente) {
    this.medioNotificador.notificar(incidente);
  }

}

