package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.RankingProgramado;
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
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import ar.edu.utn.frba.dds.repositorios.*;
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
    Usuario usuarioComun1 = new Usuario("facu", "123456", "contacto");
    Horario unHorario = new Horario(10, 30);
    usuarioComun1.agregarHorario(unHorario);
    RepoUsuarios.getInstance().add(usuarioComun1);

    Usuario usuarioAdmin = new Usuario("admin", "123456", "contacto");
    usuarioAdmin.permisoUsuario = PermisoUsuario.ADMIN;
    RepoUsuarios.getInstance().add(usuarioAdmin);

    Usuario usuarioComun2 = new Usuario("pepe", "123456", "contacto");
    RepoUsuarios.getInstance().add(usuarioComun2);

    //CARGA ENTIDAD FANTASY
    Entidad entidadFantasy = new Entidad("Fantasy Co.", "fantasy@mail.com");
    entidadFantasy.setDescripcion("una descripcion de la organizacion");
    entidadFantasy.setImg("fantasy.jpg");
    RepoEntidades.getInstance().add(entidadFantasy);

    Establecimiento establecimiento1 = new Establecimiento("nombre 1");
    entidadFantasy.agregarEstablecimiento(establecimiento1);
    RepoEstablecimientos.getInstance().add(establecimiento1);

    Establecimiento establecimiento2 = new Establecimiento("nombre 2");
    entidadFantasy.agregarEstablecimiento(establecimiento2);
    RepoEstablecimientos.getInstance().add(establecimiento2);

    Servicio servicio1 = new Servicio("baño primer piso", TipoServicio.BAÑO);
    establecimiento1.agregarServicio(servicio1);
    RepoServicios.getInstance().add(servicio1);

    Servicio servicio2 = new Servicio("baño segundo piso", TipoServicio.BAÑO);
    establecimiento1.agregarServicio(servicio2);
    RepoServicios.getInstance().add(servicio2);

    Servicio servicio5 = new Servicio("Ascensor discapacitados", TipoServicio.ASCENSOR);
    establecimiento1.agregarServicio(servicio5);
    RepoServicios.getInstance().add(servicio5);

    Incidente incidente = new Incidente("Botonera rota", servicio5);
    RepoIncidentes.getInstance().add(incidente);

    Incidente incidente2 = new Incidente("Puerta rota", servicio5);
    RepoIncidentes.getInstance().add(incidente2);

    Incidente incidente3 = new Incidente("Falta un espejo", servicio5);
    RepoIncidentes.getInstance().add(incidente3);

    Incidente incidente4 = new Incidente("El piso se esta levantando", servicio5);
    RepoIncidentes.getInstance().add(incidente4);

    //CARGA ENTIDAD PIXEL
    Entidad entidadPixel = new Entidad("Pixel Innovators", "pixel@mail.com");
    entidadPixel.setDescripcion("una descripcion de la organizacion");
    entidadPixel.setImg("pixel.jpg");
    RepoEntidades.getInstance().add(entidadPixel);

    Establecimiento establecimientoA = new Establecimiento("establecimiento A");
    entidadPixel.agregarEstablecimiento(establecimientoA);
    RepoEstablecimientos.getInstance().add(establecimientoA);

    Establecimiento establecimientoB = new Establecimiento("establecimiento B");
    entidadPixel.agregarEstablecimiento(establecimientoB);
    RepoEstablecimientos.getInstance().add(establecimientoB);

    Servicio servicio3 = new Servicio("baño tercer piso", TipoServicio.BAÑO);
    establecimientoA.agregarServicio(servicio3);
    RepoServicios.getInstance().add(servicio3);

    Servicio servicio4 = new Servicio("ascensor", TipoServicio.ASCENSOR);
    establecimientoB.agregarServicio(servicio4);
    RepoServicios.getInstance().add(servicio4);

    //CARGA ENTIDAD MCDONALDS
    Entidad entidadMcDonald = new Entidad("McDonald", "mcdonald@mail.com");
    entidadMcDonald.setDescripcion("una descripcion de la organizacion");
    entidadMcDonald.setImg("mc.jpg");
    RepoEntidades.getInstance().add(entidadMcDonald);

    Establecimiento establecimientoZ = new Establecimiento("Mc Donald Belgrano");
    entidadMcDonald.agregarEstablecimiento(establecimientoZ);
    RepoEstablecimientos.getInstance().add(establecimientoZ);

    Establecimiento establecimientoX = new Establecimiento("Mc Donald Urquiza");
    entidadMcDonald.agregarEstablecimiento(establecimientoX);
    RepoEstablecimientos.getInstance().add(establecimientoX);

    Servicio servicioZ = new Servicio("baño primer piso", TipoServicio.BAÑO);
    establecimientoA.agregarServicio(servicioZ);
    RepoServicios.getInstance().add(servicioZ);

    Servicio servicioX = new Servicio("ascensor", TipoServicio.ASCENSOR);
    establecimientoB.agregarServicio(servicioX);
    RepoServicios.getInstance().add(servicioX);

    //CARGA ENTIDAD BURGER KING
    Entidad entidadburger = new Entidad("Burguer King", "bking@mail.com");
    entidadburger.setDescripcion("una descripcion de la organizacion");
    entidadburger.setImg("burger.jpg");
    RepoEntidades.getInstance().add(entidadburger);

    Establecimiento establecimientoW = new Establecimiento("Burger king Belgrano");
    entidadburger.agregarEstablecimiento(establecimientoW);
    RepoEstablecimientos.getInstance().add(establecimientoW);

    Establecimiento establecimientoM = new Establecimiento("Burger king Urquiza");
    entidadburger.agregarEstablecimiento(establecimientoM);
    RepoEstablecimientos.getInstance().add(establecimientoM);

    Servicio servicioW = new Servicio("baño primer piso", TipoServicio.BAÑO);
    establecimientoW.agregarServicio(servicioW);
    RepoServicios.getInstance().add(servicioW);

    Servicio servicioM = new Servicio("ascensor", TipoServicio.ASCENSOR);
    establecimientoM.agregarServicio(servicioM);
    RepoServicios.getInstance().add(servicioM);

    //CARGA COMUNIDADES
    Comunidad comunidad1 = new Comunidad("nombre1");
    comunidad1.aniadirServicioInteres(servicio1);
    comunidad1.incidentes.add(incidente);
    comunidad1.incidentes.add(incidente2);
    comunidad1.incidentes.add(incidente3);
    comunidad1.incidentes.add(incidente4);

    comunidad1.agregarUsuario(usuarioAdmin, PermisoComunidad.ADMIN_COMUNIDAD);
    comunidad1.agregarUsuario(usuarioComun2, PermisoComunidad.USUARIO_COMUNIDAD);
    RepositorioComunidades.getInstance().add(comunidad1);

    Comunidad comunidad2 = new Comunidad("nombre2");
    RepositorioComunidades.getInstance().add(comunidad2);

    //CARGA CRITERIOS RANKINGS
    CriterioRanking criterioPromedioCierre = new PromedioCierresSemanal();
    CriterioRanking criterioCantidadReportes = new CantidadReportesSemanal();

    RepoRanking.getInstance().add(criterioPromedioCierre);
    RepoRanking.getInstance().add(criterioCantidadReportes);

    new RankingProgramado().run();

  }

}
