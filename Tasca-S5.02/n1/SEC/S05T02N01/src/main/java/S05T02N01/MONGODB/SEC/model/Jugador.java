package S05T02N01.MONGODB.SEC.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "jugadors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {
    @Id
    private String userName;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String Email;
    private String password;
    private String role;
    private Date data = new Date();
    private double percentatge;
    private int partidesFetes;
    private int partidesGuanyades;
    private List<Partida> partides = new ArrayList<>();

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