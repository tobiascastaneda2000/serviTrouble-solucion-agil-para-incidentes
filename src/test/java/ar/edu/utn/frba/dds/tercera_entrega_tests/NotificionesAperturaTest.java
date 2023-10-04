package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Comunidad;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.WhatsAppSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@Disabled
public class NotificionesAperturaTest {

  RepositorioComunidades repositorioComunidades;
  Comunidad comunidad;
  Usuario unUsuario;
  Usuario otroUsuario;
  Servicio servicio;
  MedioNotificador medioNotificador1;
  MedioNotificador medioEspiado;
  Incidente incidente;

  @BeforeEach
  void setUp(){
    repositorioComunidades = RepositorioComunidades.getInstance();
    comunidad = new Comunidad();
    unUsuario = new Usuario(1, "Messi", "alguien", "mail");
    otroUsuario = new Usuario(2, "Cristiano", "alguien", "otroMail");
    repositorioComunidades.guardarComunidad(comunidad);
    servicio = new Servicio(TipoServicio.ESCALERA_MECANICA);
    comunidad.registrarMiembro(unUsuario);
    unUsuario.abrirIncidente(servicio,"abc");
    medioNotificador1 = new WhatsAppSender();
    medioEspiado = spy(medioNotificador1);
    unUsuario.setMedioNotificador(medioEspiado);
    otroUsuario.setMedioNotificador(medioEspiado);
    incidente = devolverIncidente(servicio, "obs");
  }

  @Test
  public void notificaATodosLosUsuario(){
    verify(medioEspiado, times(1)).notificarUnIncidente(incidente,unUsuario.getContacto());
    verify(medioEspiado, times(1)).notificarUnIncidente(incidente,"otroMail");
  }

  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i-> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }

  @AfterEach
  void clear(){
    repositorioComunidades.clear();
  }
}
