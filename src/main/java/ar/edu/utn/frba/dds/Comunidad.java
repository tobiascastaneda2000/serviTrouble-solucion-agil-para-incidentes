package ar.edu.utn.frba.dds;

import java.util.HashSet;
import java.util.Set;

public class Comunidad {
    public Set<Miembro> miembros;

    public Comunidad() {
        this.miembros = new HashSet<>();
    }

    public void registrarMiembro(Miembro miembro) {
        this.miembros.add(miembro);
    }
}
