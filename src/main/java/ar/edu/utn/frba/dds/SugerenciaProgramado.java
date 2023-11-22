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

public class SugerenciaProgramado {

  public static void main(String[] args) {
    new SugerenciaProgramado().run();
  }

  public void run() {
    List<Comunidad> comunidades = RepositorioComunidades.getInstance().getAll();
    comunidades.forEach(c -> c.sugerirIncidentes());
  }
}
