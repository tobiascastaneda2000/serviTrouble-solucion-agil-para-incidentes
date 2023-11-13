package ar.edu.utn.frba.dds.serviciolocalizacion;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiGeoRef {
  @GET("/provincias")
  Call<List<Provincia>> getProvincias();

  @GET("/departamentos")
  Call<List<Departamento>> getDepartamentos();

  @GET("/municipios")
  Call<List<Municipio>> getMunicipios();

}
