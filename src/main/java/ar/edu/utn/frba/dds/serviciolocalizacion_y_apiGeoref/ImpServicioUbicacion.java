package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;

public class ImpServicioUbicacion implements ServicioUbicacion{

  public Ubicacion ubicacionUsuario(Usuario usuario){
    return new Ubicacion(-27.2741,-66.7529);
  }
  public Ubicacion ubicacionEstablecimiento(Establecimiento establecimiento){
    return new Ubicacion(-27.2741,-66.7529);
  }

  public Ubicacion ubicacionServicio(Servicio servicio){
    return new Ubicacion(-27.2741,-66.7529);
  }

  public boolean estaCerca(Usuario usuario, Servicio servicio){
    return this.ubicacionUsuario(usuario).mismaUbicacion(ubicacionServicio(servicio));
  }

}
