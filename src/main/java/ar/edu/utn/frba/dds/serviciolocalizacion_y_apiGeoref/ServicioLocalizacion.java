package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public interface ServicioLocalizacion {
  public List<Provincia> getProvincias() throws IOException;

  public List<Municipio> getMunicipios() throws IOException;

  public List<Departamento> getDepartamentos() throws IOException;

}
