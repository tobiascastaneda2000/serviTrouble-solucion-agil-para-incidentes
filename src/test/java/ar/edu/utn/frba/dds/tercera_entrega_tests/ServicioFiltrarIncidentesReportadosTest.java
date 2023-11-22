package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;
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
  void servicoSinIncidentesNodevuelveNada() {
    Assertions.assertEquals(0, servicio.incidentesDe24Horas().size());
  }

  @Test
  @DisplayName("Servicio con 1 incidente lo devuelve")
  void servicioConIncidenteUnicoLoDevuelve() {
    servicio.aniadirIncidente(unIncidente);
    Assertions.assertEquals(servicio.getHistorialIncidentes(), servicio.incidentesDe24Horas());
  }

  @Test
  @DisplayName("Servicio con 2 incidentes, si el primero esta ABIERTO y plazo es MAYOR a 24 horas, devuelve ambos")
  void servicioConMuchosIncidentesAbiertosPLazoMayorA24Horas() {
    when(unIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY, 6, 0, 0));
    when(unIncidente.estaCerrado()).thenReturn(false);
    when(otroIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY, 8, 0, 0));
    when(unIncidente.estaCerrado()).thenReturn(false);
    servicio.aniadirIncidente(unIncidente);
    servicio.aniadirIncidente(otroIncidente);
    Assertions.assertEquals(servicio.incidentesDe24Horas().size(), 2);
  }

  @Test
  @DisplayName("Servicio con 2 incidentes, si el primero esta CERRADO, devuelve ambos")
  void servicioConMuchosIncidentesPlazoMenorA24HorasPrimerIncidenteCerrado() {
    when(unIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY, 6, 0, 0));
    when(unIncidente.estaCerrado()).thenReturn(true);
    when(otroIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY, 6, 5, 0));
    when(otroIncidente.estaCerrado()).thenReturn(false);
    servicio.aniadirIncidente(unIncidente);
    servicio.aniadirIncidente(otroIncidente);
    Assertions.assertTrue(servicio.incidentesDe24Horas().contains(unIncidente));
    Assertions.assertTrue(servicio.incidentesDe24Horas().contains(otroIncidente));
    Assertions.assertEquals(servicio.incidentesDe24Horas().size(), 2);
  }

  @Test
  @DisplayName("Servicio con 2 incidentes, si el primero esta ABIERTO y el plazo MENOR a 24 horas, filtra el segundo")
  void servicioConMuchosIncidentesAbiertosConPlazoMenorA24Horas() {
    when(unIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY, 6, 0, 0));
    when(unIncidente.estaCerrado()).thenReturn(false);
    when(otroIncidente.getFechaHoraAbre()).thenReturn(LocalDateTime.of(2023, Month.JULY, 6, 0, 1));
    when(otroIncidente.estaCerrado()).thenReturn(false);
    servicio.aniadirIncidente(unIncidente);
    servicio.aniadirIncidente(otroIncidente);
    Assertions.assertEquals(servicio.incidentesDe24Horas().size(), 1);
    Assertions.assertTrue(servicio.incidentesDe24Horas().contains(unIncidente));
    Assertions.assertFalse(servicio.incidentesDe24Horas().contains(otroIncidente));
  }


}
