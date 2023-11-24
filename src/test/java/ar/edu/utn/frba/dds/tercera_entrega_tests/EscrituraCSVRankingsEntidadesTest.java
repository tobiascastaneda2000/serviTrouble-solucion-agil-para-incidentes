package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.lectorCSV.ArchivoNoExistenteException;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.EntradaNoPuedeSerNull;
import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.mock;

public class EscrituraCSVRankingsEntidadesTest implements SimplePersistenceTest {

  RepoEntidades repoEntidades = RepoEntidades.getInstance(); // Habra que mockear calculo, o conectarlo a una db de pruebas, ni idea
  Entidad gualmayen;
  Entidad jorgito;
  List<Entidad> listadoEntidades;
  String path;

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

    withTransaction(() -> {
      persist(unAscensor);
      persist(unaEscaleraMecanicaBajada);
      persist(unaEscaleraMecanicaSubida);
    });

    establecimientoGualmayen = new Establecimiento("nombre");

    gualmayen = new Entidad("Gualmayen", "alfajores.com");

    withTransaction(() -> {
      persist(establecimientoGualmayen);
      RepoEntidades.getInstance().add(gualmayen);

      gualmayen.agregarEstablecimiento(establecimientoGualmayen);

      establecimientoGualmayen.agregarServicio(unAscensor);
      establecimientoGualmayen.agregarServicio(unaEscaleraMecanicaSubida);
      establecimientoGualmayen.agregarServicio(unaEscaleraMecanicaBajada);
    });


    //CREACION ENTIDAD JORGITO

    otroAscensor = new Servicio(TipoServicio.ASCENSOR);
    otroEscaleraMecanicaBajada = new Servicio(TipoServicio.ESCALERA_MECANICA);
    otroEscaleraMecanicaSubida = new Servicio(TipoServicio.ESCALERA_MECANICA);

    withTransaction(() -> {
      persist(otroAscensor);
      persist(otroEscaleraMecanicaBajada);
      persist(otroEscaleraMecanicaSubida);
    });

    establecimientoJorgito = new Establecimiento("nombre");

    jorgito = new Entidad("Jorgito", "jorgito.com");


    withTransaction(() -> {
      persist(establecimientoJorgito);
      RepoEntidades.getInstance().add(jorgito);

      jorgito.agregarEstablecimiento(establecimientoJorgito);

      establecimientoJorgito.agregarServicio(otroAscensor);
      establecimientoJorgito.agregarServicio(otroEscaleraMecanicaBajada);
      establecimientoJorgito.agregarServicio(otroEscaleraMecanicaSubida);
    });

    //RANKINGS

    rankingPromedio = new PromedioCierresSemanal();
    rankingCantidadReportes = new CantidadReportesSemanal();

    //OTROS DATOS

    listadoEntidades = new ArrayList<>(List.of(gualmayen, jorgito));
    path = "src/main/resources/ranking-para-test.csv";
  }

  @AfterEach
  void clear() {
    RepoEntidades.getInstance().clean();
  }

  @Test
  @DisplayName("lector devuelve listado de entidades ORDENADA")
  public void lectorDevuelveArchivotxtConEntidades() {
    LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura(path);
    lectorDeRankings.escribirRankings(listadoEntidades);
    String primeraLinea = obtenerNumeroDeLineaN(1);
    String segundaLinea = obtenerNumeroDeLineaN(2);
    Assertions.assertEquals(primeraLinea, gualmayen.getId() + ";Gualmayen ; alfajores.com");
    Assertions.assertEquals(segundaLinea, jorgito.getId() + ";Jorgito ; jorgito.com");
  }

  @Test
  @DisplayName("Cuando se escribe en un NULL, FALLA")
  public void lectorCuandoRecibeNull() {
    LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura(path);
    Assertions.assertThrows(EntradaNoPuedeSerNull.class, () -> lectorDeRankings.escribirRankings(null));
  }

  @Test
  @DisplayName("Cuando el path NO EXISTE, FALLA")
  public void lectorCuandoPathNoExiste() {
    path = "nada";
    Assertions.assertThrows(ArchivoNoExistenteException.class, () -> {
      LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura(path);
    });
  }

  @Test
  @DisplayName("Cuando la lista esta vacia, devuelve un archivo csv VACIO")
  public void lectorCuandoListadoEntidadesEstaVacia() {
    listadoEntidades = new ArrayList<>();
    LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura(path);
    lectorDeRankings.escribirRankings(listadoEntidades);
    Assertions.assertNull(obtenerNumeroDeLineaN(1));
  }

  @Test
  @DisplayName("Archivo CSV por promedios de cierres de incidentes, escribe entidades ordenadas, de menor a mayor")
  void escribirArchivoSegunPromedios() {


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

    ///
    Clock baseClock = Clock.systemDefaultZone();

    // result clock will be later than baseClock
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


    repoEntidades.generarRankingCSVSegunCriterioEnUnPathEspecifico(rankingPromedio, path);
    String primeraLinea = obtenerNumeroDeLineaN(1);
    String segundaLinea = obtenerNumeroDeLineaN(2);
    Assertions.assertEquals(primeraLinea, (gualmayen.getId() + ";Gualmayen ; alfajores.com"));
    Assertions.assertEquals(segundaLinea, (jorgito.getId() + ";Jorgito ; jorgito.com"));
  }

  @Test
  @DisplayName("Archivo CSV por cantidad de reportes, escribe entidades ordenadas, de menor a mayor")
  void escribirArchivoSegunCantidadIncidentesOrdenados() {

    gualmayen.crearIncidente(unAscensor, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaBajada, "1° incidente");
    gualmayen.crearIncidente(unaEscaleraMecanicaSubida, "1° incidente");
    jorgito.crearIncidente(otroAscensor, "1° incidente");
    jorgito.crearIncidente(otroEscaleraMecanicaBajada, "1° incidente");

    Assertions.assertEquals(gualmayen.cantidadDeIncidentesReportados(), 3);
    Assertions.assertEquals(jorgito.cantidadDeIncidentesReportados(), 2);
    Assertions.assertEquals(repoEntidades.ordenarEntidadesSegunCriterio(rankingCantidadReportes), List.of(jorgito, gualmayen));

    repoEntidades.generarRankingCSVSegunCriterioEnUnPathEspecifico(rankingCantidadReportes, path);
    String primeraLinea = obtenerNumeroDeLineaN(1);
    String segundaLinea = obtenerNumeroDeLineaN(2);
    Assertions.assertEquals(primeraLinea, jorgito.getId() + ";Jorgito ; jorgito.com");
    Assertions.assertEquals(segundaLinea, gualmayen.getId() + ";Gualmayen ; alfajores.com");
  }

  //FUNCIONES AUXILIARES

  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i -> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }

  public String obtenerNumeroDeLineaN(int i) {
    try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
      int aux = 0;
      String linea = null;
      while (aux < i + 1) {
        linea = br.readLine();
        aux++;
      }
      return linea;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
