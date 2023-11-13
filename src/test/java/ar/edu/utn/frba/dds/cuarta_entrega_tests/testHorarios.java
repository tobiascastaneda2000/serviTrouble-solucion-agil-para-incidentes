package ar.edu.utn.frba.dds.cuarta_entrega_tests;

import ar.edu.utn.frba.dds.notificador.Horario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
