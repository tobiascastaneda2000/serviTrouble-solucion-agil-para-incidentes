package ar.edu.utn.frba.dds.cuarta_entrega_tests;

import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.notificador.Horario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


public class testHorarios {


  @Test
  void dosHorariosIguales() {

    Horario unHorario = new Horario(10,20);
    Horario otroHorario = new Horario(10,30);
    Horario tercerHorario = new Horario(10,20);

    Assertions.assertEquals(unHorario,tercerHorario);
    Assertions.assertNotEquals(unHorario,otroHorario);
    Assertions.assertEquals(unHorario.hashCode(),tercerHorario.hashCode());
    Assertions.assertNotEquals(unHorario.hashCode(),otroHorario.hashCode());
  }


}
