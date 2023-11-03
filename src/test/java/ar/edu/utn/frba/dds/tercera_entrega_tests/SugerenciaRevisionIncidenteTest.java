package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.MainTareasPlanificadas;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioUbicacion;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Comunidad;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import java.util.Optional;
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

  @BeforeEach
  void setUp()  {
    usuarioInformante = new Usuario("a", "b", "contacto");
    usuario = new Usuario("a", "b", "mail@gmail.com");
    comunidad = new Comunidad("nombre");
    comunidad.registrarMiembro(usuario);
    comunidad.registrarMiembro(usuarioInformante);
    repositorioComunidades = RepositorioComunidades.getInstance();
    repositorioComunidades.add(comunidad);
    entidad = new Entidad("a","mail");
    establecimiento = new Establecimiento("nombre");
    entidad.agregarEstablecimiento(establecimiento);
    servicio = new Servicio("nombre",TipoServicio.ASCENSOR);
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
  public void borrar(){
    repositorioComunidades.getInstance().clean();
  }

  @Test
  @DisplayName("Notificar al miembro cercano")
  void notificarMiembroCercano() {
    boolean booleano = true;
    Incidente incidente = usuarioInformante.abrirIncidente(servicio,"obs");
    when(servicioUbicacion.estaCerca(usuario,incidente.getServicioAsociado())).thenReturn(booleano);
    when(servicioUbicacion.estaCerca(usuarioInformante,incidente.getServicioAsociado())).thenReturn(booleano);
    MainTareasPlanificadas.sugerirIncidentes();

    Assertions.assertEquals(comunidad.incidentes.size(),1);
    verify(medioNotificador,times(2)).notificarUnIncidente(any(),anyString());
  }
}
