package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.notificador.Horario;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.Notificacion;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

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
  horario = new Horario(10,10);
  usuario = new Usuario(1,"pepe","1234","email");
  usuario.setMedioNotificador(medioNotificador);
  usuario.agregarHorario(horario);
  servicio = new Servicio(TipoServicio.ASCENSOR);
  incidente = new Incidente("obs",servicio);
  notificacion = new Notificacion(usuario,incidente);
  usuario.getNotificaciones().add(notificacion);

  }

  @Test
  void  hola(){

    LocalDateTime hora = LocalDateTime.of(2023,1,1,10,10);
    usuario.verificarNotificaciones(hora);

    Assertions.assertEquals(usuario.getLogNotificaciones().size(),1);
    Assertions.assertEquals(usuario.getNotificaciones().get(0).fueNotificada,true);
  }
}
