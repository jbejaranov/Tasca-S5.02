package S05T02N01.MONGODB.model.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection = "jugadors")
@Data
public class Jugador {
    @Id
    private String idJugador;
    private String nom;
    private Date data= new Date();;
    private double mitjana;
    private int partidesFetes;
    private int partidesGuanyades;
    private List<Partida> partides = new ArrayList<>();
    private String usuari="Admin";

    public Jugador() {
    }

    public Jugador(String nom) {
        this.nom = nom;
        mitjana = 0.0;
        partidesFetes = 0;
        partidesGuanyades = 0;
        partides = new ArrayList<>();
    }

    public double percentatge() {
        double result = 0;
        if (this.partides != null) {
            result = (partidesGuanyades / (double) partides.size()) * 100;
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                result = 0.0d;
            }
        }
        return result;
    }

}