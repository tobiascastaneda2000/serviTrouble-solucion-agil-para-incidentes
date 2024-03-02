package ar.edu.utn.frba.dds.controller;


import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.format.DateTimeFormatter;

import java.util.*;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;

import java.time.LocalDate;

public class ControllerComunidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarComunidades(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    modelo.put("nombreUsuario",usuario.usuario);

    String filtro = request.queryParams("filtrado");
    if (filtro!=null){
      comunidades = comunidades.stream().filter(c -> c.getNombre().toLowerCase().contains(filtro.toLowerCase())).toList();
      modelo.put("filtro",filtro);
    }



    String idPagina = request.queryParams("pagina");
    if(idPagina==null){
      idPagina="1";
    }
    int limiteInferior = (Integer.parseInt(idPagina) - 1) * 9;
    int limiteSuperior = limiteInferior + 9;

    int i=0;
    int k=1;
    HashSet<Integer> paginas = new HashSet<Integer>();
    paginas.add(1);
    for (Comunidad comunidad : comunidades) {
      i++;
      if(i>9){
        k++;
        paginas.add(k);
        i=0;
      }
    }

    try {
      List<Comunidad> comunidadesPaginadas = comunidades.subList(limiteInferior,limiteSuperior);
      modelo.put("comunidades", comunidadesPaginadas);
      modelo.put("paginas", paginas);
      return new ModelAndView(modelo, "comunidades.html.hbs");
    }
    catch(Exception e) {
      try {
        List<Comunidad> comunidadesPaginadas = comunidades.subList(limiteInferior, comunidades.size());
        if (comunidadesPaginadas.size() == 0) {
          throw new Exception("No hay incidentes para esta pagina");
        }
        modelo.put("comunidades", comunidadesPaginadas);
        modelo.put("paginas", paginas);
        return new ModelAndView(modelo, "comunidades.html.hbs");
      } catch (Exception e2) {
        return new ModelAndView(modelo, "comunidades.html.hbs");
      }
    }
  }


  public ModelAndView mostrarIncidentes(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    String id = request.params(":id");
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("idComunidad", id);
    List<Incidente> incidentesAbiertos = comunidad.incidentes;
    List<Incidente> incidentesCerrados = comunidad.incidentesCerrados;
    List<Incidente> incidentes = new ArrayList<Incidente>();

    Usuario usuario = RepoUsuarios.getInstance().getOne(idsession);
    modelo.put("nombreUsuario",usuario.usuario);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    for (Incidente incidente : incidentesAbiertos) {
      incidente.fechaApertura = incidente.fechaHoraAbre.format(formatter);
    }

    for (Incidente incidente : incidentesCerrados) {
      incidente.fechaApertura = incidente.fechaHoraAbre.format(formatter);
      incidente.fechaCierre = incidente.fechaHoraCierre.format(formatter);
    }

    incidentes.addAll(incidentesAbiertos);
    incidentes.addAll(incidentesCerrados);
    modelo.put("incidentes", incidentes);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "incidentes.html.hbs");
  }
}
