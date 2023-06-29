package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

public interface Notificador {

   void notificar(String mensaje, Comunidad comunidad, TipoServicio servicio);
}
