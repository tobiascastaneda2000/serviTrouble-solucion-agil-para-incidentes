package ar.edu.utn.frba.dds;

public interface ServicioUbicacion {

  // Interfaz que conecta API externa con nuestro sistema

  public Ubicacion ubicacionUsuario(Usuario usuario);
  public Ubicacion ubicacionEstablecimiento(Establecimiento establecimiento);
  public boolean estaCerca(Usuario usuario,Servicio servicio);
}
