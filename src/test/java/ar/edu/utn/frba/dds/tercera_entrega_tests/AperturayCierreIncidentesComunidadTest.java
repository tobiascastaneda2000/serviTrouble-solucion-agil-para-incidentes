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
    //repositorioComunidades = RepositorioComunidades.getInstance();
    palermoGrupo.agregarUsuario(usuarioInformante, PermisoComunidad.USUARIO_COMUNIDAD);
    palermoGrupo.agregarUsuario(otroUsuario, PermisoComunidad.USUARIO_COMUNIDAD);
    //persist(usuarioInformante);
    //persist(otroUsuario);
    //persist(palermoGrupo);
    otraComunidad.registrarMiembro(usuarioInformante);
    RepoUsuarios.getInstance().add(usuarioInformante);
    RepoUsuarios.getInstance().add(otroUsuario);
    RepositorioComunidades.getInstance().add(palermoGrupo);
    RepositorioComunidades.getInstance().add(otraComunidad);
    entidad = new Entidad("razonsocial", "unEmail");
    establecimiento = new Establecimiento("nombre");
    servicio = new Servicio("nombre", TipoServicio.ASCENSOR);
    RepoServicios.getInstance().add(servicio);
    //persist(servicio);
    establecimiento.agregarServicio(servicio);
    entidad.agregarEstablecimiento(establecimiento);
    palermoGrupo.serviciosDeInteres.add(servicio);
    otraComunidad.serviciosDeInteres.add(servicio);
  }

  @AfterEach
  void clear(){
    RepositorioComunidades.getInstance().clean();
  }

  @Test
  void seAbreIncidente() {
    //usuarioInformante.abrirIncidente(servicio, "unaObservacion");
    palermoGrupo.abrirIncidenteEnComunidad("unaObservacion", servicio );
    otraComunidad.abrirIncidenteEnComunidad("unaObservacion", servicio);

    //Se carga el incidente en las distintas comunidades que pertenece el usuario
    Assertions.assertEquals(palermoGrupo.incidentes.size(), 1);
    Assertions.assertEquals(otraComunidad.incidentes.size(), 1);

    //Se guarda la notificacion a los usuarios de esas comunidades
    Assertions.assertEquals(otroUsuario.getNotificaciones().size(), 1);
  }

  @Test
  void cerrarIncidente()  {
    //usuarioInformante.abrirIncidente(servicio, "unaObservacion");

    palermoGrupo.abrirIncidenteEnComunidad("unaObservacion", servicio );
    otraComunidad.abrirIncidenteEnComunidad("unaObservacion", servicio);
    Incidente incidente = palermoGrupo.incidentes.get(0);
    palermoGrupo.cerrarIncidente(incidente);

    Incidente incidenteCerrado = palermoGrupo.incidentesCerrados.get(0);

    Assertions.assertEquals(incidenteCerrado.getEstado(), EstadoIncidente.CERRADO);
    Assertions.assertNotNull(incidenteCerrado.getFechaHoraCierre());
    Assertions.assertEquals(palermoGrupo.incidentes.size(), 0);

  }


}