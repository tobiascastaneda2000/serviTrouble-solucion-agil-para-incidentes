package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.ArchivoNoExistenteException;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.EntradaNoPuedeSerNull;
import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;

public class EscrituraCSVRankingsEntidadesTest {

  RepoEntidades repoEntidades = new RepoEntidades();
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

    //OTROS DATOS

    listadoEntidades = new ArrayList<>(List.of(gualmayen, jorgito));
    path = "src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades.txt";
  }

  @Test
  @DisplayName("lector devuelve listado de entidades ORDENADA")
  public void lectorDevuelveArchivotxtConEntidades() {
    LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura(path);
    lectorDeRankings.escribirRankings(listadoEntidades);
    String primeraLinea = obtenerNumeroDeLineaN(1);
    String segundaLinea = obtenerNumeroDeLineaN(2);
    Assertions.assertEquals(primeraLinea, "12 ; Gualmayen ; alfajores.com");
    Assertions.assertEquals(segundaLinea, "32 ; Jorgito ; jorgito.com");
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
    Assertions.assertEquals(0, lectorDeRankings.getArchivo().length());

  }

  @Test
  @DisplayName("Archivo CSV por promedios de cierres de incidentes, escribe entidades ordenadas, de menor a mayor")
  void escribirArchivoSegunPromedios() throws InterruptedException {

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
    Assertions.assertEquals(repoEntidades.ordenarEntidadesSegunCriterio(rankingPromedio), List.of(gualmayen, jorgito));

    scheduler.shutdown();

    repoEntidades.generarRankingEnCsv(rankingPromedio);
    String primeraLinea = obtenerNumeroDeLineaN(1);
    String segundaLinea = obtenerNumeroDeLineaN(2);
    Assertions.assertEquals(primeraLinea, "12 ; Gualmayen ; alfajores.com");
    Assertions.assertEquals(segundaLinea, "32 ; Jorgito ; jorgito.com");
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

    repoEntidades.generarRankingEnCsv(rankingCantidadReportes);
    String primeraLinea = obtenerNumeroDeLineaN(1);
    String segundaLinea = obtenerNumeroDeLineaN(2);
    Assertions.assertEquals(primeraLinea, "32 ; Jorgito ; jorgito.com");
    Assertions.assertEquals(segundaLinea, "12 ; Gualmayen ; alfajores.com");
  }

  //FUNCIONES AUXILIARES

  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i -> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }

  public String obtenerNumeroDeLineaN(int i) {
    try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
      int aux = 0;
      String linea = null;
      while (aux < i) {
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
