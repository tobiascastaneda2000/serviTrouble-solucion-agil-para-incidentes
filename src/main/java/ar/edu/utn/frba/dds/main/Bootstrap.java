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
    withTransaction(() -> {

      Usuario usuario = new Usuario("facu", "123456","contacto");
      Horario unHorario = new Horario(10,30);
      usuario.agregarHorario(unHorario);
      persist(unHorario);
      persist(usuario);
      Usuario usuario2 = new Usuario("admin", "123456","contacto");
      usuario2.permisoUsuario = PermisoUsuario.ADMIN;
      persist(usuario2);
      Entidad entidad1 = new Entidad("Fantasy Co.", "fantasy@mail.com");
      Entidad entidad2 = new Entidad("Pixel Innovators", "pixel@mail.com");
      persist(entidad2);
      Establecimiento establecimiento1 = new Establecimiento("nombre 1");
      persist(establecimiento1);
      entidad1.agregarEstablecimiento(establecimiento1);
      Establecimiento establecimiento2 = new Establecimiento("nombre 2");
      persist(establecimiento2);
      entidad1.agregarEstablecimiento(establecimiento2);
      persist(entidad1);

      Servicio servicio1 = new Servicio("baño primer piso",TipoServicio.BAÑO);
      persist(servicio1);
      establecimiento1.agregarServicio(servicio1);

      Servicio servicio2 = new Servicio("baño segundo piso",TipoServicio.BAÑO);
      establecimiento1.agregarServicio(servicio2);
      persist(servicio2);
      persist(establecimiento1);

      Comunidad comunidad1 = new Comunidad("nombre1");
      comunidad1.aniadirServicioInteres(servicio1);
      entityManager().persist(comunidad1);
      Comunidad comunidad2 = new Comunidad("nombre2");
      entityManager().persist(comunidad2);

      comunidad1.agregarUsuario(usuario,PermisoComunidad.ADMIN_COMUNIDAD);
      persist(comunidad1);

      //CARGA CRITERIOS RANKINGS
      CriterioRanking criterioPromedioCierre = new PromedioCierresSemanal();
      CriterioRanking criterioCantidadReportes = new CantidadReportesSemanal();


      RepoRanking.instance.agregarRanking(criterioPromedioCierre);
      RepoRanking.instance.agregarRanking(criterioCantidadReportes);
      entityManager().persist(criterioPromedioCierre);
      entityManager().persist(criterioCantidadReportes);

      // Deberia andar pero lanza error
      //Exception in thread "main" javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not prepare statement
      //Caused by: org.hibernate.exception.SQLGrammarException: could not prepare statement
      //Caused by: org.hsqldb.HsqlException: usuario no tiene privilegios suficientes o objeto no encontrado: CRITERIORANKING

      MainTareasPlanificadas.lanzarRanking();


    });
  }

}
