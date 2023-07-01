package ar.edu.utn.frba.dds.entrega_3;

import ar.edu.utn.frba.dds.Comunidad;
import ar.edu.utn.frba.dds.Incidente;
import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.MedioNotificador;
import ar.edu.utn.frba.dds.RepositorioComunidades;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.WhatsAppSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class AperturaIncidenteTest {

  Usuario usuario;
  Comunidad palermoGrupo;
  Comunidad barracasGrupo;
  MedioNotificador notificadorWhatsapp;

  Miembro miembro;

  @BeforeEach
  void setUp() {
     usuario = new Usuario("Leonardo ", "Dicaprio" );
     palermoGrupo = new Comunidad();
     barracasGrupo = new Comunidad();
     notificadorWhatsapp = new WhatsAppSender();
     palermoGrupo.registrarMiembro(usuario, notificadorWhatsapp);
     barracasGrupo.registrarMiembro(usuario, notificadorWhatsapp);
     RepositorioComunidades.getInstance().guardarComunidad(palermoGrupo);
     RepositorioComunidades.getInstance().guardarComunidad(barracasGrupo);
      miembro =
         palermoGrupo.getMiembros().stream().filter(m -> m.getUsuario() == usuario).toList().get(0);
     //Miembro NO se puede instanciar ¿Es algo bueno? De momento se deja  asi para testear




  }

  @Test
  void comunidadGuardaInforme() {
    miembro.informarIncidente(TipoServicio.ASCENSOR,"algo");

    Assertions.assertTrue(palermoGrupo.getIncidentesAResolver().stream().map(Incidente::getServicio).toList().contains(TipoServicio.ASCENSOR));
    Assertions.assertTrue(palermoGrupo.getIncidentesAResolver().stream().map(Incidente::getObservacion).toList().contains("algo"));
  }

  @Test
  void cerrarIncidente() {
  }
}