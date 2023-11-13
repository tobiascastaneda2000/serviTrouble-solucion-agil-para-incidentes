package ar.edu.utn.frba.dds.serviciolocalizacion;

public class Ubicacion {

  private double latitud;
  private double longitud;

  public Ubicacion(double latitud,double longitud){
    this.latitud=latitud;
    this.longitud=longitud;
  }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  public boolean mismaUbicacion(Ubicacion ubicacion){
    return (this.latitud == ubicacion.getLatitud()) && (this.longitud == ubicacion.getLongitud());
  }

}
