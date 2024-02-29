package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.RankingProgramado;
import ar.edu.utn.frba.dds.ServicesLocators.ServiceLocatorMedioNotificador;
import ar.edu.utn.frba.dds.ServicesLocators.ServiceLocatorUbicacion;
import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.comunidad.PermisoComunidad;
import ar.edu.utn.frba.dds.comunidad.PermisoUsuario;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.entidades.Servicio;
import ar.edu.utn.frba.dds.entidades.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.notificador.Horario;
import ar.edu.utn.frba.dds.notificador.MailSender;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import ar.edu.utn.frba.dds.repositorios.*;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion1;
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
    withTransaction(() -> {

      new ServiceLocatorUbicacion().setServicios("servicioUbicacion",new ServicioUbicacion1());
      new ServiceLocatorMedioNotificador().setServicios("mailSender", new MailSender());


      Usuario usuarioComun1 = new Usuario("facu", "123456", "famartnez@frba.utn.edu.ar");
      Horario unHorario = new Horario(15, 43);
      usuarioComun1.permisoUsuario = PermisoUsuario.USUARIO_COMUN;
      usuarioComun1.agregarHorario(unHorario);
      usuarioComun1.medioNotificador = ServiceLocatorMedioNotificador.getServicio("mailSender");
      RepoUsuarios.getInstance().add(usuarioComun1);

      Usuario usuarioAdmin = new Usuario("admin", "123456", "contacto");
      usuarioAdmin.permisoUsuario = PermisoUsuario.ADMIN;
      RepoUsuarios.getInstance().add(usuarioAdmin);

      Usuario usuarioComun2 = new Usuario("pepe", "123456", "contacto");
      RepoUsuarios.getInstance().add(usuarioComun2);

      //CARGA ENTIDAD FANTASY
      Entidad entidadFantasy = new Entidad("Fantasy Co.", "fantasy@mail.com");
      entidadFantasy.setDescripcion("Una descripcion de la organizacion");
      entidadFantasy.setImg("fantasy.jpg");
      RepoEntidades.getInstance().add(entidadFantasy);

      Establecimiento establecimiento1 = new Establecimiento("Sala Este");
      entidadFantasy.agregarEstablecimiento(establecimiento1);
      RepoEstablecimientos.getInstance().add(establecimiento1);

      Establecimiento establecimiento2 = new Establecimiento("Sala Oeste");
      entidadFantasy.agregarEstablecimiento(establecimiento2);
      RepoEstablecimientos.getInstance().add(establecimiento2);

      Servicio servicio1 = new Servicio("Baño primer piso", TipoServicio.BAÑO);
      establecimiento1.agregarServicio(servicio1);
      RepoServicios.getInstance().add(servicio1);

      Servicio servicio2 = new Servicio("Baño segundo piso", TipoServicio.BAÑO);
      establecimiento1.agregarServicio(servicio2);
      RepoServicios.getInstance().add(servicio2);

      Servicio servicio5 = new Servicio("Ascensor discapacitados", TipoServicio.ASCENSOR);
      establecimiento1.agregarServicio(servicio5);
      RepoServicios.getInstance().add(servicio5);


      //CARGA ENTIDAD PIXEL
      Entidad entidadPixel = new Entidad("Pixel Innovators", "pixel@mail.com");
      entidadPixel.setDescripcion("Una descripcion de la organizacion");
      entidadPixel.setImg("pixel.jpg");
      RepoEntidades.getInstance().add(entidadPixel);

      Establecimiento establecimientoA = new Establecimiento("Establecimiento rojo");
      entidadPixel.agregarEstablecimiento(establecimientoA);
      RepoEstablecimientos.getInstance().add(establecimientoA);

      Establecimiento establecimientoB = new Establecimiento("Establecimiento azul");
      entidadPixel.agregarEstablecimiento(establecimientoB);
      RepoEstablecimientos.getInstance().add(establecimientoB);

      Servicio servicio3 = new Servicio("Baño tercer piso", TipoServicio.BAÑO);
      establecimientoA.agregarServicio(servicio3);
      RepoServicios.getInstance().add(servicio3);

      Servicio servicio4 = new Servicio("Ascensor", TipoServicio.ASCENSOR);
      establecimientoB.agregarServicio(servicio4);
      RepoServicios.getInstance().add(servicio4);

      //CARGA ENTIDAD MCDONALDS
      Entidad entidadMcDonald = new Entidad("McDonald", "mcdonald@mail.com");
      entidadMcDonald.setDescripcion("Una descripcion de la organizacion");
      entidadMcDonald.setImg("mc.jpg");
      RepoEntidades.getInstance().add(entidadMcDonald);

      Establecimiento establecimientoZ = new Establecimiento("Mc Donald Belgrano");
      entidadMcDonald.agregarEstablecimiento(establecimientoZ);
      RepoEstablecimientos.getInstance().add(establecimientoZ);

      Establecimiento establecimientoX = new Establecimiento("Mc Donald Urquiza");
      entidadMcDonald.agregarEstablecimiento(establecimientoX);
      RepoEstablecimientos.getInstance().add(establecimientoX);

      Servicio servicioZ = new Servicio("Baño primer piso", TipoServicio.BAÑO);
      establecimientoA.agregarServicio(servicioZ);
      RepoServicios.getInstance().add(servicioZ);

      Servicio servicioX = new Servicio("Ascensor", TipoServicio.ASCENSOR);
      establecimientoB.agregarServicio(servicioX);
      RepoServicios.getInstance().add(servicioX);

      //CARGA ENTIDAD BURGER KING
      Entidad entidadburger = new Entidad("Burguer King", "bking@mail.com");
      entidadburger.setDescripcion("Una descripcion de la organizacion");
      entidadburger.setImg("burger.jpg");
      RepoEntidades.getInstance().add(entidadburger);

      Establecimiento establecimientoW = new Establecimiento("Burger king Belgrano");
      entidadburger.agregarEstablecimiento(establecimientoW);
      RepoEstablecimientos.getInstance().add(establecimientoW);

      Establecimiento establecimientoM = new Establecimiento("Burger king Urquiza");
      entidadburger.agregarEstablecimiento(establecimientoM);
      RepoEstablecimientos.getInstance().add(establecimientoM);

      Servicio servicioW = new Servicio("Baño primer piso", TipoServicio.BAÑO);
      establecimientoW.agregarServicio(servicioW);
      RepoServicios.getInstance().add(servicioW);

      Servicio servicioM = new Servicio("Ascensor", TipoServicio.ASCENSOR);
      establecimientoM.agregarServicio(servicioM);
      RepoServicios.getInstance().add(servicioM);

      //CARGA COMUNIDADES
      Comunidad comunidad1 = new Comunidad("NOMBRE");
      comunidad1.aniadirServicioInteres(servicio1);
      /*comunidad1.incidentes.add(incidente);
      comunidad1.incidentes.add(incidente2);
      comunidad1.incidentes.add(incidente3);
      comunidad1.incidentes.add(incidente4);*/

      comunidad1.agregarUsuario(usuarioAdmin, PermisoComunidad.ADMIN_COMUNIDAD);
      comunidad1.agregarUsuario(usuarioComun1, PermisoComunidad.ADMIN_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad1);

      Comunidad comunidad2 = new Comunidad("Comunidad");
      comunidad2.agregarUsuario(usuarioComun1,PermisoComunidad.USUARIO_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad2);

      Comunidad comunidad3 = new Comunidad("La Comunidad");
      comunidad3.agregarUsuario(usuarioComun1,PermisoComunidad.ADMIN_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad3);

      Comunidad comunidad4 = new Comunidad("yaTuSabe");
      comunidad4.agregarUsuario(usuarioComun1,PermisoComunidad.USUARIO_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad4);

      Comunidad comunidad5 = new Comunidad("DaleDale");
      comunidad5.agregarUsuario(usuarioComun1,PermisoComunidad.USUARIO_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad5);

      Comunidad comunidad6 = new Comunidad("hola");
      comunidad6.agregarUsuario(usuarioComun1,PermisoComunidad.ADMIN_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad6);

      Comunidad comunidad7 = new Comunidad("Daledale");
      comunidad7.agregarUsuario(usuarioComun1,PermisoComunidad.USUARIO_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad7);

      Comunidad comunidad8 = new Comunidad("Mismo Nombre que Antes");
      comunidad8.agregarUsuario(usuarioComun1,PermisoComunidad.USUARIO_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad8);

      Comunidad comunidad9 = new Comunidad("Nombre9");
      comunidad9.agregarUsuario(usuarioComun1,PermisoComunidad.USUARIO_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad9);

      Comunidad comunidad10 = new Comunidad("Nombre10");
      comunidad10.agregarUsuario(usuarioComun1,PermisoComunidad.ADMIN_COMUNIDAD);
      RepositorioComunidades.getInstance().add(comunidad10);


      //CARGA CRITERIOS RANKINGS
      CriterioRanking criterioPromedioCierre = new PromedioCierresSemanal();
      CriterioRanking criterioCantidadReportes = new CantidadReportesSemanal();

      RepoRanking.getInstance().add(criterioPromedioCierre);
      RepoRanking.getInstance().add(criterioCantidadReportes);

      Incidente incidente = usuarioComun1.abrirIncidente(servicio1,"Botonera rota");
      RepoIncidentes.getInstance().add(incidente);

      Incidente incidente2 = usuarioComun1.abrirIncidente(servicio1,"Puerta rota");
      RepoIncidentes.getInstance().add(incidente2);

      Incidente incidente3 = usuarioComun1.abrirIncidente(servicio1,"Falta un espejo");
      RepoIncidentes.getInstance().add(incidente3);

      Incidente incidente4 = usuarioComun1.abrirIncidente(servicio1,"El piso se esta levantando");
      RepoIncidentes.getInstance().add(incidente4);


    });

    System.out.println("Working Directory = " + System.getProperty("user.dir"));
    new RankingProgramado().run();

  }

}
