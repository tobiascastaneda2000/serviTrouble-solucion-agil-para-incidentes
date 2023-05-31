package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones.ContraseniaConMuchosCaracteresException;
import ar.edu.utn.frba.dds.validaciones.ContraseniaConPocosCaracteresException;
import ar.edu.utn.frba.dds.validaciones.DebilPasswordException;
import ar.edu.utn.frba.dds.validaciones.ValidacionLongitudContrasenia;
import ar.edu.utn.frba.dds.validaciones.ValidacionPeorContrasenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidarCantidadCaracteresTest {

  ValidacionLongitudContrasenia validacionLongitudContrasenia;

  @BeforeEach
  void setUp() {
    validacionLongitudContrasenia = new ValidacionLongitudContrasenia();
  }

  @Test
  void contraseniaMenosDe8Caracteres() {
    assertThrows(ContraseniaConPocosCaracteresException.class, () -> validacionLongitudContrasenia.esValida("sh") );
  }

  @Test
  void contraseniaConMasDe64Caracteres() {
    String muchosCaracteres = "Las pruebas end-to-end, también conocidas como pruebas extremo a extremo, se enfocan en probar " +
        "todo el flujo de trabajo de una aplicación o sistema desde el inicio hasta el final, simulando una experiencia " +
        "real del usuario. Estas pruebas implican la interacción y coordinación de múltiples componentes del sistema, " +
        "incluidas interfaces de usuario, bases de datos, servicios web y otros sistemas externos. El objetivo principal " +
        "de las pruebas end-to-end es verificar que todo el sistema funcione correctamente y se comporte como se espera en " +
        "situaciones del mundo real.";
    assertThrows(ContraseniaConMuchosCaracteresException.class, () -> validacionLongitudContrasenia.esValida(muchosCaracteres) );
  }
}
