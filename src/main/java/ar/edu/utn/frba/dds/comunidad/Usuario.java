package ar.edu.utn.frba.dds.comunidad;

import ar.edu.utn.frba.dds.ServicesLocators.ServiceLocatorMedioNotificador;
import ar.edu.utn.frba.dds.ServicesLocators.ServiceLocatorUbicacion;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.Notificacion;
import ar.edu.utn.frba.dds.serviciolocalizacion.Localizacion;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioLocalizacion;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion;
import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.notificador.Horario;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import spark.Request;
import spark.Response;

@Entity
public class Usuario implements WithSimplePersistenceUnit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long id;

  public String usuario;
  public String contrasenia;

  public String contacto;

  int intentos;

  @ManyToOne(cascade = CascadeType.PERSIST)
  public MedioNotificador medioNotificador;

  @ManyToMany
  Set<Entidad> entidadesInteres = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "usuario_id")
  List<Notificacion> notificacionesPendientes = new ArrayList<>();
  @Transient
  ServicioLocalizacion servicioLocalizacion;
  @Transient
  ServicioUbicacion servicioUbicacion;
  @ElementCollection
  @JoinColumn(name = "usuario_id")
  public Set<Horario> horariosPlanificados = new HashSet<>();

  @Enumerated(EnumType.ORDINAL)
  public PermisoUsuario permisoUsuario;

  ///---------------------CONSTRUCTOR----------------------------------///
  protected Usuario() {
  }

  public Usuario(String nombre, String contrasenia, String contacto) {
    this.usuario = nombre;
    this.contrasenia = contrasenia;
    this.intentos = 0;
    this.contacto = contacto;
    this.permisoUsuario = PermisoUsuario.USUARIO_COMUN;
  }

  @PostLoad
  public void hidratarUsuario(){
    this.servicioUbicacion = ServiceLocatorUbicacion.getServicio("servicioUbicacion");
  }


  // ---------------------------------SETTERS Y GETTERS----------------------------------------------------//

  public void setMedioNotificador(MedioNotificador medioNotificador) {
    this.medioNotificador = medioNotificador;
  }

  public MedioNotificador getMedioNotificador() {
    return medioNotificador;
  }

  public void setHorariosPlanificados(Set<Horario> horariosPlanificados) {
    this.horariosPlanificados = horariosPlanificados;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

  public void setUsername(String username) {
    this.usuario = username;
  }

  public void setContacto(String contacto) {
    this.contacto = contacto;
  }

  public Set<Entidad> getEntidadesInteres() {
    return entidadesInteres;
  }

  public Set<Horario> getHorariosPlanificados() {
    return horariosPlanificados;
  }

  public long getId() {
    return id;
  }

  public List<Notificacion> getNotificaciones() {
    return this.notificacionesPendientes;
  }

  public String getUsuario() {
    return this.usuario;
  }

  public int getIntentos() {
    return this.intentos;
  }

  public PermisoUsuario getPermisoUsuario() {
    return permisoUsuario;
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

  public String getContacto() {
    return this.contacto;
  }

  public List<Notificacion> getNotificacionesPendientes() {
    return notificacionesPendientes;
  }

  public List<Miembro> obtenerMiembros() {
    List<Comunidad> comunidades = RepositorioComunidades.getInstance().getAll();
    List<Miembro> miembros = comunidades.stream().map(c -> c.miembros).flatMap(m -> m.stream()).toList();
    return miembros.stream().filter(m -> m.usuario.equals(this)).toList();
  }

  public boolean esAdmin() {
    return this.permisoUsuario.equals(PermisoUsuario.ADMIN);
  }


  // -----------------------------------------INICIO DE SESION--------------------------------------//

  public void iniciarSesion(String username, String contrasenia) {
    validarCantidadIntentos();
    if (!coincideUsuarioYContrasenia(username, contrasenia)) {
      this.intentos++;
    }
  }

    public static Long redirigirSesionNoIniciada(Request request, Response response){
        if (request.session().attribute("user_id")==null) response.redirect("/");
        return request.session().attribute("user_id");
    }

  private boolean coincideUsuarioYContrasenia(String username, String contrasenia) {
    return getUsuario().equals(username) && getContrasenia().equals(contrasenia);
  }

  private void validarCantidadIntentos() {
    if (getIntentos() >= 3) {
      throw new MaxCantIntentosInicioSesionException("Se ha superado limite de intentos e inicio de sesion, espere unos minutos..");
    }
  }

  //------------------------------------------------------------------------------------------------------//

  public List<Comunidad> comunidadesPertenecientes() {
    return RepositorioComunidades.getInstance().getAll().stream().filter(c -> c.contieneUsuario(this)).toList();
  }


  //--------------------------------APERTURA DE INCIDENTE PARA COMUNIDADES----------------------------------------------//

  public Incidente abrirIncidente(Servicio servicio, String observacion) {

    String obs = observacion.substring(0, 1).toUpperCase() + observacion.substring(1);
    List<Comunidad> comunidades = comunidadesPertenecientes().stream().filter(c -> c.contieneServicioDeInteres(servicio)).toList();
    Incidente incidente = new Incidente(obs, servicio);
    servicio.aniadirIncidente(incidente); //Para ranking
    comunidades.forEach(c -> c.abrirIncidenteEnComunidad(obs, servicio));
    return incidente;
  }
  //--------------------------------------------------------------------------------------------------------------------//


  //-----------------------------------NOTIFICAR INCIDENTE--------------------------------------------------------------//
  public void notificarIncidente(Incidente incidente) {
    this.medioNotificador.notificarUnIncidente(incidente, this.contacto);
  }

  public void agregarHorario(Horario horario) {
    horariosPlanificados.add(horario);
  }

  public void sacarHorario(Horario horario) {
    horariosPlanificados.remove(horario);
  }

  public void verificarNotificaciones(Horario ahora) {

    if (horariosPlanificados.stream().anyMatch(h -> h.equals(ahora))) {
      notificacionesPendientes.stream().filter(n -> !n.fueNotificada).forEach(
          n -> n.ejecutarse(this));
    }
  }

  public void guardarNotificacion(Incidente incidente) {
    Notificacion notificacion = new Notificacion(this, incidente);
    notificacionesPendientes.add(notificacion);
    persist(incidente);
    persist(notificacion);
  }

  public Notificacion obtenerNotificacion(Incidente incidente) {

    return notificacionesPendientes.stream().filter(n -> n.getIncidente().equals(incidente)).toList().get(0);
  }

  public void deleteNotificacion(Notificacion notificacion){
    notificacionesPendientes.remove(notificacion);
  }

  //------------------------------------------------------------------------------------------------------------//

  //-----------------------------SUGERENCIA REVISION INCIDENTES------------------------------------------//


  public void setServicioUbicacion(ServicioUbicacion servicioUbicacion) {
    this.servicioUbicacion = servicioUbicacion;
  }

  public ServicioUbicacion getServicioUbicacion() {
    return servicioUbicacion;
  }

  public void notificarSiEstaCerca(Incidente incidente) {
    if (esIncidenteCercano(incidente)) {
      notificarIncidente(incidente);
    }
  }

  public boolean esIncidenteCercano(Incidente incidente) {
    return this.servicioUbicacion.estaCerca(this, incidente.getServicioAsociado());
  }

  public List<Incidente> obtenerIncidentesCercanos(List<Incidente> incidentesAbiertos) {
    incidentesAbiertos.stream().filter(incidente -> esIncidenteCercano(incidente));
    return incidentesAbiertos;
  }

}

