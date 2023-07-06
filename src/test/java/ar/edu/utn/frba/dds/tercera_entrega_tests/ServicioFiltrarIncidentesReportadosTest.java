package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServicioFiltrarIncidentesReportadosTest {
  Servicio servicio;
  Incidente unIncidente;
  Incidente otroIncidente;

  @BeforeEach
  void setUp() {
    servicio = new Servicio(TipoServicio.ASCENSOR);
    unIncidente = mock(Incidente.class);
    otroIncidente = mock(Incidente.class);

  }

  @Test
  @DisplayName("Servicio sin incidentes no devuelve nada")
  void servicoSinIncidentesNodevuelveNada(){
    Assertions.assertEquals(0, servicio.incidentesDe24Horas().size());
  }

  @Test
  @DisplayName("Servicio con 1 incidente lo devuelve")
  void servicioConIncidenteUnicoLoDevuelve() {
    servicio.actualizarEstadoServicio(unIncidente);
    Assertions.assertEquals(servicio.getHistorialIncidentes(), servicio.incidentesDe24Horas());
  }

  @Test
  @DisplayName("Servicio con 2 incidentes, si el anterior NO esta cerrado y plazo es mayor a 24 horas, devuelve ambos")
  void servicioConMuchosIncidentesAbiertosPLazoMayorA24Horas(){
    when(unIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY,6,0,0));
    when(unIncidente.estaCerrado()).thenReturn(false);
    when(otroIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY,8,0,0));
    when(unIncidente.estaCerrado()).thenReturn(false);
    servicio.actualizarEstadoServicio(unIncidente);
    servicio.actualizarEstadoServicio(otroIncidente);
    Assertions.assertEquals(servicio.incidentesDe24Horas().size(), 2);
  }

  @Test
  @DisplayName("Servicio con 2 incidentes, si el anterior esta cerrado y el plazo menor a 24 horas, devuelve ambos")
  void servicioConMuchosIncidentesPlazoMenorA24HorasPrimerIncidenteCerrado(){
    when(unIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY,6,0,0));
    when(unIncidente.estaCerrado()).thenReturn(true);
    when(otroIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY,6,5,0));
    when(otroIncidente.estaCerrado()).thenReturn(false);
    servicio.actualizarEstadoServicio(unIncidente);
    servicio.actualizarEstadoServicio(otroIncidente);
    Assertions.assertTrue(servicio.incidentesDe24Horas().contains(unIncidente));
    Assertions.assertTrue(servicio.incidentesDe24Horas().contains(otroIncidente));
    Assertions.assertEquals(servicio.incidentesDe24Horas().size(), 2);
  }

  @Test
  @DisplayName("Servicio con 2 incidentes, si el anterior NO esta cerrado y el plazo menor a 24 horas, filtra el segundo")
  void servicioConMuchosIncidentesAbiertosConPlazoMenorA24Horas(){
    when(unIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY,6,0,0));
    when(unIncidente.estaCerrado()).thenReturn(false);
    when(otroIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY,6,0,1));
    when(otroIncidente.estaCerrado()).thenReturn(false);
    servicio.actualizarEstadoServicio(unIncidente);
    servicio.actualizarEstadoServicio(otroIncidente);
    Assertions.assertEquals(servicio.incidentesDe24Horas().size(), 1);
    Assertions.assertTrue(servicio.incidentesDe24Horas().contains(unIncidente));
    Assertions.assertFalse(servicio.incidentesDe24Horas().contains(otroIncidente));
  }


}