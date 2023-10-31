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

public class ControllerEstablecimientos implements WithSimplePersistenceUnit {

  public ModelAndView mostrarEstablecimientos(Request request, Response response) {


    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Entidad entidad = RepoEntidades.instance.buscarPorId(Integer.parseInt(id));
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Establecimiento> establecimientos = entidad.getEstablecimientos();
      modelo.put("establecimientos", establecimientos);
      return new ModelAndView(modelo, "establecimientos.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView cargarIncidente(Request request, Response response) {

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String idEstablecimiento = request.params(":id");
      Establecimiento establecimiento = entityManager()
          .createQuery("from Establecimiento where id = :id", Establecimiento.class)
          .setParameter("id", Long.parseLong(idEstablecimiento))
          .getResultList()
          .get(0);
      Map<String, Object> modelo = new HashMap<>();
      List<Servicio> servicios = establecimiento.getServicios();
      modelo.put("anio", LocalDate.now().getYear());
      modelo.put("servicios", servicios);
      return new ModelAndView(modelo, "servicio.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView crearIncidente(Request request, Response response) {


    try {
          String id = request.queryParams("servicio");
          String observacion = request.queryParams("observacion");
          Servicio servicio = entityManager()
          .createQuery("from Servicio where id = :id", Servicio.class)
          .setParameter("id", Long.parseLong(id))
          .getResultList()
          .get(0);
          Long userid = request.session().attribute("user_id");
          Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(userid);
          usuario.abrirIncidente(servicio,observacion);
          response.redirect("/incidente-creado");
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("error");
      response.redirect("/Incidente-No-Creado");
      return null;
    }
  }
  
}
