package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.notificador.Horario;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.Notificacion;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ConfiguracionNotificaciones {

  Usuario usuario;
  Horario horario;
  Notificacion notificacion;
  Incidente incidente;
  Servicio servicio;
  MedioNotificador medioNotificador;

  @BeforeEach
  void setUp() {

    medioNotificador = mock(MedioNotificador.class);
    horario = new Horario(10, 10);
    usuario = new Usuario("pepe", "1234", "email");
    usuario.setMedioNotificador(medioNotificador);
    usuario.agregarHorario(horario);
    servicio = new Servicio("unNombre", TipoServicio.ASCENSOR);
    incidente = new Incidente("obs", servicio);
    notificacion = new Notificacion(usuario, incidente);
    usuario.getNotificaciones().add(notificacion);

  }

  @Test
  void seEjecutanLasNotificaciones() {

    LocalDateTime hora = LocalDateTime.of(2023, 1, 1, 10, 10);
    usuario.verificarNotificaciones(hora);

    verify(medioNotificador, times(1)).notificarUnIncidente(incidente, "email");
  }
}
