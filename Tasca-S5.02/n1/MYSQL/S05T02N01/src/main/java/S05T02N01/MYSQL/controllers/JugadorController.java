package S05T02N01.MYSQL.controllers;

import S05T02N01.MYSQL.model.domain.Jugador;
import S05T02N01.MYSQL.model.services.JugadorService;
import S05T02N01.MYSQL.model.services.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    PartidaService partidaService;
    @Autowired
    JugadorService jugadorService;

    //1. Crea un jugador/a.
    @PostMapping("/players")
    public ResponseEntity<?> creaJugador(@RequestBody Jugador jugador) {
        try {
            jugadorService.creaJugador(jugador);
            return new ResponseEntity<Jugador>(jugador, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //2. Modifica el nom del jugador/a.
    @PutMapping("/player/{id}")
    public ResponseEntity<Jugador> jugadorUpdate(@RequestBody Jugador jugador, @PathVariable Long id) {
        try {
            Jugador jugadorExistent = jugadorService.jugadorPerId(id);
            jugadorExistent.setNom(jugador.getNom());
            jugadorService.creaJugador(jugadorExistent);
            return new ResponseEntity<Jugador>(jugadorExistent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }

    //6. Retorna el llistat de jugades per un jugador/a.
    @GetMapping("/players/{id}/games")
    public ResponseEntity<?> partides(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(jugadorService.partidesPerJugador(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    //7. Ranking mig de tots els jugadors/es del sistema.
    @GetMapping("/players/ranking")
    public ResponseEntity<Map<String, Double>> rankingJugadors() {
        try {
            return new ResponseEntity<>(partidaService.rankingJugadors(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //Altres metodes
    //Mostra jugadors
    @GetMapping("/jugadors")
    public List<Jugador> llistaJugadors() {
        return jugadorService.llistaJugadors();
    }

    //Mostra 1 jugador
    @GetMapping("/jugador/{id}")
    public ResponseEntity<Jugador> obtenirJugador(@PathVariable Long id) {
        try {
            Jugador jugador = jugadorService.jugadorPerId(id);
            return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }

    //Elimina jugador
    @DeleteMapping("/elimina/{id}")
    public void eliminaJugador(@PathVariable Long id) {
        jugadorService.eliminaJugador(id);
    }

    //Elimina jugadors
    @DeleteMapping("/elimina/jugadors")
    public void eliminaJugador() {
        jugadorService.eliminaJugadors();
    }
}