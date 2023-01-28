package S05T02N01.MONGODB.SEC.services;

import S05T02N01.MONGODB.SEC.model.Jugador;
import S05T02N01.MONGODB.SEC.model.Partida;
import S05T02N01.MONGODB.SEC.repository.GameRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final GameRepository gameRepository;

    public JwtUserDetailsService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        S05T02N01.MONGODB.SEC.model.Jugador jugador = gameRepository.findUserByUsername(username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new User(jugador.getUserName(), jugador.getPassword(), authorityList);

    }

    public UserDetails createUserDetails(String username, String password) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new User(username, password, authorityList);
    }
    ////////////////////////////////////////

    //3. Realitza una tirada dels daus.
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

        jugador.setPercentatge(jugador.percentatge()); //Càlcul del percentatge d'exit
        gameRepository.save(jugador);

        return partida;

    }

    //4. Elimina les tirades del jugador/a.
    public void eliminaPartides(String idJugador) {
        Jugador jugador = gameRepository.findById(idJugador).get();
        jugador.getPartides().removeAll(jugador.getPartides());
        jugador.setPercentatge(0.0);
        jugador.setPartidesFetes(0);
        jugador.setPartidesGuanyades(0);
        gameRepository.save(jugador);
    }


    public Map<String, Double> percentatgeJugadors() {
        List<Jugador> jugadors = gameRepository.findAll(); //Jugadors
        Map<String, Double> mapJugadors = new HashMap<>(); //Map amb mitjanes de jugadors

        for (int i = 0; i < jugadors.size(); i++) { // Introduim en un mapa per tenir idJugador -> mitjana
            mapJugadors.put("UserName: " + jugadors.get(i).getUserName() + " Nom: " + jugadors.get(i).getFirstName() + " Mitjana: ", jugadors.get(i).getPercentatge());
        }
        return mapJugadors;
    }

    //6. Retorna el llistat de jugades per un jugador/a.
    public String partidesPerJugador(String idJugador) {

        Jugador jugador = gameRepository.findById(idJugador).get();
        List<Partida> partides = jugador.getPartides();

        return "UserName: " + jugador.getUserName() + " Nom: " + jugador.getFirstName() + jugador.getPartides();

    }

    //7. Ranking mig de tots els jugadors/es del sistema.
    public Map<String, Double> rankingJugadors() {
        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        Map<String, Double> mapJugadors = new LinkedHashMap<>();
        String ranking;
        int j = 1;

        for (int i = 0; i < jugadors.size(); i++) { // Introduim en un mapa per tenir idJugador -> mitjana
            mapJugadors.put(j + " UserName: " + jugadors.get(i).getUserName() + " Nom: " + jugadors.get(i).getFirstName() + " Mitjana: ", jugadors.get(i).getPercentatge());
            j++;
        }

        return mapJugadors;
    }


    //8. Retorna el jugador/a amb pitjor percentatge d’èxit.
    //Lista jugador amb millor puntuació
    public Jugador rankingWinner() {
        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        jugadors.sort(Comparator.comparing(Jugador::getPercentatge).reversed());
        return jugadors.get(0);
    }

    //9. Retorna el jugador amb pitjor percentatge d’èxit.
    public Jugador rankingLoser() {
        List<Jugador> jugadors = gameRepository.findAll(); //llista de jugadors
        jugadors.sort(Comparator.comparing(Jugador::getPercentatge));
        return jugadors.get(0);
    }


    //Altres metodes
    //Elimina jugador
    public String eliminaJugador(String id) {
        gameRepository.deleteById(id);
        return "Jugador eliminat";
    }

    //Elimina jugadors
    public void eliminaJugadors() {
        gameRepository.deleteAll();
    }

    //Llista jugadors
    public List<Jugador> findAll() {

        return gameRepository.findAll();
    }

    //Jugador per id
    public Jugador jugadorPerId(String id) {
        return gameRepository.findById(id).get();
    }


    //Nou jugador
    public Jugador creaJugador(Jugador jugador) {
        if (jugador.getFirstName() == "") {
            jugador.setFirstName("Anònim");
        }
        gameRepository.save(jugador);
        return jugador;
    }
}