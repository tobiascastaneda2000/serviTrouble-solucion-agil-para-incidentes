package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.rankings.LaListaEstaVaciaException;
import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class LectorCSVRankingsTest {
  RepoEntidades repoEntidades = new RepoEntidades();
  Entidad gualmayen;
  Entidad jorgito;
  Entidad quatar_aerolines;
  List<Entidad> listadoEntidades;

  @BeforeEach
  void setUp() {

    gualmayen = new Entidad(12, "Fabrica de alfajores", "fassakunnatu-4107@yopmail.com");
    jorgito = new Entidad(1, "Jorgito", "frelaubatuquoi-7637@yopmail.com");
    quatar_aerolines = new Entidad(20, "Quatar", "gixibreprousa-4617@yopmail.com");
    listadoEntidades = new ArrayList<>(List.of(gualmayen, jorgito, quatar_aerolines));
  }

  @Test
  public void lectorDevuelveArchivotxtConEntidades(){
    LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura();
    lectorDeRankings.escribirRankings(listadoEntidades);
    String primeraLinea = lectorDeRankings.obtenerPrimeraLinea("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades.txt",1);
    String segundaLinea = lectorDeRankings.obtenerPrimeraLinea("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades.txt",2);
    String terceraLinea = lectorDeRankings.obtenerPrimeraLinea("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades.txt",3);
    Assertions.assertEquals(primeraLinea,"12 ; Fabrica de alfajores ; fassakunnatu-4107@yopmail.com");
    Assertions.assertEquals(segundaLinea,"1 ; Jorgito ; frelaubatuquoi-7637@yopmail.com");
    Assertions.assertEquals(terceraLinea,"20 ; Quatar ; gixibreprousa-4617@yopmail.com");
  }

  @Test
  public void lectorCuandoRecibeListaVaciaLanzaError(){
    LectorCSVEscritura lectorDeRankings = new LectorCSVEscritura();
    Assertions.assertThrows( LaListaEstaVaciaException.class, ()-> lectorDeRankings.escribirRankings(null));
  }
}
