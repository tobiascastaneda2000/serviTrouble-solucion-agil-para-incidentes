package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones.ValidacionLongitudContrasenia;
import org.junit.jupiter.api.BeforeEach;
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
  void contraseniaSinEspaciosDevuelveMismoString(){
    assertEquals(validacionLongitudContrasenia.recortarEspaciosSeguidos(contraseniaSinEspacios) , "Maximo1234");
  }

  @Test
  void contraseniaConEspaciosDevuelveStringMenor(){
    assertEquals(validacionLongitudContrasenia.recortarEspaciosSeguidos(contraseniaConEspacios) , "M l");
  }
}