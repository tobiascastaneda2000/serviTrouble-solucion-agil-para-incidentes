package ar.edu.utn.frba.dds.segunda_entrega_tests;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.lectorCSV.LectorCSVLectura;
import ar.edu.utn.frba.dds.lectorCSV.ArchivoNoExistenteException;
import ar.edu.utn.frba.dds.lectorCSV.CampoDeEntidadVacioException;
import ar.edu.utn.frba.dds.lectorCSV.EntidadIncompletaException;
import ar.edu.utn.frba.dds.lectorCSV.EntidadYaCargadaException;
import ar.edu.utn.frba.dds.lectorCSV.PathIncorrectoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LectorCSVEntidadesTest {

  LectorCSVLectura lectorCSV = new LectorCSVLectura("src/main/java/ar/edu/utn/frba/dds/lectorCSV_y_entidadesPrestadoras/CSVEntidadesPrestadoras.csv");
  LectorCSVLectura lectorCSVError = new LectorCSVLectura("src/main/java/ar/edu/utn/frba/dds/lectorCSV_y_entidadesPrestadoras/CSVEntidadesPrestadoras con error.csv");

  public String lineaCompleta() {
    return "10;DDS.SA;sistemas@dds.com";
  }

  public String lineaSinRazonSocial() {
    return "10;;sistemas@dds.com";
  }

  public String lineaSinUnCampo() {
    return "10;sistemas@dds.com";
  }

  @Test
  void ElpathdeunCSVescsv() {
    LectorCSVLectura lector = new LectorCSVLectura("src/main/java/ar/edu/utn/frba/dds/lectorCSV_y_entidadesPrestadoras/CSVEntidadesPrestadoras.csv");
    assertEquals(lector.extensioncsv, ".csv");
  }

  @Test
  @DisplayName("Si la extension no es .csv tira exception")
  void NoseAbreArchivoConOtraExtension() {

    String path = "src/main/java/ar/edu/utn/frba/dds/lectorCSV_y_entidadesPrestadoras/CSVEntidadesPrestadoras.txt";
    assertThrows(PathIncorrectoException.class, () -> new LectorCSVLectura(path));
  }

  @Test
  @DisplayName("Si un CSV no existe tira exception")
  void NoseAbreArchivoCSVsiNoExiste() {

    LectorCSVLectura lector = new LectorCSVLectura("src/main/java/ar/edu/utn/frba/dds/lectorCSV_y_entidadesPrestadoras/CSVEntiuyudadesPrestadoras.csv");
    assertThrows(ArchivoNoExistenteException.class, () -> lector.obtenerEntidadesDeCSV());
  }

  @Test
  @DisplayName("Si un CSV existe entonces y es correcto entonces carga las entidades")
  void seAbreCSV() {

    LectorCSVLectura lector = new LectorCSVLectura("src/main/java/ar/edu/utn/frba/dds/lectorCSV_y_entidadesPrestadoras/CSVEntidadesPrestadoras.csv");
    List<Entidad> lista = lector.obtenerEntidadesDeCSV();
  }

  @Test
  void seGeneraUnaEntidadConDatosCompletos() {
    Entidad entidadNueva = lectorCSV.generarEntidad(lineaCompleta());
    assertNotNull(entidadNueva);
  }

  @Test
  @DisplayName("No se genera ninguna entidad si uno de sus campos esta vacio.")
  void noSeGeneraUnaEntidadConDatosVacios() {
    assertThrows(CampoDeEntidadVacioException.class, () -> lectorCSV.generarEntidad(lineaSinRazonSocial()));
  }

  @Test
  @DisplayName("No se genera ninguna entidad si uno de sus campos no esta.")
  void noSeGeneraUnaEntidadConDatosFaltantes() {
    assertThrows(EntidadIncompletaException.class, () -> lectorCSV.generarEntidad(lineaSinUnCampo()));
  }

  @Test
  @DisplayName("No se genera ninguna entidad si ya estaba cargada")
  void noSeGeneraUnaEntidadYaCargada() {
    assertThrows(EntidadYaCargadaException.class, () -> lectorCSVError.obtenerEntidadesDeCSV());
  }

  @Test
  @DisplayName("No se genera ninguna entidad si ya estaba cargada")
  void noSeGeneraUnaEntidadYaCargadaDeOtroCSV() {
    lectorCSV.obtenerEntidadesDeCSV();
    assertThrows(EntidadYaCargadaException.class, () -> lectorCSV.obtenerEntidadesDeCSV());
  }

  @Test
  @DisplayName("se generan la cantidad de entidades que hay en el archivo")
  void seGeneranLaMismaCantidadDeEntidadesQueEnElCSV() {
    lectorCSV.obtenerEntidadesDeCSV();
    assertEquals(lectorCSV.entidades.size(), 5);
  }

}