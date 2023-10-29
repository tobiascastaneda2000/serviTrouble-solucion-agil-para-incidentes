package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.mock;

class RankingEntidadesSegunCriterioTest {

  RepoEntidades repoEntidades = new RepoEntidades();
  Entidad gualmayen;
  Entidad jorgito;

  Establecimiento establecimientoGualmayen;
  Establecimiento establecimientoJorgito;
  Servicio unAscensor;
  Servicio unaEscaleraMecanicaSubida;
  Servicio unaEscaleraMecanicaBajada;

  Servicio otroAscensor;
  Servicio otroEscaleraMecanicaSubida;
  Servicio otroEscaleraMecanicaBajada;

  CriterioRanking rankingPromedio;
  CriterioRanking rankingCantidadReportes;

  @BeforeEach
  void setUp() {

    //CREACION ENTIDAD GUALMAYEN

    unAscensor = new Servicio(TipoServicio.ASCENSOR);
    unaEscaleraMecanicaSubida = new Servicio(TipoServicio.ESCALERA_MECANICA);
    unaEscaleraMecanicaBajada = new Servicio(TipoServicio.ESCALERA_MECANICA);

    establecimientoGualmayen = new Establecimiento();

    gualmayen = new Entidad("Gualmayen", "alfajores.com");

    gualmayen.agregarEstablecimiento(establecimientoGualmayen);

    establecimientoGualmayen.agregarServicio(unAscensor);
    establecimientoGualmayen.agregarServicio(unaEscaleraMecanicaSubida);
    establecimientoGualmayen.agregarServicio(unaEscaleraMecanicaBajada);

    repoEntidades.guardarEntidad(gualmayen);


    //CREACION ENTIDAD JORGITO

    otroAscensor = new Servicio(TipoServicio.ASCENSOR);
    otroEscaleraMecanicaBajada = new Servicio(TipoServicio.ESCALERA_MECANICA);
    otroEscaleraMecanicaSubida = new Servicio(TipoServicio.ESCALERA_MECANICA);

    establecimientoJorgito = new Establecimiento();

    jorgito = new Entidad("Jorgito", "jorgito.com");

    jorgito.agregarEstablecimiento(establecimientoJorgito);


    establecimientoJorgito.agregarServicio(otroAscensor);
    establecimientoJorgito.agregarServicio(otroEscaleraMecanicaBajada);
    establecimientoJorgito.agregarServicio(otroEscaleraMecanicaSubida);

    repoEntidades.guardarEntidad(jorgito);

    //RANKINGS

    rankingPromedio = new PromedioCierresSemanal();
    rankingCantidadReportes = new CantidadReportesSemanal();
  }

  @AfterEach
  void clear(){
    repoEntidades.clear();
  }

