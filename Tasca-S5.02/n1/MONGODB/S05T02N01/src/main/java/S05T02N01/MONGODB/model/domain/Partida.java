package cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Partida {

    private int dau1;
    private int dau2;
    private String guanyada;

    public Partida() {
        dau1 = tiraDau();
        dau2 = tiraDau();
        guanyada = guanyada();
    }

    public int tiraDau() {
        int dau = (int) (Math.random() * 6 + 1);
        return dau;
    }

    public String guanyada() {
        int sumaPunts = dau1 + dau2;
        if (sumaPunts == 7) {
            guanyada = "Si";
        } else {
            guanyada = "No";
        }
        return guanyada;
    }
}
