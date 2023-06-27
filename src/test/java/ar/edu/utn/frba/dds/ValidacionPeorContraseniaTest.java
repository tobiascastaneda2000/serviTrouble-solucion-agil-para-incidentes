package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones_password.DebilPasswordException;
import ar.edu.utn.frba.dds.validaciones_password.ValidacionPeorContrasenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionPeorContraseniaTest {

  ValidacionPeorContrasenia validacionPeorContrasenia;
  @BeforeEach
  void setUp() {
     validacionPeorContrasenia = new ValidacionPeorContrasenia();
  }

  @Test
  @DisplayName("contraseña que esta en lista negra lanza excepccion")
  void contraseniaEstaEnListBlackNoEsValida() {
    assertThrows(DebilPasswordException.class, () -> validacionPeorContrasenia.esValida("1234") );
  }

  @Test
  @DisplayName("contraseña que NO esta en lista negra NO lanza excepccion")
  void contraseniaNoEstaEnListBlackEsValida(){
    assertDoesNotThrow(()-> validacionPeorContrasenia.esValida("elMarcianito 132"));
  }
}