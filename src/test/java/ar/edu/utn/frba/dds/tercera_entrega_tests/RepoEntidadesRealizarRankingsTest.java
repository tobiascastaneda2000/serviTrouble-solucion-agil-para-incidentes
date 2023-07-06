package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.RepoEntidades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepoEntidadesRealizarRankingsTest {

  RepoEntidades repoEntidades = new RepoEntidades();
  Entidad gualmayen;
  Entidad jorgito;
  Entidad quatar_aerolines;

  @BeforeEach
  void setUp() {

    gualmayen = mock(Entidad.class);
    jorgito = mock(Entidad.class);
    quatar_aerolines = mock(Entidad.class);
    repoEntidades.guardarEntidad(gualmayen);
    repoEntidades.guardarEntidad(gualmayen);
    repoEntidades.guardarEntidad(gualmayen);
    when(gualmayen.cantidadDeIncidentesReportados()).thenReturn(4);
    when(jorgito.cantidadDeIncidentesReportados()).thenReturn(20);
    when(quatar_aerolines.cantidadDeIncidentesReportados()).thenReturn(10);
    when(gualmayen.promedioDuracionIncidentes()).thenReturn(Duration.ofHours(12));
    when(jorgito.promedioDuracionIncidentes()).thenReturn(Duration.ofHours(4));
    when(quatar_aerolines.promedioDuracionIncidentes()).thenReturn(Duration.ofHours(20));
  }

  @Test
  void realizarRankingSegunCriterio() {
  }
}