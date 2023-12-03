package ar.edu.utn.frba.dds.serviciolocalizacion;

import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.entidades.Servicio;

public class ServicioUbicacion1 implements ServicioUbicacion{


  @Override
  public Ubicacion ubicacionUsuario(Usuario usuario) {
    return null;
  }

  @Override
  public Ubicacion ubicacionEstablecimiento(Establecimiento establecimiento) {
    return null;
  }

  @Override
  public boolean estaCerca(Usuario usuario, Servicio servicio) {
    return true;
  }
}
