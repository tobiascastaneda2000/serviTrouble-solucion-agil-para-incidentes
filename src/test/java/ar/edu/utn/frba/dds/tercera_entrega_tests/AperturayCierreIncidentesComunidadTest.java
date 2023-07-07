package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Miembro;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AperturayCierreIncidentesComunidadTest {

  Usuario usuarioInformante;
  Usuario otroUsuario;
  Comunidad palermoGrupo;
  Comunidad barracasGrupo;
  MedioNotificador notificadorWhatsapp;
  MedioNotificador notificadorMail;

  Servicio servicio;

  Miembro otroMiembro;

  Miembro miembroInformante;

  @BeforeEach
  void setUp() {
    usuarioInformante = new Usuario(1,"Leonardo ", "Dicaprio", "mail@utn.com.ar");
    otroUsuario = new Usuario(1,"Margot ", "Robbie", "mail2@utn.com.ar");
    palermoGrupo = new Comunidad();
    barracasGrupo = new Comunidad();
    notificadorWhatsapp = mock(MedioNotificador.class);
    notificadorMail = mock(MedioNotificador.class);
    usuarioInformante.setMedioNotificador(notificadorWhatsapp);
    otroUsuario.setMedioNotificador(notificadorWhatsapp);
    palermoGrupo.registrarMiembro(usuarioInformante);
    palermoGrupo.registrarMiembro(otroUsuario);
    RepositorioComunidades.getInstance().guardarComunidad(palermoGrupo);
    RepositorioComunidades.getInstance().guardarComunidad(barracasGrupo);
    miembroInformante = palermoGrupo.getMiembros().stream().filter(m -> m.getUsuario() == usuarioInformante).toList().get(0);
    otroMiembro = palermoGrupo.getMiembros().stream().filter(m -> m.getUsuario() == otroUsuario).toList().get(0);;
    servicio =new Servicio(TipoServicio.ASCENSOR);

    //Miembro NO se puede instanciar ¿Es algo bueno? De momento se deja  asi para testear

  }

  @Test
  void comunidadEfetivizaAltaIncidente() {
    palermoGrupo.abrirIncidente(servicio, "algo");
    Incidente incidente = devolverIncidente(servicio, "algo");
    Assertions.assertTrue(servicio.getHistorialIncidentes().contains(incidente));
    Assertions.assertTrue(palermoGrupo.getIncidentes().stream().map(Incidente::getObservacion).toList().contains("algo"));
    Assertions.assertEquals(incidente.getEstado(),EstadoIncidente.ABIERTO);
  }
/*
  @Test
    ///Falla, por que solo le comunica a miembors interezados, hacer test aparte
  void comunidadNotificaAltaIncidenteATodosLosMiembros() {
    palermoGrupo.abrirIncidente(servicio, "algo");
    Incidente incidente = devolverIncidente(servicio, "algo");
    verify(miembroInformante.getUsuario().medioNotificador).notificar(incidente);
    verify(otroMiembro.getUsuario().medioNotificador).notificar(incidente);
  }*/

  @Test
  void cerrarIncidente() {
    palermoGrupo.abrirIncidente(servicio, "algo");
    Incidente incidente = devolverIncidente(servicio, "algo");
    palermoGrupo.cerrarIncidente(incidente);
    Assertions.assertEquals(incidente.getEstado(), EstadoIncidente.CERRADO);
    Assertions.assertNotNull(incidente.getFechaHoraCierre());

  }


  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i-> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }
}