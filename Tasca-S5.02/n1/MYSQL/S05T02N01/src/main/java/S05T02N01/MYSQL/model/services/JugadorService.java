package S05T02N01.MYSQL.model.services;

import S05T02N01.MYSQL.model.domain.Jugador;
import S05T02N01.MYSQL.model.domain.Partida;
import S05T02N01.MYSQL.model.repository.JugadorRepository;
import S05T02N01.MYSQL.model.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private PartidaRepository partidaRepository;


    //1. Crea un jugador/a.
    //2. Modifica el nom del jugador/a.
    public Jugador creaJugador(Jugador jugador) {
        if (jugador.getNom().isEmpty() || jugador.getNom().equalsIgnoreCase("")) {
            jugador.setNom("Anònim");
        }
        jugadorRepository.save(jugador);
        return jugador;
    }

    //6. Retorna el llistat de jugades per un jugador/a.
    public List<Partida> partidesPerJugador(Long idJugador) {

        Jugador jugador = jugadorRepository.findById(idJugador).get();
        List<Partida> partides = partidaRepository.findAll();

        return partides;

    }

    //Jugadors
    public List<Jugador> llistaJugadors() {
        return jugadorRepository.findAll();
    }

    //Jugador per id
    public Jugador jugadorPerId(Long id) {
        return jugadorRepository.findById(id).get();
    }

    //Elimina jugador
    public void eliminaJugador(Long id) {
        jugadorRepository.deleteById(id);
    }

    //Elimina jugadors
    public void eliminaJugadors() {
        partidaRepository.deleteAll();
        jugadorRepository.deleteAll();
    }

}