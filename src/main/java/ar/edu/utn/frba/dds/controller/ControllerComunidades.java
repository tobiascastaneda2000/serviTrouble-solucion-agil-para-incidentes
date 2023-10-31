package ar.edu.utn.frba.dds.controller;


import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.text.CompactNumberFormat;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ControllerComunidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarComunidades(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(id);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      //List<Comunidad> comunidades = RepositorioComunidades.getInstance().listarComunidades();
      List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
      modelo.put("comunidades", comunidades);
      return new ModelAndView(modelo, "comunidades.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView mostrarIncidentes(Request request, Response response) {


    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Comunidad comunidad = RepositorioComunidades.instance.buscarPorId(Long.parseLong(id));
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Incidente> incidentesAbiertos = comunidad.incidentes;
      List<Incidente> incidentesCerrados = comunidad.incidentesCerrados;
      modelo.put("incidentesAbiertos", incidentesAbiertos);
      modelo.put("incidentesCerrados", incidentesCerrados);
      return new ModelAndView(modelo, "incidentes.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }
  }

}
