package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Localizacion.Departamento;
import ar.edu.utn.frba.dds.Localizacion.Municipio;
import ar.edu.utn.frba.dds.Localizacion.Provincia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.CallAdapter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioLocalizacionTest {
  ServicioLocalizacion servicio;
  ApiGeoRef api;
  Provincia jujuy;
  List<Provincia> listadoProvincias;
  Call<List<Provincia>> call;

  @BeforeEach
  void setUp() {
    jujuy = new Provincia();
    servicio = new ServicioLocalizacion("https://apis.datos.gob.ar/georef/api/");
    api = mock(ApiGeoRef.class);
    listadoProvincias = Arrays.asList(jujuy);
    call = api.getListData(listadoProvincias);
    when(api.getProvincias()).thenReturn(call);
  }

  @Test
  void primerTest() throws IOException {
    Assertions.assertEquals(servicio.getProvincias(), Arrays.asList(jujuy));
  }
}
