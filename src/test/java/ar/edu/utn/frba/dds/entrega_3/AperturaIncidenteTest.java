package ar.edu.utn.frba.dds.entrega_3;

import ar.edu.utn.frba.dds.Comunidad;
import ar.edu.utn.frba.dds.Incidente;
import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.MedioNotificador;
import ar.edu.utn.frba.dds.RepositorioComunidades;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AperturaIncidenteTest {

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
    Assertions.assertTrue(palermoGrupo.getIncidentesAResolver().stream().map(Incidente::getServicio).toList().contains(TipoServicio.ASCENSOR));
    Assertions.assertTrue(palermoGrupo.getIncidentesAResolver().stream().map(Incidente::getObservacion).toList().contains("algo"));
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
    Assertions.assertFalse(palermoGrupo.getIncidentesAResolver().stream().map(Incidente::getServicio).toList().contains(TipoServicio.ASCENSOR));
    Assertions.assertFalse(palermoGrupo.getIncidentesAResolver().stream().map(Incidente::getObservacion).toList().contains("algo"));
    Assertions.assertTrue(palermoGrupo.getIncidentesResueltos().stream().map(Incidente::getServicio).toList().contains(TipoServicio.ASCENSOR));
    Assertions.assertTrue(palermoGrupo.getIncidentesResueltos().stream().map(Incidente::getObservacion).toList().contains("algo"));
    Assertions.assertNotNull(incidente.getFechaHoraCierre());

  }


  public Incidente devolverIncidente(TipoServicio servicio, String obs, Comunidad comunidad) {
    Incidente lista1 = comunidad.getIncidentesAResolver().stream().filter(i -> i.getServicio() == servicio && Objects.equals(i.getObservacion(), obs)).findFirst().orElse(null);
    Incidente lista2 = comunidad.getIncidentesResueltos().stream().filter(i -> i.getServicio() == servicio && Objects.equals(i.getObservacion(), obs)).findFirst().orElse(null);
    if (lista1 != null) {
      return lista1;
    } else {
      return lista2;
    }

  }
}