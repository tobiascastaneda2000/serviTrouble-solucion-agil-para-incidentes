package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DevolverEntidadesOrdenadasSegunCriterioTest {

  RepoEntidades repoEntidades = new RepoEntidades();
  Entidad gualmayen;
  Entidad jorgito;
  Entidad quatar_aerolines;

  CriterioRanking rankingPromedio ;
  CriterioRanking rankingCantidadReportes;

  @BeforeEach
  void setUp() {

    gualmayen = mock(Entidad.class);
    jorgito = mock(Entidad.class);
    quatar_aerolines = mock(Entidad.class);
    repoEntidades.guardarEntidad(gualmayen);
    repoEntidades.guardarEntidad(jorgito);
    repoEntidades.guardarEntidad(quatar_aerolines);

    when(gualmayen.cantidadDeIncidentesReportados()).thenReturn(4);
    when(quatar_aerolines.cantidadDeIncidentesReportados()).thenReturn(10);
    when(jorgito.cantidadDeIncidentesReportados()).thenReturn(20);


    when(jorgito.promedioDuracionIncidentes()).thenReturn(Duration.ofHours(4));
    when(gualmayen.promedioDuracionIncidentes()).thenReturn(Duration.ofHours(12));
    when(quatar_aerolines.promedioDuracionIncidentes()).thenReturn(Duration.ofHours(20));

    rankingPromedio = new PromedioCierresSemanal();
    rankingCantidadReportes = new CantidadReportesSemanal();
  }

  @Test
  @DisplayName("Ordenar segun promedios de cierre")
  void realizarRankingSegunPromedios() {
    Assertions.assertEquals(repoEntidades.realizarRankingSegunCriterio(rankingPromedio), List.of(jorgito, gualmayen,quatar_aerolines));
  }

  @Test
  @DisplayName("Ordenar segun cantidad de reportes")
  void realizarRankingSegunCantidadReportes() {
    Assertions.assertEquals(repoEntidades.realizarRankingSegunCriterio(rankingCantidadReportes), List.of(gualmayen, quatar_aerolines,jorgito));
  }
}