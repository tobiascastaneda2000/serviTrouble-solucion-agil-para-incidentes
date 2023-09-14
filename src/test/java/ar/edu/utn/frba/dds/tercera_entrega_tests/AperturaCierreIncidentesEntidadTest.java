package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
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
  RepoUsuarios repoUsuarios = new RepoUsuarios();
  Usuario usuario;


  @BeforeEach
  void setUp() {

    usuario = new Usuario(1,"Juan ", "Perez", "unMail");
    repoUsuarios.guardarUsuario(usuario);
    servicio =new Servicio(TipoServicio.ASCENSOR);
    unEstablecimiento = new Establecimiento();
    unaEntidad= new Entidad(1,"razonsocial","unMail");
    unEstablecimiento.agregarServicio(servicio);
    unaEntidad.agregarEstablecimiento(unEstablecimiento);
    usuario.getEntidadesInteres().add(unaEntidad);

  }

  @Test
  void seCreaYAgregaElIncidente() {
    unaEntidad.crearIncidente(servicio,"observacion");
    Incidente incidente = devolverIncidente(servicio, "observacion");
    Assertions.assertTrue(unaEntidad.incidentes.contains(incidente));
    Assertions.assertEquals(1, unaEntidad.incidentes.size());
  }


  public Incidente devolverIncidente(Servicio servicio, String obs) {
    return servicio.getHistorialIncidentes().stream().filter(i-> Objects.equals(i.getObservacion(), obs)).toList().get(0);

  }

}
