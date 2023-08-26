package ar.edu.utn.frba.dds.primera_entrega_tests;

import ar.edu.utn.frba.dds.validaciones_password.ContraseniaConMuchosCaracteresException;
import ar.edu.utn.frba.dds.validaciones_password.ContraseniaConPocosCaracteresException;
import ar.edu.utn.frba.dds.validaciones_password.DebilPasswordException;
import ar.edu.utn.frba.dds.validaciones_password.ValidacionLongitudContrasenia;
import ar.edu.utn.frba.dds.validaciones_password.ValidacionPeorContrasenia;
import ar.edu.utn.frba.dds.validaciones_password.Validador;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ValidadorTest {
  ValidacionPeorContrasenia validacionPeorContrasenia;
  ValidacionLongitudContrasenia validacionLongitudContrasenia;
  Validador validador;
  String contrasenia;
  Stack<RuntimeException> listadoErrores;

  @BeforeEach
  void setUp() {
    validador = new Validador();
    validacionPeorContrasenia = new ValidacionPeorContrasenia();
    validacionLongitudContrasenia = new ValidacionLongitudContrasenia();

  }

  @Test
  @DisplayName("Cuando no hay fallas la lista esta vacia")
  void siNoFallaValidacionesDevuelveListaVacia() {
    contrasenia = "AguanteCanserbero6543";
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 0);

  }

  @Test
  @DisplayName("Cuando hay pocos caracteres devuelve su excepccion")
  void fallaSoloValidacionPocosCaracteres() {
    contrasenia = "345";
    validador.agregarValidacion(validacionLongitudContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 1);
    assertInstanceOf(listadoErrores.get(0).getClass(), new ContraseniaConPocosCaracteresException());
  }

  @Test
  @DisplayName("Cuando hay muchos caracteres devuelve su excepccion")
  void fallaSoloValidacionMuchosCaracteres() {
    contrasenia = "En este ejemplo, estamos utilizando assertEquals para comparar la clase de excepción lanzada " +
        "(e.getClass()) con la clase de excepción esperada (MiExcepcion.class). Si las clases son iguales, " +
        "la aserción será exitosa y el test pasará. Si las clases no son iguales, la aserción fallará y se " +
        "mostrará un mensaje de error.";
    validador.agregarValidacion(validacionLongitudContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 1);
    assertInstanceOf(listadoErrores.get(0).getClass(), new ContraseniaConMuchosCaracteresException());

  }

  @Test
  @DisplayName("Cuando es contraseña debil devuelve su excepccion")
  void fallaSoloValidacionPeoresContrasenias() {
    contrasenia = "secret";
    validador.agregarValidacion(validacionPeorContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 1);
    assertInstanceOf(listadoErrores.get(0).getClass(), new DebilPasswordException());
  }

  @Test
  @Disabled
  @DisplayName("Cuando hay muchos fallos devuelve todos los fallos ocurridos")
  void puedenFallarVariasValidacionesSoloSeQuedaConPrimerError() {
    contrasenia = "1234";
    validador.agregarValidacion(validacionPeorContrasenia);
    validador.agregarValidacion(validacionLongitudContrasenia);
    listadoErrores = validador.validarContrasenia(contrasenia);
    assertEquals(listadoErrores.size(), 2);
    assertInstanceOf(listadoErrores.get(1).getClass(), new DebilPasswordException());
    assertInstanceOf(listadoErrores.get(0).getClass(), new ContraseniaConPocosCaracteresException());

  }


}