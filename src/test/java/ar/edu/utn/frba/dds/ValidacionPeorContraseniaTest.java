package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones.DebilPasswordException;
import ar.edu.utn.frba.dds.validaciones.ValidacionPeorContrasenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionPeorContraseniaTest {

  ValidacionPeorContrasenia validacionPeorContrasenia;
  @BeforeEach
  void setUp() {
     validacionPeorContrasenia = new ValidacionPeorContrasenia();
  }

  @Test
  void contraseniaEstaEnListBlackNoEsValida() {
    assertThrows(DebilPasswordException.class, () -> validacionPeorContrasenia.esValida("1234") );
  }

  @Test
  void contraseniaNoEstaEnListBlackEsValida(){
    assertDoesNotThrow(()-> validacionPeorContrasenia.esValida("elMarcianito 132"));
  }
}