package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.usuario.Usuario;

public interface Notificador {

   void notificar(String mensaje, Usuario usuario);
}
