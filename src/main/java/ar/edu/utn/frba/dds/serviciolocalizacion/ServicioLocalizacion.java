package ar.edu.utn.frba.dds.serviciolocalizacion;

import java.io.IOException;
import java.util.List;

public interface ServicioLocalizacion {
  public List<Provincia> getProvincias() throws IOException;

  public List<Municipio> getMunicipios() throws IOException;

  public List<Departamento> getDepartamentos() throws IOException;

}
