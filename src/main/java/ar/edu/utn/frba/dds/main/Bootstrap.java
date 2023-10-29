package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
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
      Entidad entidad1 = new Entidad("Fantasy Co.", "fantasy@mail.com");
      Entidad entidad2 = new Entidad("Pixel Innovators", "pixel@mail.com");
      persist(entidad2);
      Establecimiento establecimiento1 = new Establecimiento("nombre 1");
      persist(establecimiento1);
      entidad1.agregarEstablecimiento(establecimiento1);
      Establecimiento establecimiento2 = new Establecimiento("nombre 2");
      persist(establecimiento2);
      entidad1.agregarEstablecimiento(establecimiento2);
      persist(entidad1);

      Servicio servicio1 = new Servicio("baño primer piso",TipoServicio.BAÑO);
      persist(servicio1);
      establecimiento1.agregarServicio(servicio1);

      Servicio servicio2 = new Servicio("baño segundo piso",TipoServicio.BAÑO);
      establecimiento1.agregarServicio(servicio2);
      persist(servicio2);
      persist(establecimiento1);


    });
  }

}
