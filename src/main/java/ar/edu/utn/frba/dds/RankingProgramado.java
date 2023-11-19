package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import java.util.*;
import java.util.Set;
import java.time.LocalDateTime;

public class RankingProgramado {

  public static void main(String[] args) {
    new RankingProgramado().run();
  }

  public void run(){
    CriterioRanking criterioPromediosCierres = new PromedioCierresSemanal();
    CriterioRanking criterioCantidadReportes = new CantidadReportesSemanal();

    criterioPromediosCierres.setEntidadesOrdenadas(
        RepoEntidades.getInstance().ordenarEntidadesSegunCriterio(criterioPromediosCierres)
    );
    criterioCantidadReportes.setEntidadesOrdenadas(
        RepoEntidades.getInstance().ordenarEntidadesSegunCriterio(criterioCantidadReportes)
    );

    RepoEntidades.getInstance().generarRankingCSVSegunCriterio(criterioCantidadReportes);
    RepoEntidades.getInstance().generarRankingCSVSegunCriterio(criterioPromediosCierres);

    //Se realizan una vez por semana
    /*Cada criterio guarda los rankings en su respectivo path*/
  }
}
