package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import ar.edu.utn.frba.dds.RepoUsuarios;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import java.util.Locale;
import java.util.Set;
import java.time.LocalDateTime;

import java.util.List;

public class Main {
  public static void main(String[] args) {

    /*
    *
    * En args[0] indicaremos el tipo de tarea programada, ya que pueden existir varias
    * y solo se puede tener un "Main" por proyecto, de esta manera podemos diferenciarlas.
    *
    */

    String tipoTareaPlanificada = args[0];
    switch(tipoTareaPlanificada){
      case "notificacion":
                        notificarUsuarios();
                        break;
      case "ranking":
                      lanzarRanking();
                      break;
      default:;
    }
  }

  //-----------------------------------NOTIFICADOR A USUARIOS SEGUN HORARIO CONFIGURADO-------------------//

  public static void notificarUsuarios(){
    Set<Usuario> usuarios = RepoUsuarios.instance.getUsuarios();
    LocalDateTime ahora = LocalDateTime.now();
    usuarios.forEach(u->u.verificarNotificaciones(ahora));
  }


  //------------------------------------SUGERENCIA REVISION INCIDENTES------------------------//

  public static void sugerirIncidentes(){
    Set<Comunidad> comunidades = RepositorioComunidades.instance.getComunidades();
    comunidades.forEach(c->c.sugerirIncidentes());
  }




  //------------------------------------------------------------------------------------------//




  /*public static void lanzarNotificacion(int idUsuario) {
    //Busco a mi usuario con su id en mi reposotorio de usuarios
    Usuario usuario =
        RepoUsuarios.getInstance().getUsuarios().stream().filter( user -> user.getId() == idUsuario ).findFirst().orElse(null);

    //Obtengo las comunidades a las que pertenece
    List<Comunidad> comunidadesDelUsuario =
        RepositorioComunidades.getInstance().getComunidades().stream().filter(comunidad -> comunidad.contieneUsuario(usuario)).toList();

    List<Incidente> incidentes = comunidadesDelUsuario.stream().flatMap( com -> com.getIncidentes().stream() ).toList();

   // usuario.notificarIncidente(incidentes);
  }*/

  public static void lanzarRanking(){

    CriterioRanking criterioPromediosCierres = new PromedioCierresSemanal();
    CriterioRanking criterioCantidadReportes = new CantidadReportesSemanal();

    RepoEntidades.instance.generarRankingEnCsv(criterioCantidadReportes);
    RepoEntidades.instance.generarRankingEnCsv(criterioPromediosCierres);
    //Se realizan una vez por semana

    /*
     * List<Entidad> entidades = RepoEntidades.instance.getEntidades();
     * Entidad gualmayen = new Entidad(12, "Gualmayen ", "mail");
     * Entidad jorgito = new Entidad(11, "Jorgito", "mail");
     * Entidad quatar_aerolines = new Entidad(10, "Chorros ", "mail");
     * RepoEntidades.instance.guardarEntidad(gualmayen);
     * Para ranking Utilizaremos https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-java.util.Comparator-
     */
  }



}
