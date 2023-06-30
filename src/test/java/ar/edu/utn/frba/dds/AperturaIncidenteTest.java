package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;

class AperturaIncidenteTest {

  Usuario usuario;
  Comunidad palermoGrupo;
  Comunidad barracasGrupo;
  Notificador notificadorWhatsapp;

  @BeforeEach
  void setUp() {
     usuario = new Usuario("Leonardo ", "Dicaprio" );
     palermoGrupo = new Comunidad();
     barracasGrupo = new Comunidad();
     notificadorWhatsapp = new WhatsAppNotificador();
     palermoGrupo.registrarMiembro(usuario, notificadorWhatsapp);
     RepositorioComunidades.getInstance().guardarComunidad(palermoGrupo);
     RepositorioComunidades.getInstance().guardarComunidad(barracasGrupo);
     Miembro miembro =
         palermoGrupo.getMiembros().stream().filter(m -> m.getUsuario() == usuario).toList().get(0);
     //Mucho codigo para obtener el miembro


  }

  @Test
  void informarIncidente() {
  }

  @Test
  void cerrarIncidente() {
  }
}