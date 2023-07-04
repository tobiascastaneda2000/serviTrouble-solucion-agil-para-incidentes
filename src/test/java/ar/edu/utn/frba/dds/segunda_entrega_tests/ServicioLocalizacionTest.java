package ar.edu.utn.frba.dds.segunda_entrega_tests;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ApiGeoRef;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Provincia;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioLocalizacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.io.IOException;
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
    /*call = apit.getListData(listadoProvincias);
    when(api.getProvincias()).thenReturn(call);*/
  }

  @Test
  void primerTest() throws IOException {
    Assertions.assertEquals(servicio.getProvincias(), Arrays.asList(jujuy));
  }
}
