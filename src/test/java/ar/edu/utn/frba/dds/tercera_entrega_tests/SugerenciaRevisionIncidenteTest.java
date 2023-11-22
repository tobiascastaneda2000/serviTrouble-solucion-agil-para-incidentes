package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.SugerenciaProgramado;
import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
  RepositorioComunidades repositorioComunidades;
/*
  @BeforeEach
  void setUp() {
    usuarioInformante = new Usuario("a", "b", "contacto");
    usuario = new Usuario("a", "b", "mail@gmail.com");
    comunidad = new Comunidad("nombre");
    comunidad.registrarMiembro(usuario);
    comunidad.registrarMiembro(usuarioInformante);
    repositorioComunidades = RepositorioComunidades.getInstance();
    repositorioComunidades.add(comunidad);
    entidad = new Entidad("a", "mail");
    establecimiento = new Establecimiento("nombre");
    entidad.agregarEstablecimiento(establecimiento);
    servicio = new Servicio("nombre", TipoServicio.ASCENSOR);
    comunidad.aniadirServicioInteres(servicio);
    establecimiento.agregarServicio(servicio);
    servicioUbicacion = mock(ServicioUbicacion.class);
    medioNotificador = mock(MedioNotificador.class);
    usuarioInformante.setMedioNotificador(medioNotificador);
    usuarioInformante.setServicioUbicacion(servicioUbicacion);
    usuario.setMedioNotificador(medioNotificador);
    usuario.setServicioUbicacion(servicioUbicacion);

  }

  @AfterEach
  public void borrar() {
    repositorioComunidades.getInstance().clean();
  }

  @Test
  @DisplayName("Notificar al miembro cercano")
  void notificarMiembroCercano() {
    boolean booleano = true;
    Incidente incidente = usuarioInformante.abrirIncidente(servicio, "obs");
    when(servicioUbicacion.estaCerca(usuario, incidente.getServicioAsociado())).thenReturn(booleano);
    when(servicioUbicacion.estaCerca(usuarioInformante, incidente.getServicioAsociado())).thenReturn(booleano);
    new SugerenciaProgramado().run();

    Assertions.assertEquals(comunidad.incidentes.size(), 1);
    verify(medioNotificador, times(2)).notificarUnIncidente(any(), anyString());
  }*/
}
