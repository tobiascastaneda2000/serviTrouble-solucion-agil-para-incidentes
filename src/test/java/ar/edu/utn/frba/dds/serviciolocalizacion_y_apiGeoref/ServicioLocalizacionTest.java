package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

class ServicioLocalizacionTest {
  ServicioLocalizacion servicio;
  private final int totalProvincias = 24;
  private final int totalMunicipios = 10;
  private final int totalDepartamentos = 10;

  @BeforeEach
  void setUp(){
    servicio = mock(ServicioLocalizacion.class);
  }

  public List<Provincia> generarListaTotalProvincias(){
    List<Provincia> provincias = new ArrayList<>();
    for (int i = 0; i < totalProvincias; i++) {
      provincias.add( new Provincia() );
    }
    return provincias;
  }

  public List<Municipio> generarListaTotalMunicipios(){
    List<Municipio> municipios = new ArrayList<>();
    for (int i = 0; i < totalMunicipios; i++) {
      municipios.add( new Municipio() );
    }
    return municipios;
  }

  public List<Departamento> generarListaTotalDepartamentos(){
    List<Departamento> departamentos = new ArrayList<>();
    for (int i = 0; i < totalDepartamentos; i++) {
      departamentos.add( new Departamento() );
    }
    return departamentos;
  }

  @Test
  @DisplayName("Consultar por todas las provincias devuelve una lista de 24 provincias")
  void solicitarTodasLasProvinciasDevuelveLista24Provincias() throws IOException {
    when(servicio.getProvincias()).thenReturn(this.generarListaTotalProvincias());
    assertTrue( servicio.getProvincias().size() == totalProvincias );
    assertTrue( servicio.getProvincias().stream().allMatch( provincia -> provincia.getClass().getSimpleName().equals("Provincia")));
  }

  @Test
  @DisplayName("Consultar por todos los municipios devuelve una lista de 10 municipios")
  void solicitarTodasLosMunicipios() throws IOException {
    when(servicio.getMunicipios()).thenReturn(this.generarListaTotalMunicipios());
    assertTrue( servicio.getMunicipios().size() == totalMunicipios );
    assertTrue( servicio.getMunicipios().stream().allMatch( municipio -> municipio.getClass().getSimpleName().equals("Municipio")));
  }

  @Test
  @DisplayName("Consultar por todos los departamentos devuelve una lista de 10 departamentos")
  void solicitarTodasLosDepartamentos() throws IOException {
    when(servicio.getDepartamentos()).thenReturn(this.generarListaTotalDepartamentos());
    assertTrue( servicio.getDepartamentos().size() == totalDepartamentos );
    assertTrue( servicio.getDepartamentos().stream().allMatch( departamento -> departamento.getClass().getSimpleName().equals("Departamento")));
  }

}