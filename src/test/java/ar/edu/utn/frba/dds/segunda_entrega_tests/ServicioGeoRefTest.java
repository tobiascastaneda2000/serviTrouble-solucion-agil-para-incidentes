package ar.edu.utn.frba.dds.segunda_entrega_tests;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ApiGeoRef;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Provincia;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioGeoRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioGeoRefTest {
  ServicioGeoRef servicio;
  ApiGeoRef api;
  Provincia jujuy;
  List<Provincia> listadoProvincias;
  Call<List<Provincia>> call;

  @BeforeEach
  void setUp() {
    jujuy = new Provincia();
    servicio = new ServicioGeoRef("https://apis.datos.gob.ar/georef/api/");
    api = mock(ApiGeoRef.class);
    listadoProvincias = Arrays.asList(jujuy);
    call = api.getProvincias();
    when(api.getProvincias()).thenReturn(call);
  }
/*
  @Test
  void primerTest() throws IOException {
    Assertions.assertEquals(servicio.getProvincias(), Collections.singletonList(jujuy));
  }*/
}
