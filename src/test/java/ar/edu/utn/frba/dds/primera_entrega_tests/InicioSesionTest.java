package ar.edu.utn.frba.dds.primera_entrega_tests;

import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
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
  @DisplayName("Ingresar usuario erroneo suma un intento")
  void usuarioErroneoSumaUnIntento() {
    usuarioSesionCerrada.iniciarSesion("Adrian", "Rodriguez");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 1);
  }

  @Test
  @DisplayName("Ingresar contraseña erronea suma un intento")
  void contraseniaErroneaSumaUnIntento() {
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rincon");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 1);
  }

  @Test
  @DisplayName("Ingresar datos correctos abre sesion y no suma intentos")
  void datosCorrectosAbrenSesion() {
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rodriguez");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 0);
  }


}
