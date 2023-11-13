package ar.edu.utn.frba.dds.serviciolocalizacion;

import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.entidades.Servicio;

public interface ServicioUbicacion {

  // Interfaz que conecta API externa con nuestro sistema

  public Ubicacion ubicacionUsuario(Usuario usuario);
  public Ubicacion ubicacionEstablecimiento(Establecimiento establecimiento);
  public boolean estaCerca(Usuario usuario, Servicio servicio);
}
