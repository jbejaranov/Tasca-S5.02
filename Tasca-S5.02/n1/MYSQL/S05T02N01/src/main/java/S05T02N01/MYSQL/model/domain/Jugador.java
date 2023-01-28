package S05T02N01.MYSQL.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Jugadors")
@Data
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Jugador")
    private Long idJugador;

    @Column(name = "NOM")
    private String nom;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA")
    private Date data = new Date();
    private double percentatge;

    @Column(name = "Partides_Fetes")
    private int partidesFetes;

    @Column(name = "Partides_Guanyades")
    private int partidesGuanyades;
    @OneToMany
    @Column(name = "Resultats")
    private List<Partida> partides = new ArrayList<>();

    public Jugador() {
    }

    public Jugador(Long idJugador, String nom) {
        this.idJugador = idJugador;
        this.nom = nom;
        percentatge = 0;
        partidesFetes = 0;
        partidesGuanyades = 0;
    }

    public int sumaPartidaFeta() {
        return partidesFetes++;
    }

    public int sumaPartidaGuanyada() {
        return partidesGuanyades++;
    }

    public double percentatge(Jugador jugador) {
        percentatge = jugador.getPartidesFetes() * jugador.getPartidesGuanyades() / 100;
        return percentatge;
    }

}