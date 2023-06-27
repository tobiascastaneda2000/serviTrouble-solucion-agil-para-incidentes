package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones_password.ValidacionLongitudContrasenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DevuelveContraseniaSinEspaciosTest {
  ValidacionLongitudContrasenia validacionLongitudContrasenia;
  String contraseniaSinEspacios;
  String contraseniaConEspacios;

  @BeforeEach
  void setUp() {

    validacionLongitudContrasenia = new ValidacionLongitudContrasenia();
    contraseniaSinEspacios = "Maximo1234";
    contraseniaConEspacios = "M           l";
  }

  @Test
  @DisplayName("Contrasenia sin espacios devuelve el mismo string")
  void contraseniaSinEspaciosDevuelveMismoString() {
    assertEquals(validacionLongitudContrasenia.recortarEspaciosSeguidos(contraseniaSinEspacios), "Maximo1234");
  }

  @Test
  @DisplayName("Contrasenia con espacios reemplana muchos espacios seguidos por uno solo")
  void contraseniaConEspaciosDevuelveStringMenor() {
    assertEquals(validacionLongitudContrasenia.recortarEspaciosSeguidos(contraseniaConEspacios), "M l");
  }
}