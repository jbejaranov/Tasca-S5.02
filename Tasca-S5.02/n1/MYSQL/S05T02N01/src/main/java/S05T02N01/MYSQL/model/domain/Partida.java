package S05T02N01.MYSQL.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "partides")
@Data
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartida;

    @Column(name = "Dau 1")
    private int dau1;

    @Column(name = "Dau 2")
    private int dau2;

    @Column(name = "Partida Guanyada")
    private String guanyada;

    @JsonBackReference
    @JoinColumn(name = "idJugador", nullable = false)
    @ManyToOne
    private Jugador jugador;

    public Partida() {
        super();

    }

    public Partida(Jugador jugador) {
        dau1 = tiraDau();
        dau2 = tiraDau();
        guanyada = guanyada(dau1, dau2);
        this.jugador = jugador;
    }

    public String guanyada(int dau1, int dau2) {
        String guanyada = "No";

        if (dau1 + dau2 == 7) {
            guanyada = "Si";
        }

        return guanyada;
    }

    public int tiraDau() {

        int dau = (int) (Math.random() * 6 + 1);
        return dau;
    }
}