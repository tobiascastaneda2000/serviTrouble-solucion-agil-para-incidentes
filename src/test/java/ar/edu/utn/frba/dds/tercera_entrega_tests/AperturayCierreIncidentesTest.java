package ar.edu.utn.frba.dds.tercera_entrega_tests;

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

class AperturayCierreIncidentesTest {

  Usuario usuarioInformante;

  Usuario otroUsuario;
  Comunidad palermoGrupo;
  Comunidad barracasGrupo;
  MedioNotificador notificadorWhatsapp;
  MedioNotificador notificadorMail;

  Miembro otroMiembro;

  Miembro miembroInformante;

  @BeforeEach
  void setUp() {
    usuarioInformante = new Usuario("Leonardo ", "Dicaprio", "mail@utn.com.ar");
    otroUsuario = new Usuario("Margot ", "Robbie", "mail2@utn.com.ar");
    palermoGrupo = new Comunidad();
    barracasGrupo = new Comunidad();
    notificadorWhatsapp = mock(MedioNotificador.class);
    notificadorMail = mock(MedioNotificador.class);
    palermoGrupo.registrarMiembro(usuarioInformante, notificadorWhatsapp);
    palermoGrupo.registrarMiembro(otroUsuario, notificadorWhatsapp);
    RepositorioComunidades.getInstance().guardarComunidad(palermoGrupo);
    RepositorioComunidades.getInstance().guardarComunidad(barracasGrupo);
    miembroInformante = palermoGrupo.getUnMiembro(usuarioInformante);
    otroMiembro = palermoGrupo.getUnMiembro(otroUsuario);

    //Miembro NO se puede instanciar ¿Es algo bueno? De momento se deja  asi para testear

  }

  @Test
  void comunidadEfetivizaAltaIncidente() {
    miembroInformante.informarIncidente(TipoServicio.ASCENSOR, "algo");
    Assertions.assertTrue(palermoGrupo.getIncidentes().stream().map(Incidente::getServicio).toList().contains(TipoServicio.ASCENSOR));
    Assertions.assertTrue(palermoGrupo.getIncidentes().stream().map(Incidente::getObservacion).toList().contains("algo"));
  }

  @Test
    ///Falla, por que solo le comunica a miembors interezados, hacer test aparte
  void comunidadNotificaAltaIncidenteATodosLosMiembros() {
    miembroInformante.informarIncidente(TipoServicio.ASCENSOR, "algo");
    Incidente incidente = devolverIncidente(TipoServicio.ASCENSOR, "algo", palermoGrupo);
    verify(miembroInformante.getTipoNotificador()).notificar("Apertura Incidente", miembroInformante.getCorreo(), incidente);
    verify(otroMiembro.getTipoNotificador()).notificar("Apertura Incidente", otroMiembro.getCorreo(), incidente);
  }

  @Test
  void cerrarIncidente() {
    miembroInformante.informarIncidente(TipoServicio.ASCENSOR, "algo");
    Incidente incidente = devolverIncidente(TipoServicio.ASCENSOR, "algo", palermoGrupo);
    miembroInformante.cerrarIncidente(incidente);
    Assertions.assertEquals(incidente.getEstado(), EstadoIncidente.CERRADO);
    Assertions.assertNotNull(incidente.getFechaHoraCierre());

  }


  public Incidente devolverIncidente(TipoServicio servicio, String obs, Comunidad comunidad) {
    return comunidad.getIncidentes().stream().filter(i -> i.getServicio() == servicio && Objects.equals(i.getObservacion(), obs)).findFirst().orElse(null);

  }
}