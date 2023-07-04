package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
  private int id;
  private String razonSocial;
  private String email;

  public List<Establecimiento> establecimientos = new ArrayList<>();

  public Entidad(int id, String razonSocial, String email) {
    this.id = id;
    this.razonSocial = razonSocial;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public String getEmail() {
    return email;
  }

}
