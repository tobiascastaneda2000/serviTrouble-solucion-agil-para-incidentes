package ar.edu.utn.frba.dds.comunidad_y_usuarios;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Miembro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  public Usuario usuario;
  @Enumerated(EnumType.STRING)
  public PermisoComunidad permisoComunidad;

  //-------------------------CONSTRUCTOR----------------------------------------//
  protected Miembro() {

  }

  public Miembro(Usuario usuario, PermisoComunidad permisoComunidad) {
    this.usuario = usuario;
    this.permisoComunidad = permisoComunidad;
  }

  //-------------------------GETTERS----------------------------------------//

  public Usuario getUsuario() {
    return usuario;
  }

}
