package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Miembro;
import ar.edu.utn.frba.dds.notificador.MailSender;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConsultaIncidentesTest {

  Comunidad comunidadFlores;
  Servicio servicioFlores1;
  Servicio servicioFlores2;
  /*Incidente incidenteAbierto1;
  Incidente incidenteAbierto2;*/
  Optional<Incidente> incidenteCerrado1;
  Optional<Incidente> incidenteCerrado2;

  @BeforeEach
  void setUp() {
    comunidadFlores = new Comunidad();
    servicioFlores1 = new Servicio(TipoServicio.ASCENSOR);
    servicioFlores2 = new Servicio(TipoServicio.ESCALERA_MECANICA);
    /*incidenteAbierto1 = new Incidente("Incidente abierto 1");
    incidenteAbierto2 = new Incidente("Incidente abierto 2")/*/
    comunidadFlores.abrirIncidente(servicioFlores1, "Incidente abierto 1");
    comunidadFlores.abrirIncidente(servicioFlores2, "Incidente abierto 2");
    comunidadFlores.abrirIncidente(servicioFlores1, "Incidente cerrado 1");
    comunidadFlores.abrirIncidente(servicioFlores2, "Incidente cerrado 2");
    comunidadFlores.cerrarIncidente(comunidadFlores.getIncidentes().get(2));
    comunidadFlores.cerrarIncidente(comunidadFlores.getIncidentes().get(3));

  }

  @Test
  @DisplayName("Consultar por los dos incidentes abiertos")
  void consultarIncidentesAbiertos() {
    List<Incidente> listaDeIncidentesAbiertos = comunidadFlores.incidentesPorEstado(EstadoIncidente.ABIERTO);
    Assertions.assertEquals(2, listaDeIncidentesAbiertos.size());
    for(int i = 0; i < listaDeIncidentesAbiertos.size(); i++) {
      Assertions.assertEquals(listaDeIncidentesAbiertos.get(i).getObservacion(), Arrays.asList("Incidente abierto 1", "Incidente abierto 2").get(i));
    }
  }

  @Test
  @DisplayName("Consultar por los dos incidentes cerrados")
  void consultarIncidentesCerrados() {
    List<Incidente> listaDeIncidentesCerrados = comunidadFlores.incidentesPorEstado(EstadoIncidente.CERRADO);
    Assertions.assertEquals(2, listaDeIncidentesCerrados.size());
    for(int i = 0; i < listaDeIncidentesCerrados.size(); i++) {
      Assertions.assertEquals(listaDeIncidentesCerrados.get(i).getObservacion(), Arrays.asList("Incidente cerrado 1", "Incidente cerrado 2").get(i));
    }
  }
}
