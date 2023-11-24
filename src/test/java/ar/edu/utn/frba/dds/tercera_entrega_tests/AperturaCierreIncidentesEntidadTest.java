package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.WhatsAppSender;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import ar.edu.utn.frba.dds.repositorios.RepoEstablecimientos;
import ar.edu.utn.frba.dds.repositorios.RepoServicios;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


public class AperturaCierreIncidentesEntidadTest implements SimplePersistenceTest {

  Servicio servicio;
  Entidad unaEntidad;
  Establecimiento unEstablecimiento;
  RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();
  Usuario usuario;
  MedioNotificador medioNotificador;


  @BeforeEach
  void setUp() {
    usuario = new Usuario("Juan ", "Perez", "unMail");
    medioNotificador = new WhatsAppSender();
    servicio = new Servicio("unNombre", TipoServicio.ASCENSOR);
    unEstablecimiento = new Establecimiento("nombre");
    unaEntidad = new Entidad("razonsocial", "unMail");
    unEstablecimiento.agregarServicio(servicio);
    unaEntidad.agregarEstablecimiento(unEstablecimiento);
    usuario.setMedioNotificador(new WhatsAppSender());

    // Persistir el Usuario
    repoUsuarios.add(usuario);
    RepoServicios.getInstance().add(servicio);
    RepoEstablecimientos.getInstance().add(unEstablecimiento);
    RepoEntidades.getInstance().add(unaEntidad);

    // Persistir el Usuario y MedioNotificador
    //getTransaction().begin();
    persist(medioNotificador);
    //getTransaction().commit();


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
  }

}
