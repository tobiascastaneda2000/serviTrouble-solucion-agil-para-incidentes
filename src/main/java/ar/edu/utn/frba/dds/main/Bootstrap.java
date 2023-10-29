package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 * @author flbulgarelli
 */
public class Bootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {
      Usuario usuario = new Usuario("facu", "123456","contacto");
      persist(usuario);
    });
  }

}
