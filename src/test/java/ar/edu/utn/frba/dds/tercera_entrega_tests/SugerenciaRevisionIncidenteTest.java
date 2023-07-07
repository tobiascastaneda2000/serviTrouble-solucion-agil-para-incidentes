package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Miembro;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Departamento;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioGeoRef;
import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.ServicioLocalizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SugerenciaRevisionIncidenteTest {

  /*
  Dentro de Miembro, porque uso this.
  Miembro<<estaCercaMiembro(servicio) {
    this.devolverUbicacionMiembro() == RepositorioEntidades.getInstance().devolverUbicacionServicio()
  }

  RepositorioEntidad<< devolverLocalizacion(Servicio servicio){
    this.entidades.map(e->e.getEstablecimientos).filter(e->e.getServicios.contains(servicio)).get(0).getLocalizacion()
  }

  RepositorioComunidad>>detectarCercania(servicio){
    this.comunidades.flatmap(c->c.getMiembros).filter(m->m.estaCerca(servicio)).forEach(m->m.getUsuario().getMedioNotificacion.notificarSugerenciaRevisionIncidente(servicio))
  }

   */

  Usuario usuarioCaballito;
  Miembro miembroCaballito;
  Localizacion localizacion;
  Establecimiento establecimientoCaballito;
  Entidad entidadCaballito;
  RepoEntidades repoEntidadesCaballito;
  Servicio servicioCaballito;
  Comunidad comunicadCaballito;
  ServicioLocalizacion servicioLocalizacionCaballito;

  @BeforeEach
  void setUp() throws IOException {
    usuarioCaballito = new Usuario(1, "a", "b", "mail@gmail.com");
    comunicadCaballito = new Comunidad();
    comunicadCaballito.registrarMiembro(usuarioCaballito);
    repoEntidadesCaballito.guardarEntidad(entidadCaballito);
    entidadCaballito.agregarEstablecimiento(establecimientoCaballito);
    establecimientoCaballito.agregarServicio(servicioCaballito);
    localizacion = new Departamento();
    establecimientoCaballito.setLocalizacion(localizacion);
    servicioLocalizacionCaballito = mock(ServicioGeoRef.class);
    usuarioCaballito.setServicioLocalizacion(servicioLocalizacionCaballito);
    // comunicadCaballito.abrirIncidente(servicioCaballito, "Incidente Caballito");
    when(servicioLocalizacionCaballito.getDepartamentos().stream().toList().get(0)).thenReturn((Departamento) localizacion);
  }

  @Test
  @DisplayName("Notificar al miembro cercano")
  void notificarMiembroCercano() {}
}
