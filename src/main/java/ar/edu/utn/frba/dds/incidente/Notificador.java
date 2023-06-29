package ar.edu.utn.frba.dds.incidente;

public class Notificador {

  public void notificar(String mensaje, Comunidad comunidad){

    comunidad.getMiembros().forEach(m->m.recibirNotificaiones(mensaje));
  }
}
