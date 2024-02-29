package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Arrays;
import java.util.HashSet;
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
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id", id).getResultList().get(0);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("incidente", incidente);
    modelo.put("estado", incidente.estadoIncidente.toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String fechaAperturaFormateada = incidente.fechaHoraAbre.format(formatter);
    modelo.put("fechaApertura", fechaAperturaFormateada);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "detalleIncidente.html.hbs");
  }

  public ModelAndView verDetalleIncidenteCerrado(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id", id).getResultList().get(0);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("incidente", incidente);
    modelo.put("estado", incidente.estadoIncidente.toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String fechaAperturaFormateada = incidente.fechaHoraAbre.format(formatter);
    String fechaCierreFormateada = incidente.fechaHoraCierre.format(formatter);
    modelo.put("fechaApertura", fechaAperturaFormateada);
    modelo.put("fechaCierre", fechaCierreFormateada);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "detalleIncidenteCerrado.html.hbs");
  }


  public ModelAndView cerrarIncidente(Request request, Response response) {
    String idIncidente = request.queryParams("idIncidente");
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id", Long.parseLong(idIncidente)).getResultList().get(0);
    Comunidad comunidad = RepositorioComunidades.getInstance().contieneIncidente(incidente);
    Long idComunidad = comunidad.id;
    withTransaction(() -> {
      comunidad.cerrarIncidente(incidente);
    });

    response.redirect("/comunidades/"+idComunidad);
    return null;
  }


  public ModelAndView mostrarIncidentesSugeridosPaginados(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(id);
    String pag = request.queryParams("pag");
    int idPagina = Integer.parseInt(pag);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("nombreUsuario",usuario.usuario);
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    List<Incidente> incidentes = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentes().stream()).filter(incidente -> usuario.esIncidenteCercano(incidente)).collect(Collectors.toList());
    if (incidentes.isEmpty()) {
      modelo.put("mensajeIncidentes", "No tienes incidentes cercanos para revisar");
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    for (Incidente incidente : incidentes) {
      incidente.fechaApertura = incidente.fechaHoraAbre.format(formatter);
    }
    int i=0;
    int k=1;
    HashSet<Integer> paginas = new HashSet<Integer>();
    paginas.add(1);
    for (Incidente incidente : incidentes) {
      i++;
      if(i>8){
        k++;
        paginas.add(k);
        i=0;
      }
    }


    int limiteInferior = (idPagina - 1) * 8;
    int limiteSuperior = limiteInferior + 8;
    try {
      List<Incidente> incidentesPaginados = incidentes.subList(limiteInferior,limiteSuperior);
      modelo.put("usuario", usuario);
      modelo.put("incidentes", incidentesPaginados);
      modelo.put("paginas", paginas);
      List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
      modelo.put("criterios", criterio);
      return new ModelAndView(modelo, "incidenteSugerido.html.hbs");
    }
    catch(Exception e){
        try {
          List<Incidente> incidentesPaginados = incidentes.subList(limiteInferior,incidentes.size());
          if(incidentesPaginados.size() == 0){
            throw new Exception("No hay incidentes para esta pagina");
          }
          modelo.put("usuario", usuario);
          modelo.put("incidentes", incidentesPaginados);
          modelo.put("paginas", paginas);
          List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
          modelo.put("criterios", criterio);
          return new ModelAndView(modelo, "incidenteSugerido.html.hbs");
        }
        catch (Exception e2){
          response.redirect("/incidentes/sugeridos?pag=1");
          return null;
        }
    }
  }

  public ModelAndView verDetalleIncidente(Request request, Response response){
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Map<String, Object> modelo = new HashMap<>();
    String id = request.params(":id");
    Incidente incidente = RepoIncidentes.getInstance().getOne(Long.parseLong(id));
    modelo.put("anio", LocalDate.now().getYear());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    incidente.fechaApertura = incidente.fechaHoraAbre.format(formatter);

    if(incidente.estadoIncidente.equals(EstadoIncidente.CERRADO)){
      incidente.fechaCierre = incidente.fechaHoraCierre.format(formatter);
    }

    modelo.put("incidente", incidente);
    return new ModelAndView(modelo, "verDetalleIncidente.html.hbs");
  }


}

