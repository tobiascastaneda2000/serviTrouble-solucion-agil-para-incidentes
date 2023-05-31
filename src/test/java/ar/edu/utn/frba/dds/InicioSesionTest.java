package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.usuario.*;
import org.junit.jupiter.api.*;

public class InicioSesionTest {
  Usuario usuarioSesionCerrada;
  Usuario usuarioSesionAbierta;

  @BeforeEach
  void init() {
    usuarioSesionCerrada = new Usuario("Miguel", "Rodriguez");
    usuarioSesionAbierta = new Usuario("Camila", "Zoe");
    usuarioSesionAbierta.iniciarSesion("Camila", "Zoe");

  }

  @Test
  void noMasDe3Intentos() {
    usuarioSesionCerrada.iniciarSesion("Migal", "Rodriguez");
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rudiguer");
    usuarioSesionCerrada.iniciarSesion("Michael", "Rodri");
    Assertions.assertThrows(MaxCantIntentosInicioSesionException.class, () -> usuarioSesionCerrada.iniciarSesion("Miguel", "Rodriguez"));
  }

  @Test
  void sesionYaFueAbierta() {

    Assertions.assertTrue(usuarioSesionAbierta.isSesionAbierta());
    Assertions.assertThrows(SesionYaEstaAbiertaException.class, () -> usuarioSesionAbierta.iniciarSesion("Camila", "Zoe"));
    Assertions.assertThrows(SesionYaEstaAbiertaException.class, () -> usuarioSesionAbierta.iniciarSesion("Miguel", "Rodriguez"));
  }

  @Test
  void usuarioErroneoSumaUnIntento() {
    usuarioSesionCerrada.iniciarSesion("Adrian", "Rodriguez");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 1);
    Assertions.assertFalse(usuarioSesionCerrada.isSesionAbierta());
  }

  @Test
  void contraseniaErroneaSumaUnIntento() {
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rincon");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 1);
    Assertions.assertFalse(usuarioSesionCerrada.isSesionAbierta());
  }

  @Test
  void datosCorrectosAbrenSesion() {
    usuarioSesionCerrada.iniciarSesion("Miguel", "Rodriguez");
    Assertions.assertEquals(usuarioSesionCerrada.getIntentos(), 0);
    Assertions.assertTrue(usuarioSesionCerrada.isSesionAbierta());
  }


}
