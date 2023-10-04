package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;

public interface ServicioUbicacion {

  // Interfaz que conecta API externa con nuestro sistema

  public Ubicacion ubicacionUsuario(Usuario usuario);
  public Ubicacion ubicacionEstablecimiento(Establecimiento establecimiento);
  public boolean estaCerca(Usuario usuario, Servicio servicio);
}
