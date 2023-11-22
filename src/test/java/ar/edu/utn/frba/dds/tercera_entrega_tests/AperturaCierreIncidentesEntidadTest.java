package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.notificador.WhatsAppSender;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


public class AperturaCierreIncidentesEntidadTest {

  Servicio servicio;
  Entidad unaEntidad;
  Establecimiento unEstablecimiento;
  RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();
  Usuario usuario;

/*
  @BeforeEach
  void setUp() {

    usuario = new Usuario("Juan ", "Perez", "unMail");
    usuario.setMedioNotificador(new WhatsAppSender());
    repoUsuarios.add(usuario);
    servicio = new Servicio("unNombre", TipoServicio.ASCENSOR);
    unEstablecimiento = new Establecimiento("nombre");
    unaEntidad = new Entidad("razonsocial", "unMail");
    unEstablecimiento.agregarServicio(servicio);
    unaEntidad.agregarEstablecimiento(unEstablecimiento);
    usuario.getEntidadesInteres().add(unaEntidad);

  }

  @Test
  void seCreaYAgregaElIncidente() {
    unaEntidad.crearIncidente(servicio, "observacion");
    Incidente incidente = devolverIncidente(servicio, "observacion");
    Assertions.assertTrue(unaEntidad.incidentes.contains(incidente));
    Assertions.assertEquals(1, unaEntidad.incidentes.size());
  }


  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i -> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }

  @AfterEach
  void clear() {
    repoUsuarios.clean();
  }*/

}