  @Test
  @DisplayName("Cuando no hay incidentes que reportar, NO rompe")
  void noHayIncidentesQueReportar() {
    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 0);
  }

  @Test
  @DisplayName("Cuando hay incidentes en servicios separados, se reportan")
  void guelmayenUnIncidentePorServicio() {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");

    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 3);
  }

  @Test
  @DisplayName("Cuando hay incidentes en un mismo servicio, se reportan SOLO el 1º")
  void guelmayenTieneMasDeUnIncidentePorServicio() {
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "2° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "3° incidente");

    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 1);
  }//de 5 incidentes, filtran servicio que se repiten en un mmismo servicio dentro de las 24 horas

  @Test
  @DisplayName("Cuando hay incidentes en un mismo servicio, pero el primero fue cerrado, se reportan el 1º y el siguiente")
  void servicioTieneMasDeUnIncidentePeroElPrimeroCerro() {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unAscensor, "2° incidente");
    Incidente incidente = devolverIncidente(unAscensor, "1° incidente");
    incidente.cerrar();
    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 2);
  }

  @Test
  @DisplayName("Cuando hay 2 incidentes abiertos, pero con mas de 24 horas de diferencia, se suman al conteo")
  void servicioAbreIncidenteDespuesDe24HorasDeOtroIncidenteAbierto() {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unAscensor, "2° incidente");
    Incidente incidente = devolverIncidente(unAscensor, "1° incidente");
    Incidente incidente2 = devolverIncidente(unAscensor, "2° incidente");

    Clock clock1 = Clock.systemDefaultZone();
    Clock clock2 = Clock.offset(clock1, Duration.ofHours(25));

    incidente.setFechaHoraAbre(LocalDateTime.now(clock1));
    incidente2.setFechaHoraAbre(LocalDateTime.now(clock2));

    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 2);
  }


  @Test
  @DisplayName("Ranking de cantidad de reportes de incidentes, devuelve entidades ordenadas, de menor a mayor")
  void realizarRankingCantidadIncidentesOrdenados() {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");
    jorgito.crearIncidente(otroAscensor, "1° incidente");
    jorgito.crearIncidente(otroEscaleraMecanicaBajada, "1° incidente");

    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 3);
    Assertions.assertEquals(jorgito.cantidadDeIncidentesReportados(), 2);
    Assertions.assertEquals(repoEntidades.ordenarEntidadesSegunCriterio(rankingCantidadReportes), List.of(jorgito, gualmayen));
  }

  @Test
  @DisplayName("SI hay varios incidentes cerrados, se devuelve el PROMEDIO")
  void guelmayenCalculaPromedioCierreUnIncidente(){
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");

    Incidente incidente = devolverIncidente(unAscensor, "1° incidente");
    Incidente incidente2 = devolverIncidente(unaEscaleraMecanicaBajada, "1° incidente");

    incidente.cerrar();
    incidente2.cerrar();

    Clock baseClock = Clock.systemDefaultZone();

    // Setteo parametros
    Clock clock1 = Clock.offset(baseClock, Duration.ofSeconds(5));
    Clock clock2 = Clock.offset(baseClock, Duration.ofSeconds(3));

    incidente.setFechaHoraAbre(LocalDateTime.now(baseClock));
    incidente.setFechaHoraCierre(LocalDateTime.now(clock1));
    incidente2.setFechaHoraAbre(LocalDateTime.now(baseClock));
    incidente2.setFechaHoraCierre(LocalDateTime.now(clock2));

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.of(4, ChronoUnit.MINUTES));
    //El promedio entre 3 y 5 es igual a 4

  }

  @Test
  @DisplayName("Cuando no hay incidentes cerrados, la duracion es CERO, NO rompe")
  void guelmayenNoTieneIncidentesCerrados() {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.ZERO);

  }

  @Test
  @DisplayName("Cuando no hay incidentes, la duracion es CERO, NO rompe")
  void guelmayenNoTieneIncidentes() {
    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.ZERO);
  }

  @Test
  @DisplayName("Ranking por promedios de cierres de incidentes, devuelve entidades ordenadas, de menor a mayor")
  void realizarRankingSegunPromedios() {

    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");
    jorgito.crearIncidente(otroAscensor, "1° incidente");
    jorgito.crearIncidente(otroEscaleraMecanicaBajada, "1° incidente");

    Incidente incidenteGuelmayen1 = devolverIncidente(unAscensor, "1° incidente");
    Incidente incidenteGuelmayen2 = devolverIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    Incidente incidenteJorgito = devolverIncidente(otroAscensor, "1° incidente");

    incidenteGuelmayen1.cerrar();
    incidenteGuelmayen2.cerrar();
    incidenteJorgito.cerrar();

    ///// Setteo parametros
    Clock baseClock = Clock.systemDefaultZone();

    Clock clock1 = Clock.offset(baseClock, Duration.ofSeconds(1));
    Clock clock2 = Clock.offset(baseClock, Duration.ofSeconds(3));
    Clock clock3 = Clock.offset(baseClock, Duration.ofSeconds(8));

    incidenteGuelmayen1.setFechaHoraAbre(LocalDateTime.now(baseClock));
    incidenteGuelmayen1.setFechaHoraCierre(LocalDateTime.now(clock1));
    incidenteGuelmayen2.setFechaHoraAbre(LocalDateTime.now(baseClock));
    incidenteGuelmayen2.setFechaHoraCierre(LocalDateTime.now(clock2));
    incidenteJorgito.setFechaHoraAbre(LocalDateTime.now(baseClock));
    incidenteJorgito.setFechaHoraCierre(LocalDateTime.now(clock3));

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.of(2, ChronoUnit.MINUTES));
    Assertions.assertEquals(jorgito.promedioDuracionIncidentes(), Duration.of(8, ChronoUnit.MINUTES));
    Assertions.assertEquals(repoEntidades.ordenarEntidadesSegunCriterio(rankingPromedio), List.of(gualmayen, jorgito));


  }


  //FUNCIONES AUXILIARES

  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i -> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }
}