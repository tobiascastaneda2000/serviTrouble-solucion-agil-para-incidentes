package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LectorCSVTest {

    public LectorCSV lectorCSV(){
        return new LectorCSV("C:\\Users\\fmartinez\\Desktop\\Facu\\tpdds\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras.csv");
    }

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
        LectorCSV lector = new LectorCSV("C:\\Users\\fmartinez\\Desktop\\Facu\\tpdds\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras.csv");
        assertEquals(lector.extensioncsv,".csv");
    }

    @Test
    @DisplayName("Si la extension no es .csv tira exception")
    void NoseAbreArchivoConOtraExtension() {
        String  path = "C:\\Users\\fmartinez\\Desktop\\Facu\\tpdds\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSEntidadesPrestadoras.txt";
        assertThrows(PathIncorrectoException.class,()->new LectorCSV(path));
    }

    @Test
    @DisplayName("Si un CSV no existe tira exception")
    void NoseAbreArchivoCSVsiNoExiste() {
        LectorCSV lector = new LectorCSV("C:\\Users\\fmartinez\\Desktop\\Facu\\tpdds\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSEntidadesPrestadoras.csv");
        assertThrows(ArchivoNoExistenteException.class,()->lector.obtenerEntidadesDeCSV());
    }

    @Test
    @DisplayName("Si un CSV existe entonces y es correcto entonces carga las entidades")
    void seAbreCSV() {
        LectorCSV lector = new LectorCSV("C:\\Users\\fmartinez\\Desktop\\Facu\\tpdds\\2023-tpa-vi-no-grupo-07-main\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\CSVEntidadesPrestadoras.csv");
        List<Entidad> lista = lector.obtenerEntidadesDeCSV();
    }


    @Test
    @DisplayName("No se genera ninguna entidad si uno de sus campos no esta.")
    void seGeneraUnaEntidadConDatosCompletos() {
        Entidad entidadNueva = lectorCSV().generarEntidad(lineaCompleta());
        assertNotNull( entidadNueva );
    }

    @Test
    @DisplayName("No se genera ninguna entidad si uno de sus campos esta vacio.")
    void noSeGeneraUnaEntidadConDatosVacios() {
        Entidad entidadNueva = lectorCSV().generarEntidad(lineaSinRazonSocial());
        assertNull( entidadNueva );
    }

    @Test
    @DisplayName("No se genera ninguna entidad si uno de sus campos no esta.")
    void noSeGeneraUnaEntidadConDatosFaltantes() {
        Entidad entidadNueva = lectorCSV().generarEntidad(lineaSinUnCampo());
        assertNull( entidadNueva );
    }

}