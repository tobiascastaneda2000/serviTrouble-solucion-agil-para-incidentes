package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.entidades_y_servicios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.format.DateTimeFormatter;
public class ControllerIncidentes implements WithSimplePersistenceUnit {

  public ModelAndView mostrarIncidenteCreado(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "incidenteCreado.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView errorIncidente(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "incidenteNoCreado.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView verDetalle(Request request, Response response) {

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
          .setParameter("id",Long.parseLong(id)).getResultList().get(0);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      modelo.put("incidente",incidente);
      modelo.put("estado",incidente.estadoIncidente.toString());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
      String fechaAperturaFormateada = incidente.fechaHoraAbre.format(formatter);
      modelo.put("fechaApertura",fechaAperturaFormateada);
      return new ModelAndView(modelo, "detalleIncidente.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView verDetalleIncidenteCerrado(Request request, Response response) {

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
          .setParameter("id",Long.parseLong(id)).getResultList().get(0);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      modelo.put("incidente",incidente);
      modelo.put("estado",incidente.estadoIncidente.toString());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
      String fechaAperturaFormateada = incidente.fechaHoraAbre.format(formatter);
      String fechaCierreFormateada = incidente.fechaHoraCierre.format(formatter);
      modelo.put("fechaApertura",fechaAperturaFormateada);
      modelo.put("fechaCierre",fechaCierreFormateada);
      return new ModelAndView(modelo, "detalleIncidenteCerrado.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView cerrarIncidente(Request request, Response response) {

    String id = request.params(":id");
    Incidente incidente = entityManager().createQuery("from Incidente where id=:id", Incidente.class)
        .setParameter("id",Long.parseLong(id)).getResultList().get(0);
    Comunidad comunidad = RepositorioComunidades.instance.contieneIncidente(incidente);
    comunidad.cerrarIncidente(incidente);
    response.redirect("/home");
    return null;
  }

  public ModelAndView mostrarIncidentesSugeridos(Request request, Response response){
    Long user_id = revisarSesionIniciada(request,response);
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(user_id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("usuario", usuario);
    return new ModelAndView(modelo, "incidenteSugerido.html.hbs");
    //return null;
  }

  public Long revisarSesionIniciada(Request request, Response response){
    if (request.session().attribute("user_id")==null) response.redirect("/");
    return request.session().attribute("user_id");
  }

}
