package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LectorCSVTest {

    public LectorCSV lectorCSV(){
        return new LectorCSV("");
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