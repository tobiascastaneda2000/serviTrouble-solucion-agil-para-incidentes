package ar.edu.utn.frba.dds.controller;


import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.format.DateTimeFormatter;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ControllerComunidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarComunidades(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    modelo.put("comunidades", comunidades);
    return new ModelAndView(modelo, "comunidades.html.hbs");
  }


  public ModelAndView mostrarIncidentes(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    String id = request.params(":id");
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<Incidente> incidentesAbiertos = comunidad.incidentes;
    List<Incidente> incidentesCerrados = comunidad.incidentesCerrados;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    for (Incidente incidente : incidentesAbiertos) {
      incidente.fechaApertura = incidente.fechaHoraAbre.format(formatter);
    }

    for (Incidente incidente : incidentesCerrados) {
      incidente.fechaApertura = incidente.fechaHoraAbre.format(formatter);
      incidente.fechaCierre = incidente.fechaHoraCierre.format(formatter);
    }

    modelo.put("incidentesAbiertos", incidentesAbiertos);
    modelo.put("incidentesCerrados", incidentesCerrados);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "incidentes.html.hbs");
  }


  public ModelAndView verComunidadesAdministrables(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    modelo.put("anio", LocalDate.now().getYear());
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    List<Comunidad> comunidadesAdmin = comunidades.stream().filter(c -> c.miembroEsAdmin(usuario)).toList();
    if (comunidadesAdmin.isEmpty()) {
      String vacio = "No eres administrador de ninguna comunidad";
      modelo.put("vacio", vacio);
    }
    modelo.put("comunidades", comunidadesAdmin);
    return new ModelAndView(modelo, "comunidadesAdministrables.html.hbs");
  }

  public ModelAndView verMiembros(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(idsession);
    String id = request.params(":id");
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    if (!usuario.comunidadesPertenecientes().contains(comunidad)) {
      response.redirect("/home");
    }
    List<Usuario> usuarios = comunidad.miembros.stream().map(m -> m.usuario).toList();
    modelo.put("nombreComunidad", comunidad.nombre);
    modelo.put("usuarios", usuarios);
    return new ModelAndView(modelo, "verUsuariosComunidad.html.hbs");
  }

  public ModelAndView eliminarMiembro(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuariosession = RepoUsuarios.getInstance().getOne(idsession);
    String idusuario = request.queryParams("idusuario");
    String id = request.params(":id");
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    if (!usuariosession.comunidadesPertenecientes().contains(comunidad)) {
      response.redirect("/home");
    }
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(Long.parseLong(idusuario));
    Miembro miembro = comunidad.miembros.stream().filter(m -> m.usuario.equals(usuario)).toList().get(0);
    getTransaction().begin();
    comunidad.miembros.remove(miembro);
    remove(miembro);
    getTransaction().commit();
    response.redirect("/admin-comunidades");
    return null;
  }
}
