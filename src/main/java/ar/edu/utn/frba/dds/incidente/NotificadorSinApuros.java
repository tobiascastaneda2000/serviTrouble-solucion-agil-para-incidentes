package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

import java.util.List;

public class NotificadorSinApuros implements Notificador{public void notificar(String mensaje, Comunidad comunidad, TipoServicio servicio){

  List<String> horarios;

  comunidad.getMiembros().forEach(m->m.recibirNotificaiones(new Notificacion(mensaje, servicio)));
}
}
