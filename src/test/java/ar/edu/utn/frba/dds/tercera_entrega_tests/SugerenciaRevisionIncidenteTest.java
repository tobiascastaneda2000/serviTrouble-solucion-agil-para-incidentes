package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.Main;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.ServicioUbicacion;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Miembro;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Departamento;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioGeoRef;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioLocalizacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SugerenciaRevisionIncidenteTest {

  Usuario usuario;
  Usuario usuarioInformante;
  Establecimiento establecimiento;
  Entidad entidad;
  Servicio servicio;
  Comunidad comunidad;
  ServicioUbicacion servicioUbicacion;
  MedioNotificador medioNotificador;

  @BeforeEach
  void setUp() throws IOException {
    usuarioInformante = new Usuario(2, "a", "b", "mail@gmail.com");
    usuario = new Usuario(1, "a", "b", "mail@gmail.com");
    comunidad = new Comunidad();
    comunidad.registrarMiembro(usuario);
    comunidad.registrarMiembro(usuarioInformante);
    RepositorioComunidades repositorioComunidades = RepositorioComunidades.instance;
    repositorioComunidades.guardarComunidad(comunidad);
    entidad = new Entidad(1,"a","mail");
    establecimiento = new Establecimiento();
    entidad.agregarEstablecimiento(establecimiento);
    servicio = new Servicio(TipoServicio.ASCENSOR);
    comunidad.aniadirServicioInteres(servicio);
    establecimiento.agregarServicio(servicio);
    servicioUbicacion = mock(ServicioUbicacion.class);
    medioNotificador = mock(MedioNotificador.class);
    usuarioInformante.setMedioNotificador(medioNotificador);
    usuarioInformante.setServicioUbicacion(servicioUbicacion);
    usuario.setMedioNotificador(medioNotificador);
    usuario.setServicioUbicacion(servicioUbicacion);


  }

  @Test
  @DisplayName("Notificar al miembro cercano")
  void notificarMiembroCercano() {
    boolean booleano = true;
    Incidente incidente = usuarioInformante.abrirIncidente(servicio,"obs");
    when(servicioUbicacion.estaCerca(usuario,incidente.getServicioAsociado())).thenReturn(booleano);
    Main.sugerirIncidentes();

    Assertions.assertEquals(comunidad.incidentes.size(),1);
    Assertions.assertEquals(usuario.getLogNotificaciones().size(),1);
  }
}
