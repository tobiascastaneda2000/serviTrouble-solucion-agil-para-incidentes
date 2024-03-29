package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.entidades.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ControllerEstablecimientos implements WithSimplePersistenceUnit {

  public ModelAndView mostrarEstablecimientos(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    String id = request.params(":id");
    Entidad entidad = RepoEntidades.getInstance().getOne(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    List<Establecimiento> establecimientos = entidad.getEstablecimientos();
    modelo.put("establecimientos", establecimientos);
    modelo.put("idEntidad",id);
    Usuario usuario = RepoUsuarios.getInstance().getOne(idsession);
    modelo.put("nombreUsuario",usuario.usuario);
    if (request.cookie("creado") == null) {
      return new ModelAndView(modelo, "establecimientos.html.hbs");
    } else {
      response.removeCookie("creado");
      return new ModelAndView(modelo, "incidenteCreado.html.hbs");
    }
  }

  public ModelAndView mostrarIncidentes(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    String entidadId = request.params(":entidadId");
    String establecimientoId = request.params(":establecimientoId");
    Entidad entidad = RepoEntidades.getInstance().getOne(Long.parseLong(entidadId));

    Establecimiento establecimiento = entidad.getEstablecimientos().stream()
        .filter(e -> e.getId().equals(Long.parseLong(establecimientoId)))
        .findFirst()
        .get();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("entidadId", entidadId);
    modelo.put("establecimientoId", establecimientoId);
    modelo.put("criterios", criterio);
    modelo.put("incidentes", establecimiento.getIncidentes());
    Usuario usuario = RepoUsuarios.getInstance().getOne(idsession);
    modelo.put("nombreUsuario",usuario.usuario);
    return new ModelAndView(modelo, "incidentesEstablecimiento.html.hbs");
  }

  public ModelAndView cargarIncidente(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    String idEstablecimiento = request.params(":id");
    Establecimiento establecimiento = entityManager()
        .createQuery("from Establecimiento where id = :id", Establecimiento.class)
        .setParameter("id", Long.parseLong(idEstablecimiento))
        .getResultList()
        .get(0);
    Map<String, Object> modelo = new HashMap<>();
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    List<Servicio> servicios = establecimiento.getServicios();
    modelo.put("nombreEstablecimiento", establecimiento.nombre);
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("servicios", servicios);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    modelo.put("nombreUsuario",usuario.usuario);
    return new ModelAndView(modelo, "servicio.html.hbs");
  }

  public ModelAndView crearIncidente(Request request, Response response) {
    try {
      String idEntidad = request.queryParams("idEntidad");
      String id = request.queryParams("servicio");
      String observacion = request.queryParams("observacion");
      Servicio servicio = entityManager()
          .createQuery("from Servicio where id = :id", Servicio.class)
          .setParameter("id", Long.parseLong(id))
          .getResultList()
          .get(0);
      Long userid = request.session().attribute("user_id");
      Usuario usuario = RepoUsuarios.getInstance().getOne(userid);
      
      withTransaction(() -> {
        usuario.abrirIncidente(servicio, observacion);
      });

      response.cookie("creado","si",1);
      response.redirect("/entidades/" + idEntidad);
    } catch (Exception e) {
      String idEstablecimiento = request.params(":id");
      Establecimiento establecimiento = entityManager()
          .createQuery("from Establecimiento where id = :id", Establecimiento.class)
          .setParameter("id", Long.parseLong(idEstablecimiento))
          .getResultList()
          .get(0);
      Map<String, Object> modelo = new HashMap<>();
      List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
      modelo.put("criterios", criterio);
      List<Servicio> servicios = establecimiento.getServicios();
      modelo.put("nombreEstablecimiento", establecimiento.nombre);
      modelo.put("anio", LocalDate.now().getYear());
      modelo.put("servicios", servicios);
      return new ModelAndView(modelo, "incidenteNoCreado.html.hbs");
    }
    return null;
  }

}
