package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.main.Bootstrap;
import ar.edu.utn.frba.dds.notificador.Horario;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;

import java.util.*;
import java.util.Set;
import java.time.LocalDateTime;

public class NotificadorProgramado implements SimplePersistenceTest{

  public static void main(String[] args) {
    new NotificadorProgramado().run();
  }

  public void run() {
    withTransaction(() -> {
      Set<Usuario> usuarios = RepoUsuarios.getInstance().getUsuariosComoSet();
      LocalDateTime ahora = LocalDateTime.now();
      int hora = ahora.getHour();
      int minuto = ahora.getMinute();
      usuarios.forEach(u -> u.verificarNotificaciones(new Horario(hora,minuto)));
    });

  }
}
