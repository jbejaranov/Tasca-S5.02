package S05T02N01.MYSQL.model.services;

import S05T02N01.MYSQL.model.domain.Jugador;
import S05T02N01.MYSQL.model.domain.Partida;
import S05T02N01.MYSQL.model.repository.JugadorRepository;
import S05T02N01.MYSQL.model.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartidaService {

    @Autowired
    JugadorRepository jugadorRepository;
    @Autowired
    PartidaRepository partidaRepository;

    @Autowired
    JugadorService jugadorService;

    //3. Realitza una tirada dels daus.
    public Partida novaPartida(Jugador jugador) {

        Partida partida = new Partida(jugador);
        jugador.getPartides().add(partida); //Afegim partida a llista
        jugador.sumaPartidaFeta(); //Sumem partida feta

        if (partida.getDau1() + partida.getDau2() == 7) { //Sumem partida si la guanya
            jugador.sumaPartidaGuanyada();
        }

        jugador.percentatge(jugador);

        partidaRepository.save(partida); //Guardem partides al repository
        jugadorRepository.save(jugador);

        return partida;
    }

    //4. Elimina les tirades del jugador/a.
    public void eliminaPartides(Long idJugador) {
        Jugador jugador = jugadorRepository.findById(idJugador).get();
        jugador.getPartides().removeAll(jugador.getPartides());
        jugador.setPartidesFetes(0);
        jugador.setPartidesGuanyades(0);
        jugador.setPercentatge(0);
        Partida partida = new Partida();
        partida.setGuanyada("null");
        jugadorRepository.save(jugador);
        partidaRepository.deleteAll();
    }

    //5. Tots els jugadors/es del sistema amb el seu percentatge mitjà d’èxits.
    public Map<String, Double> percentatgeJugadors() {
        List<Jugador> jugadors = jugadorRepository.findAll(); //Jugadors
        Map<String, Double> mapJugadors = new HashMap<>(); //Map amb mitjanes de jugadors

        for (int i = 0; i < jugadors.size(); i++) { // Introduim en un mapa per tenir idJugador -> mitjana
            mapJugadors.put("IdJugador: " + jugadors.get(i).getIdJugador() + " Nom: " + jugadors.get(i).getNom() + " Mitjana: ", jugadors.get(i).getPercentatge());
        }
        return mapJugadors;
    }

    //6. Retorna el llistat de jugades per un jugador/a.
    public String partidesPerJugador(Long idJugador) {

        Jugador jugador = jugadorRepository.findById(idJugador).get();
        List<Partida> partides = jugador.getPartides();

        return "IdJugador: " + jugador.getIdJugador() + " Nom: " + jugador.getNom() + jugador.getPartides();

    }

    //7. Ranking mig de tots els jugadors/es del sistema.
    public Map<String, Double> rankingJugadors() {
        List<Jugador> jugadors = jugadorRepository.findAll(); //llista de jugadors
        Map<String, Double> mapJugadors = new LinkedHashMap<>();
        String ranking;
        int j = 1;

        for (int i = 0; i < jugadors.size(); i++) { // Introduim en un mapa per tenir idJugador -> mitjana
            mapJugadors.put(j + " IdJugador: " + jugadors.get(i).getIdJugador() + " Nom: " + jugadors.get(i).getNom() + " Mitjana: ", jugadors.get(i).getPercentatge());
            j++;
        }

        return mapJugadors;
    }

    //8. Retorna el jugador/a amb pitjor percentatge d’èxit.
    public Jugador rankingLoser() {
        List<Jugador> jugadors = jugadorRepository.findAll(); //llista de jugadors
        jugadors.sort(Comparator.comparing(Jugador::getPercentatge));
        return jugadors.get(0);
    }

    //9. Retorna el jugador amb pitjor percentatge d’èxit.
    public Jugador rankingWinner() {
        List<Jugador> jugadors = jugadorRepository.findAll(); //llista de jugadors
        jugadors.sort(Comparator.comparing(Jugador::getPercentatge).reversed());
        return jugadors.get(0);

    }

    //Altres metodes
    //Llistat de partides de tots els jugadors
    public List<Partida> partides() {

        List<Partida> partides = partidaRepository.findAll();

        return partides;
    }

}
