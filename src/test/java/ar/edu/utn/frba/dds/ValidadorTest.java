package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones.ValidacionLongitudContrasenia;
import ar.edu.utn.frba.dds.validaciones.ValidacionPeorContrasenia;
import ar.edu.utn.frba.dds.validaciones.Validador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorTest {
  ValidacionPeorContrasenia validacionPeorContrasenia = new ValidacionPeorContrasenia();
  ValidacionLongitudContrasenia validacionLongitudContrasenia = new ValidacionLongitudContrasenia();
  Validador validador = new Validador();

  @BeforeEach
  void setUp() {

  }


}