package ar.edu.utn.frba.dds.primera_entrega_tests;

import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.validaciones_password.SesionYaEstaAbiertaException;
import ar.edu.utn.frba.dds.Usuario;
import org.junit.jupiter.api.*;

public class InicioSesionTest {
  Usuario usuarioSesionCerrada;
  Usuario usuarioSesionAbierta;

  @BeforeEach
  void init() {
    usuarioSesionCerrada = new Usuario("Miguel", "Rodriguez", "mail");
    usuarioSesionAbierta = new Usuario("Camila", "Zoe", "mail");
    usuarioSesionAbierta.iniciarSesion("Camila", "Zoe");

  }

  @Test
  @DisplayName("Intentar iniciar sesion con masde 3 intentos lanza excepccion")
  void noMasDe3Intentos() {
    usuarioSesionCerrada.iniciarSesion("Migal", "Rodriguez");
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rudiguer");
    usuarioSesionCerrada.iniciarSesion("Michael", "Rodri");
    Assertions.assertThrows(MaxCantIntentosInicioSesionException.class, () -> usuarioSesionCerrada.iniciarSesion("Miguel", "Rodriguez"));
  }

  @Test
  @DisplayName("Intentar iniciar sesion cuando esta abierta lanza excepccion")
  void sesionYaFueAbierta() {

    Assertions.assertTrue(usuarioSesionAbierta.isSesionAbierta());
    Assertions.assertThrows(SesionYaEstaAbiertaException.class, () -> usuarioSesionAbierta.iniciarSesion("Camila", "Zoe"));
    Assertions.assertThrows(SesionYaEstaAbiertaException.class, () -> usuarioSesionAbierta.iniciarSesion("Miguel", "Rodriguez"));
  }

  @Test
  @DisplayName("Ingresar usuario erroneo suma un intento")
  void usuarioErroneoSumaUnIntento() {
    usuarioSesionCerrada.iniciarSesion("Adrian", "Rodriguez");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 1);
    Assertions.assertFalse(usuarioSesionCerrada.isSesionAbierta());
  }

  @Test
  @DisplayName("Ingresar contraseña erronea suma un intento")
  void contraseniaErroneaSumaUnIntento() {
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rincon");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 1);
    Assertions.assertFalse(usuarioSesionCerrada.isSesionAbierta());
  }

  @Test
  @DisplayName("Ingresar datos correctos abre sesion y no suma intentos")
  void datosCorrectosAbrenSesion() {
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rodriguez");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 0);
    Assertions.assertTrue(usuarioSesionCerrada.isSesionAbierta());
  }


}
