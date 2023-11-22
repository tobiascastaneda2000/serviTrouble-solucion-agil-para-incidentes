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

public class NotificadorProgramado {

  public static void main(String[] args) {
    new NotificadorProgramado().run();
  }

  public void run() {
    Set<Usuario> usuarios = RepoUsuarios.getInstance().getUsuariosComoSet();
    LocalDateTime ahora = LocalDateTime.now();
    usuarios.forEach(u -> u.verificarNotificaciones(ahora));
  }
}