package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.MainTareasPlanificadas;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Comunidad;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Miembro;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.PermisoComunidad;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.PermisoUsuario;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.notificador.Horario;
import ar.edu.utn.frba.dds.notificador.MailSender;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.notificador.WhatsAppSender;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 * @author flbulgarelli
 */
public class Bootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
      //CARGA USUARIO

      Usuario usuario = new Usuario("facu", "123456", "contacto");
      Horario unHorario = new Horario(10, 30);
      usuario.agregarHorario(unHorario);
      persist(unHorario);
      persist(usuario);
      Usuario usuario2 = new Usuario("admin", "123456", "contacto");
      usuario2.permisoUsuario = PermisoUsuario.ADMIN;
      persist(usuario2);
      Usuario usuario3 = new Usuario("pepe", "123456", "contacto");
      persist(usuario3);


      //CARGA ENTIDAD FANTASY
      Entidad entidadFantasy = new Entidad("Fantasy Co.", "fantasy@mail.com");
      entidadFantasy.setDescripcion("una descripcion de la organizacion");
      entidadFantasy.setImg("fantasy.jpg");
      persist(entidadFantasy);
      Establecimiento establecimiento1 = new Establecimiento("nombre 1");
      persist(establecimiento1);
      entidadFantasy.agregarEstablecimiento(establecimiento1);
      Establecimiento establecimiento2 = new Establecimiento("nombre 2");
      persist(establecimiento2);
      entidadFantasy.agregarEstablecimiento(establecimiento2);

      Servicio servicio1 = new Servicio("baño primer piso", TipoServicio.BAÑO);
      persist(servicio1);
      establecimiento1.agregarServicio(servicio1);

      Servicio servicio2 = new Servicio("baño segundo piso", TipoServicio.BAÑO);
      establecimiento1.agregarServicio(servicio2);
      persist(servicio2);

      Servicio servicio5 = new Servicio("Ascensor discapacitados",TipoServicio.ASCENSOR);
      establecimiento1.agregarServicio(servicio5);
      persist(servicio5);
      persist(establecimiento1);

      Incidente incidente = new Incidente("Botonera rota",servicio5);
      persist(incidente);
      Incidente incidente2 = new Incidente("Puerta rota",servicio5);
      persist(incidente2);
      Incidente incidente3 = new Incidente("Falta un espejo",servicio5);
      persist(incidente3);
      Incidente incidente4 = new Incidente("El piso se esta levantando",servicio5);
      persist(incidente4);

      //CARGA ENTIDAD PIXEL
      Entidad entidadPixel = new Entidad("Pixel Innovators", "pixel@mail.com");
      entidadPixel.setDescripcion("una descripcion de la organizacion");
      entidadPixel.setImg("pixel.jpg");
      persist(entidadPixel);

      Establecimiento establecimientoA = new Establecimiento("establecimiento A");
      persist(establecimientoA);
      entidadPixel.agregarEstablecimiento(establecimientoA);
      Establecimiento establecimientoB = new Establecimiento("establecimiento B");
      persist(establecimientoB);
      entidadPixel.agregarEstablecimiento(establecimientoB);

      Servicio servicio3 = new Servicio("baño tercer piso", TipoServicio.BAÑO);
      persist(servicio3);
      establecimientoA.agregarServicio(servicio3);

      Servicio servicio4 = new Servicio("ascensor", TipoServicio.ASCENSOR);
      establecimientoB.agregarServicio(servicio4);
      persist(servicio4);

      //CARGA COMUNIDADES
      Comunidad comunidad1 = new Comunidad("nombre1");
      comunidad1.aniadirServicioInteres(servicio1);
      comunidad1.incidentes.add(incidente);
      comunidad1.incidentes.add(incidente2);
      comunidad1.incidentes.add(incidente3);
      comunidad1.incidentes.add(incidente4);
      entityManager().persist(comunidad1);

      Comunidad comunidad2 = new Comunidad("nombre2");
      entityManager().persist(comunidad2);

      comunidad1.agregarUsuario(usuario, PermisoComunidad.ADMIN_COMUNIDAD);
      comunidad1.agregarUsuario(usuario3, PermisoComunidad.USUARIO_COMUNIDAD);
      persist(comunidad1);

      //CARGA CRITERIOS RANKINGS
      CriterioRanking criterioPromedioCierre = new PromedioCierresSemanal();
      CriterioRanking criterioCantidadReportes = new CantidadReportesSemanal();

      RepoRanking.getInstance().add(criterioPromedioCierre);
      RepoRanking.getInstance().add(criterioCantidadReportes);

      MainTareasPlanificadas.lanzarRanking();

  }

}
