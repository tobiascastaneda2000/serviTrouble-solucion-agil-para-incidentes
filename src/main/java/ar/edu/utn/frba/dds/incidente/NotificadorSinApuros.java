package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class NotificadorSinApuros implements Notificador{public void notificar(String mensaje, Comunidad comunidad, TipoServicio servicio){

  comunidad.getMiembros().forEach(m->m.recibirNotificaiones(new Notificacion(mensaje, servicio)));

}
}
