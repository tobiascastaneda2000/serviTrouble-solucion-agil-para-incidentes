package ar.edu.utn.frba.dds.tercera_entrega_tests;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.RepoUsuarios;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.notificador.MailSender;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class MailSenderTest {

  public static void main(String[] args) {
    RepoEntidades repoEntidades = new RepoEntidades();
    Entidad gualmayen;

    Establecimiento establecimientoGualmayen;
    Servicio unAscensor;

    //CREACION ENTIDAD GUALMAYEN

    unAscensor = new Servicio(TipoServicio.ASCENSOR);

    establecimientoGualmayen = new Establecimiento();

    gualmayen = new Entidad(12, "Gualmayen", "alfajores.com");

    gualmayen.agregarEstablecimiento(establecimientoGualmayen);

    establecimientoGualmayen.agregarServicio(unAscensor);

    repoEntidades.guardarEntidad(gualmayen);

    Usuario usuario = new Usuario(1, "Juan ", "Perez", "unMail");
    RepoUsuarios repoUsuarios = new RepoUsuarios();
    repoUsuarios.guardarUsuario(usuario);
    // Configura el incidente y el contacto (dirección de correo electrónico)
    Incidente incidente = new Incidente("Aguante River plate", unAscensor);
    String contacto = "tomich2088@gmail.com"; // Cambia a la dirección de correo electrónico de destino

    // Crea una instancia de MailSender
    MedioNotificador mailSender = new MailSender();

    // Envía el correo electrónico
    mailSender.notificarNuevoIncidente(incidente, contacto);

  }

  @BeforeEach
  void setUp() {
  }
}