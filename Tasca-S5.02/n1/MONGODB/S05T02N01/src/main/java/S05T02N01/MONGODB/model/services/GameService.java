package cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.services;

import cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.domain.Jugador;
import cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.domain.Partida;
import cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    public List<Jugador> findAll() {
        return gameRepository.findAll();
    }

    public Jugador creaJugador(Jugador jugador) {
        if (jugador.getNom() == "") {
            jugador.setNom("Anònim");
        }
        gameRepository.save(jugador);
        return jugador;
    }

    public Jugador jugadorPerId(String id) {
        return gameRepository.findById(id).get();
    }


    public Partida novaPartida(String idJugador) {

        Jugador jugador = jugadorPerId(idJugador);
        Partida partida = new Partida();

        if (jugador.getPartides() != null) {
            jugador.getPartides().add(partida);
        }

        jugador.setPartidesFetes(jugador.getPartidesFetes() + 1); //Partides fetes

        if (partida.getDau1() + partida.getDau2() == 7) {
            jugador.setPartidesGuanyades(jugador.getPartidesGuanyades() + 1);
        }

        jugador.setMitjana(jugador.percentatge()); //Càlcul del percentatge d'exit
        gameRepository.save(jugador);

        return partida;

    }

    //4. Elimina les tirades del jugador/a.
    public void eliminaPartides(String idJugador) {
        Jugador jugador = gameRepository.findById(idJugador).get();
        jugador.getPartides().removeAll(jugador.getPartides());
        jugador.setMitjana(0.0);
        jugador.setPartidesFetes(0);
        jugador.setPartidesGuanyades(0);
        gameRepository.save(jugador);
    }


    public Map<String, Double> mitjanaJugadors() {
        List<Jugador> jugadors = gameRepository.findAll(); //Jugadors
        Map<String, Double> mapJugadors = new HashMap<>(); //Map amb mitjanes de jugadors

        for (int i = 0; i < jugadors.size(); i++) { // Introduim en un mapa per tenir idJugador -> mitjana
            mapJugadors.put("IdJugador: " + jugadors.get(i).getIdJugador() + " Nom: " + jugadors.get(i).getNom() + " Mitjana: ", jugadors.get(i).getMitjana());
        }
        return mapJugadors;
    }

    //7. Ranking mig de tots els jugadors/es del sistema.
    public Map<String, Double> rankingJugadors() {
        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        Map<String, Double> mapJugadors = new LinkedHashMap<>();
        String ranking;
        int j = 1;

        for (int i = 0; i < jugadors.size(); i++) { // Introduim en un mapa per tenir idJugador -> mitjana
            mapJugadors.put(j + " IdJugador: " + jugadors.get(i).getIdJugador() + " Nom: " + jugadors.get(i).getNom() + " Mitjana: ", jugadors.get(i).getMitjana());
            j++;
        }

        return mapJugadors;
    }

    //Lista jugador amb pitjor puntuació
    public Jugador rankingLoser() {
        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        jugadors.sort(Comparator.comparing(Jugador::getMitjana));
        return jugadors.get(0);
    }

    //Lista jugador amb millor puntuació
    public Jugador rankingWinner() {
        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        jugadors.sort(Comparator.comparing(Jugador::getMitjana).reversed());
        return jugadors.get(0);
/*        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        Jugador jugador = new Jugador();

        for (int i = 0; i < jugadors.size(); i++) {
            if (jugadors.get(i).getMitjana() > 0) {
                jugador = jugadors.get(i);
            }
        }
        return jugador;*/
    }

    //6. Retorna el llistat de jugades per un jugador/a.
    public String partidesPerJugador(String idJugador) {

        Jugador jugador = gameRepository.findById(idJugador).get();
        List<Partida> partides = jugador.getPartides();

        return "IdJugador: " + jugador.getIdJugador() + " Nom: " + jugador.getNom() + jugador.getPartides();

    }

    //Elimina jugador
    public String eliminaJugador(String id) {
        gameRepository.deleteById(id);
        return "Jugador eliminat";
    }

    //Elimina jugadors
    public void eliminaJugadors() {
        gameRepository.deleteAll();
    }
}

