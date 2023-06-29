package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

public class NotificadorCuandoSuceden implements Notificador{
  public void notificar(String mensaje, Comunidad comunidad, TipoServicio servicio){

    comunidad.getMiembros().forEach(m->m.recibirNotificaiones(new Notificacion(mensaje, servicio)));
  }
}
