package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ControllerIncidentes implements WithSimplePersistenceUnit {


  public ModelAndView verDetalle(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request,response);
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id",id).getResultList().get(0);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("incidente",incidente);
    modelo.put("estado",incidente.estadoIncidente.toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String fechaAperturaFormateada = incidente.fechaHoraAbre.format(formatter);
    modelo.put("fechaApertura",fechaAperturaFormateada);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "detalleIncidente.html.hbs");
  }

  public ModelAndView verDetalleIncidenteCerrado(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request,response);
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id",id).getResultList().get(0);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("incidente",incidente);
    modelo.put("estado",incidente.estadoIncidente.toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String fechaAperturaFormateada = incidente.fechaHoraAbre.format(formatter);
    String fechaCierreFormateada = incidente.fechaHoraCierre.format(formatter);
    modelo.put("fechaApertura",fechaAperturaFormateada);
    modelo.put("fechaCierre",fechaCierreFormateada);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "detalleIncidenteCerrado.html.hbs");
  }


  public ModelAndView cerrarIncidente(Request request, Response response) {
    String idIncidente = request.queryParams("idIncidente");
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id",Long.parseLong(idIncidente)).getResultList().get(0);
    Comunidad comunidad = RepositorioComunidades.getInstance().contieneIncidente(incidente);
    comunidad.cerrarIncidente(incidente);
    response.redirect("/home");
    return null;
  }

  public ModelAndView mostrarIncidentesSugeridos(Request request, Response response){
    Long id = Usuario.redirigirSesionNoIniciada(request,response);
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(id);
    Map<String, Object> modelo = new HashMap<>();
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    List<Incidente> incidentes = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentes().stream()).filter(incidente -> usuario.esIncidenteCercano(incidente)).collect(Collectors.toList());
    if (incidentes.isEmpty()) {
      modelo.put("mensajeIncidentes", "No tienes incidentes cercanos para revisar");
    }
    modelo.put("usuario", usuario);
    modelo.put("incidentes", incidentes);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "incidenteSugerido.html.hbs");
  }

}
