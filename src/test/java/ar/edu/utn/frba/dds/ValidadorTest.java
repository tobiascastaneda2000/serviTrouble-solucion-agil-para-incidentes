package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.validaciones.ContraseniaConMuchosCaracteresException;
import ar.edu.utn.frba.dds.validaciones.ContraseniaConPocosCaracteresException;
import ar.edu.utn.frba.dds.validaciones.DebilPasswordException;
import ar.edu.utn.frba.dds.validaciones.Validacion;
import ar.edu.utn.frba.dds.validaciones.ValidacionLongitudContrasenia;
import ar.edu.utn.frba.dds.validaciones.ValidacionPeorContrasenia;
import ar.edu.utn.frba.dds.validaciones.Validador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorTest {
  ValidacionPeorContrasenia validacionPeorContrasenia;
  ValidacionLongitudContrasenia validacionLongitudContrasenia;
  Validador validador;
  String contrasenia;

  @BeforeEach
  void setUp() {
    validador = new Validador();
    validacionPeorContrasenia = new ValidacionPeorContrasenia();
    validacionLongitudContrasenia = new ValidacionLongitudContrasenia();

  }

  @Test
  void siNoFallaValidacionesDevuelveListaVacia() {
    contrasenia = "AguanteCanserbero6543";
    List<RuntimeException> listadoErrores;
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 0);
    assertEquals(listadoErrores, List.of());

  }

  @Test
  void fallaSoloValidacionPocosCaracteres() {
    contrasenia = "345";
    List<RuntimeException> listadoErrores;
    validador.agregarValidacion(validacionLongitudContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 1);
    assertTrue(listadoErrores.contains(new ContraseniaConPocosCaracteresException()));
    assertEquals(listadoErrores, Arrays.asList(new ContraseniaConPocosCaracteresException()));

  }

  @Test
  void fallaSoloValidacionMuchosCaracteres() {
    contrasenia = "En este ejemplo, estamos utilizando assertEquals para comparar la clase de excepción lanzada " +
        "(e.getClass()) con la clase de excepción esperada (MiExcepcion.class). Si las clases son iguales, " +
        "la aserción será exitosa y el test pasará. Si las clases no son iguales, la aserción fallará y se " +
        "mostrará un mensaje de error.";
    List<RuntimeException> listadoErrores;
    validador.agregarValidacion(validacionLongitudContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 1);
    assertTrue(listadoErrores.contains(new ContraseniaConMuchosCaracteresException()));
    assertEquals(listadoErrores, Arrays.asList(new ContraseniaConMuchosCaracteresException()));

  }

  @Test
  void fallaSoloValidacionPeoresContrasenias() {
    contrasenia = "secret";
    List<RuntimeException> listadoErrores;
    validador.agregarValidacion(validacionPeorContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 1);
    assertEquals(listadoErrores, Arrays.asList(new DebilPasswordException()));
    assertTrue(listadoErrores.contains(new DebilPasswordException()));
  }

  @Test
  void puedenFallarVariasValidacionesSoloSeQuedaConPrimerError() {
    contrasenia = "1234";
    List<RuntimeException> listadoErrores;
    validador.agregarValidacion(validacionPeorContrasenia);
    validador.agregarValidacion(validacionLongitudContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 2);

    assertEquals(listadoErrores, Arrays.asList(new DebilPasswordException(), new ContraseniaConPocosCaracteresException()));
    assertTrue(listadoErrores.contains(new ContraseniaConPocosCaracteresException()));
    assertTrue(listadoErrores.contains(new DebilPasswordException()));

    //Averiguar por que no valida los tests
    //calcula cant elementos dentor de manera correcta, pero no se puede comparar excepcciones
  }


}