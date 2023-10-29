package ar.edu.utn.frba.dds.segunda_entrega_tests;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ApiGeoRef;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Departamento;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Provincia;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioGeoRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioGeoRefTest {
  ServicioGeoRef servicio;
  ApiGeoRef api;
  List<Departamento> listadoProvincias;
  Call<List<Provincia>> call;
  Departamento jujuy;

  Usuario usuario;

  @BeforeEach
  void setUp() throws IOException {
    usuario = new Usuario("Pepe", "jfisjfijs", "mail");

    jujuy = new Departamento();
    listadoProvincias = Collections.singletonList(jujuy);
    servicio = mock(ServicioGeoRef.class);
    when(servicio.getDepartamentos()).thenReturn(listadoProvincias);
    usuario.setServicioLocalizacion(servicio);

    /*
    api = mock(ApiGeoRef.class);

    call = api.getProvincias();
    when(api.getProvincias()).thenReturn(call);*/
  }

  @Test
  @DisplayName("Un usuario puede llamar a un servicio Localizacion")
  void usuarioLlamaAServicioLocalizacion() throws IOException {
    Assertions.assertEquals(jujuy, usuario.getLocalizacionInteres());
  }

  //Testamos usuario, pero no el adapter -> mockear api
}
