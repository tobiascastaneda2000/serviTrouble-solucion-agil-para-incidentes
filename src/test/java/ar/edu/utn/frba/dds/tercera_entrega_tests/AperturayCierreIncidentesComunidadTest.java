package ar.edu.utn.frba.dds.tercera_entrega_tests;


import ar.edu.utn.frba.dds.entidades.*;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.RepoServicios;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.incidentes.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AperturayCierreIncidentesComunidadTest implements WithSimplePersistenceUnit {

  Usuario usuarioInformante;
  Usuario otroUsuario;
  Comunidad palermoGrupo;
  Comunidad otraComunidad;
  Servicio servicio;
  Entidad entidad;
  Establecimiento establecimiento;
  RepositorioComunidades repositorioComunidades;

  @BeforeEach
  void setUp() {
    usuarioInformante = new Usuario("Leonardo ", "Dicaprio", "mail@utn.com.ar");
    otroUsuario = new Usuario("Margot ", "Robbie", "mail2@utn.com.ar");
    palermoGrupo = new Comunidad("uno");
    otraComunidad = new Comunidad("dos");
    entidad = new Entidad("razonsocial", "unEmail");
    establecimiento = new Establecimiento("nombre");
    servicio = new Servicio("nombre", TipoServicio.ASCENSOR);

    establecimiento.agregarServicio(servicio);
    entidad.agregarEstablecimiento(establecimiento);
    palermoGrupo.serviciosDeInteres.add(servicio);
    otraComunidad.serviciosDeInteres.add(servicio);

    withTransaction(() -> {
      RepoUsuarios.getInstance().add(usuarioInformante);
      RepoUsuarios.getInstance().add(otroUsuario);
      RepositorioComunidades.getInstance().add(palermoGrupo);
      RepositorioComunidades.getInstance().add(otraComunidad);
      RepoServicios.getInstance().add(servicio);

      palermoGrupo.agregarUsuario(usuarioInformante, PermisoComunidad.USUARIO_COMUNIDAD);
      palermoGrupo.agregarUsuario(otroUsuario, PermisoComunidad.USUARIO_COMUNIDAD);
      otraComunidad.registrarMiembro(usuarioInformante);
    });
  }

  @AfterEach
  void clear() {
    RepositorioComunidades.getInstance().clean();
  }

  @Test
  @DisplayName("Incidente se persiste en la Base de datos")
  void incidenteCreadoExiste() {
    Incidente incidente = usuarioInformante.abrirIncidente(servicio, "unaObservacion");

    withTransaction(() -> persist(incidente));

    Assertions.assertNotNull(incidente.getId());
  }

  @Test
  @DisplayName("Se guarda un incidente equivalente para cada comunidad ")
  void guardaIncidentesEnComunidades() {
    Incidente incidente = usuarioInformante.abrirIncidente(servicio, "unaObservacion");

    withTransaction(() -> persist(incidente));

    Assertions.assertEquals(palermoGrupo.incidentes.size(), 1);
    Assertions.assertEquals(otraComunidad.incidentes.size(), 1);

  }

  @Test
  @DisplayName("Se guarda la notificacion a los usuarios de esas comunidades")
  void usuariosMiembrosRecibenNotificacion() {
    Incidente incidente = usuarioInformante.abrirIncidente(servicio, "unaObservacion");

    withTransaction(() -> persist(incidente));

    Assertions.assertEquals(otroUsuario.getNotificaciones().size(), 1);
  }

  @Test
  @DisplayName("Incidente se cierra en una sola comunidad, sin que afecte a al otra")
  void cerrarIncidente() {

    Incidente incidenteGeneral = usuarioInformante.abrirIncidente(servicio, "unaObservacion");

    Incidente incidente = palermoGrupo.incidentes.get(0);

    palermoGrupo.cerrarIncidente(incidente);

    Incidente incidenteCerrado = palermoGrupo.incidentesCerrados.get(0);

    Incidente incidenteAbierto = otraComunidad.getIncidentes().get(0);

    Assertions.assertEquals(incidenteCerrado.getEstado(), EstadoIncidente.CERRADO);
    Assertions.assertNotNull(incidenteCerrado.getFechaHoraCierre());
    Assertions.assertEquals(palermoGrupo.incidentes.size(), 0);

    Assertions.assertEquals(incidenteAbierto.getEstado(), EstadoIncidente.ABIERTO);
    Assertions.assertNull(incidenteAbierto.getFechaHoraCierre());
    Assertions.assertEquals(otraComunidad.incidentes.size(), 1);

  }


}