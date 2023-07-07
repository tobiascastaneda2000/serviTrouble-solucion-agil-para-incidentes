package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.RepoUsuarios;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AperturaCierreIncidentesEntidad {

  Servicio servicio;
  Entidad unaEntidad;
  Establecimiento unEstablecimiento;
  RepoUsuarios repoUsuarios = new RepoUsuarios();
  Usuario usuario;

  @BeforeEach
  void setUp() {

    usuario = new Usuario("Juan ", "Perez", "unMail");
    repoUsuarios.guardarUsuario(usuario);
    servicio =new Servicio(TipoServicio.ASCENSOR);
    unEstablecimiento = new Establecimiento();
    unaEntidad= new Entidad(1,"razonsocial","unMail");
    unEstablecimiento.agregarServicio(servicio);
    unaEntidad.agregarEstablecimiento(unEstablecimiento);
  }

  @Test
  void seCreaYAgregaElIncidente() {
    Incidente incidente=unaEntidad.crearIncidente(unEstablecimiento,servicio,"observacion");
    Assertions.assertTrue(unaEntidad.incidentes.contains(incidente));
    Assertions.assertTrue(unaEntidad.incidentes.size()==1);
  }

}
