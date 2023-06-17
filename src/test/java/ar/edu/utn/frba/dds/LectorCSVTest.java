package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LectorCSVTest {

    LectorCSV lectorCSV = new LectorCSV("C:\\Users\\usuario\\Desktop\\Facu\\utn\\diseño de sistemas\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras.csv");
    LectorCSV lectorCSVError = new LectorCSV("C:\\Users\\usuario\\Desktop\\Facu\\utn\\diseño de sistemas\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras con error.csv");
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
        LectorCSV lector = new LectorCSV("C:\\Users\\usuario\\Desktop\\Facu\\utn\\diseño de sistemas\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras.csv");
        assertEquals(lector.extensioncsv,".csv");
    }

    @Test
    @DisplayName("Si la extension no es .csv tira exception")
    void NoseAbreArchivoConOtraExtension() {
        String  path = "C:\\Users\\usuario\\Desktop\\Facu\\utn\\diseño de sistemas\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSEntidadesPrestadoras.txt";
        assertThrows(PathIncorrectoException.class,()->new LectorCSV(path));
    }

    @Test
    @DisplayName("Si un CSV no existe tira exception")
    void NoseAbreArchivoCSVsiNoExiste() {
        LectorCSV lector = new LectorCSV("C:\\Users\\usuario\\Desktop\\Facu\\utn\\diseño de sistemas\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSEntidadesPrestadoras.csv");
        assertThrows(ArchivoNoExistenteException.class,()->lector.obtenerEntidadesDeCSV());
    }

    @Test
    @DisplayName("Si un CSV existe entonces y es correcto entonces carga las entidades")
    void seAbreCSV() {
        LectorCSV lector = new LectorCSV("C:\\Users\\usuario\\Desktop\\Facu\\utn\\diseño de sistemas\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras.csv");
        List<Entidad> lista = lector.obtenerEntidadesDeCSV();
    }

    @Test
    void seGeneraUnaEntidadConDatosCompletos() {
        Entidad entidadNueva = lectorCSV.generarEntidad(lineaCompleta());
        assertNotNull( entidadNueva );
    }

    @Test
    @DisplayName("No se genera ninguna entidad si uno de sus campos esta vacio.")
    void noSeGeneraUnaEntidadConDatosVacios() {
        assertThrows(CampoDeEntidadVacioException.class,()->lectorCSV.generarEntidad(lineaSinRazonSocial()));
    }

    @Test
    @DisplayName("No se genera ninguna entidad si uno de sus campos no esta.")
    void noSeGeneraUnaEntidadConDatosFaltantes() {
        assertThrows(EntidadIncompletaException.class,()->lectorCSV.generarEntidad(lineaSinUnCampo()));
    }

    @Test
    @DisplayName("No se genera ninguna entidad si ya estaba cargada")
    void noSeGeneraUnaEntidadYaCargada() {
        assertThrows(EntidadYaCargadaException.class,()->lectorCSVError.obtenerEntidadesDeCSV());
    }

    @Test
    @DisplayName("No se genera ninguna entidad si ya estaba cargada")
    void noSeGeneraUnaEntidadYaCargadaDeOtroCSV() {
        lectorCSV.obtenerEntidadesDeCSV();
        assertThrows(EntidadYaCargadaException.class,()->lectorCSV.obtenerEntidadesDeCSV());
    }

    @Test
    @DisplayName("se generan la cantidad de entidades que hay en el archivo")
    void seGeneranLaMismaCantidadDeEntidadesQueEnElCSV() {
        lectorCSV.obtenerEntidadesDeCSV();
        assertEquals(lectorCSV.entidades.size(),5);
    }

}