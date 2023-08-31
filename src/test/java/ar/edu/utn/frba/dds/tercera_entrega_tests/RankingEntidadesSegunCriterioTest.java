package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    gualmayen = new Entidad(12, "Gualmayen", "alfajores.com");

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

    jorgito = new Entidad(32, "Jorgito", "jorgito.com");

    jorgito.agregarEstablecimiento(establecimientoJorgito);


    establecimientoJorgito.agregarServicio(otroAscensor);
    establecimientoJorgito.agregarServicio(otroEscaleraMecanicaBajada);
    establecimientoJorgito.agregarServicio(otroEscaleraMecanicaSubida);

    repoEntidades.guardarEntidad(jorgito);

    //RANKINGS

    rankingPromedio = new PromedioCierresSemanal();
    rankingCantidadReportes = new CantidadReportesSemanal();
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

  //falta test para probar diferencia de 24 horas

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
    Assertions.assertEquals(repoEntidades.realizarRankingSegunCriterio(rankingCantidadReportes), List.of(jorgito, gualmayen));
  }

  @Test
  @DisplayName("SI hay varios incidentes cerrados, se devuelve el PROMEDIO")
  void guelmayenCalculaPromedioCierreUnIncidente() throws InterruptedException {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");

    Incidente incidente = devolverIncidente(unAscensor, "1° incidente");
    Incidente incidente2 = devolverIncidente(unaEscaleraMecanicaBajada, "1° incidente");

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    scheduler.schedule(() -> incidente.cerrar(), 5, TimeUnit.SECONDS);
    scheduler.schedule(() -> incidente2.cerrar(), 3, TimeUnit.SECONDS);

    TimeUnit.SECONDS.sleep(6);

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.of(4, ChronoUnit.MINUTES));
    //El promedio entre 3 y 5 es igual a 4

    scheduler.shutdown();
  }

  @Test
  @DisplayName("Cuando no hay incidentes cerrados, la duracion es CERO, NO rompe")
  void guelmayenNoTieneIncidentesCerrados() throws InterruptedException {
    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    TimeUnit.SECONDS.sleep(3);

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.ZERO);

    scheduler.shutdown();
  }

  @Test
  @DisplayName("Cuando no hay incidentes, la duracion es CERO, NO rompe")
  void guelmayenNoTieneIncidentes() throws InterruptedException {

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    TimeUnit.SECONDS.sleep(3);

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.ZERO);

    scheduler.shutdown();
  }

  @Test
  @DisplayName("Ranking por promedios de cierres de incidentes, devuelve entidades ordenadas, de menor a mayor")
  void realizarRankingSegunPromedios() throws InterruptedException {

    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");
    jorgito.crearIncidente(otroAscensor, "1° incidente");
    jorgito.crearIncidente(otroEscaleraMecanicaBajada, "1° incidente");

    Incidente incidenteGuelmayen1 = devolverIncidente(unAscensor, "1° incidente");
    Incidente incidenteGuelmayen2 = devolverIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    Incidente incidenteJorgito = devolverIncidente(otroAscensor, "1° incidente");

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    scheduler.schedule(() -> incidenteGuelmayen1.cerrar(), 1, TimeUnit.SECONDS);
    scheduler.schedule(() -> incidenteGuelmayen2.cerrar(), 3, TimeUnit.SECONDS);
    scheduler.schedule(() -> incidenteJorgito.cerrar(), 8, TimeUnit.SECONDS);

    TimeUnit.SECONDS.sleep(10);

    Assertions.assertEquals(gualmayen.promedioDuracionIncidentes(), Duration.of(2, ChronoUnit.MINUTES));
    Assertions.assertEquals(jorgito.promedioDuracionIncidentes(), Duration.of(8, ChronoUnit.MINUTES));
    Assertions.assertEquals(repoEntidades.realizarRankingSegunCriterio(rankingPromedio), List.of(gualmayen, jorgito));

    scheduler.shutdown();
  }

  //FUNCIONES AUXILIARES

  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i -> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }
}