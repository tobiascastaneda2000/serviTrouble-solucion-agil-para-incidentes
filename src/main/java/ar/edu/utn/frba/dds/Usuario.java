package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.Notificacion;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioGeoRef;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioLocalizacion;
import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.validaciones_password.SesionYaEstaAbiertaException;
import ar.edu.utn.frba.dds.notificador.Horario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Usuario {
  private int id;

  public String usuario;
  public String contrasenia;

  public String contacto;
  int intentos;
  boolean sesionAbierta;

  List<Entidad> estidadesInteres;
  List<Servicio> serviciosDeInteres;
  List<Entidad> entidadesInteres = new ArrayList<>();

  List<Notificacion> notificacionesPendientes = new ArrayList<>();
  ServicioLocalizacion servicioLocalizacion;

  private Set<Horario> horariosPlanificados;

  // ---------------------------------SETTERS Y GETTERS----------------------------------------------------//

  public void setMedioNotificador(MedioNotificador medioNotificador) {
    this.medioNotificador = medioNotificador;
  }

  public MedioNotificador getMedioNotificador() {
    return medioNotificador;
  }

  public MedioNotificador medioNotificador;

  public Usuario(int id, String nombre, String contrasenia, String contacto) {
    this.id = id;
    this.usuario = nombre;
    this.contrasenia = contrasenia;
    this.intentos = 0;
    this.sesionAbierta = false;
    this.contacto = contacto;
  }

  public void setHorariosPlanificados(Set<Horario> horariosPlanificados) {
    this.horariosPlanificados = horariosPlanificados;
  }

  public Set<Horario> getHorariosPlanificados() {
    return horariosPlanificados;
  }

  public int getId() {
    return id;
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

  public void setServicioLocalizacion(ServicioLocalizacion servicioLocalizacion) {
    this.servicioLocalizacion = servicioLocalizacion;
  }

  public boolean isSesionAbierta() {
    return sesionAbierta;
  }

  public String getContacto() {
    return this.contacto;
  }

  public List<Servicio> serviciosDeInteres() {
    return this.serviciosDeInteres();
  }

  public List<Notificacion> getNotificacionesPendientes(){
    return notificacionesPendientes;
  }


  // -----------------------------------------INICIO DE SESION--------------------------------------//

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

  //------------------------------------------------------------------------------------------------------//

  public List<Comunidad> comunidadesPertenecientes() {
    return RepositorioComunidades.getInstance().getComunidades().stream().filter(c -> c.contieneUsuario(this)).toList();
  }



  //--------------------------------APERTURA DE INCIDENTE PARA COMUNIDADES----------------------------------------------//

  public void abrirIncidente(Servicio servicio,String observacion) {
    List<Comunidad> comunidades = comunidadesPertenecientes().stream().filter(c->c.contieneServicioDeInteres(servicio)).toList();
    Incidente incidente = new Incidente(observacion, servicio);
    servicio.aniadirIncidente(incidente); //Para ranking
    comunidades.forEach(c->c.abrirIncidente(incidente));

  }
  //--------------------------------------------------------------------------------------------------------------------//


  //-----------------------------------NOTIFICAR INCIDENTE--------------------------------------------------------------//
  public void notificarIncidente(Incidente incidente) {
    this.medioNotificador.notificar(incidente, this.contacto);
  }

  public void agregarHorario(Horario horario) {
    horariosPlanificados.add(horario);
  }

  public void verificarNotificaciones(LocalDateTime ahora) {

    int hora = ahora.getHour();
    int minutos = ahora.getMinute();
    if(horariosPlanificados.stream().anyMatch(h->h.esIgual(hora,minutos))){
      notificacionesPendientes.forEach(n->n.ejecutarse());
    }
  }

  public void guardarNotificacion(Incidente incidente) {
    Notificacion notificacion = new Notificacion(this,incidente);
    notificacionesPendientes.add(notificacion);
  }

  public void sacarNotificacion(Notificacion notificacion) {
    notificacionesPendientes.remove(notificacion);
  }

  public boolean contieneIncidentePendiente(Incidente incidente) {
    List<Incidente> incidentes = notificacionesPendientes.stream().map(n->n.getIncidente()).toList();
    if(incidentes.contains(incidente)){
      return true;
    }
    else{
      return false;
    }
  }

  public Notificacion obtenerNotificacion(Incidente incidente) {

    return notificacionesPendientes.stream().filter(n->n.getIncidente().equals(incidente)).toList().get(0);
  }


  //------------------------------------------------------------------------------------------------------------//

  //-----------------------------SUGERENCIA REVISION INCIDENTES------------------------------------------//

  ServicioUbicacion servicioUbicacion;
  public void setServicioUbicacion(ServicioUbicacion servicioUbicacion) {
    this.servicioUbicacion = servicioUbicacion;
  }
  public void notificarSiEstaCerca(Incidente incidente){
    if(servicioUbicacion.estaCerca(this,incidente.getServicioAsociado())){
      notificarIncidente(incidente);
    }
  }



}

