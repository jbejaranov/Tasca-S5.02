package S05T02N01.MYSQL.controllers;

import S05T02N01.MYSQL.model.domain.Jugador;
import S05T02N01.MYSQL.model.repository.JugadorRepository;
import S05T02N01.MYSQL.model.services.JugadorService;
import S05T02N01.MYSQL.model.services.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    PartidaService partidaService;
    @Autowired
    JugadorService jugadorService;
    @Autowired
    private JugadorRepository jugadorRepository;


    //3. Realitza una tirada dels daus.
    @PostMapping("/players/{id}/games")
    public ResponseEntity<?> novaPartida(@PathVariable(value = "id") Long idJugador) throws Exception {
        Jugador jugador = jugadorService.jugadorPerId(idJugador);
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.novaPartida(jugador));
    }

    //4. Elimina les tirades del jugador/a.
    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<Jugador> eliminaPartida(@PathVariable Long id) {
        try {
            partidaService.eliminaPartides(id);
            return ResponseEntity.status(HttpStatus.OK).body(jugadorService.jugadorPerId(id));
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }

    //5. Llistat de jugadors amb el seu percentatge d’èxits.
    @GetMapping("/players")
    public ResponseEntity<Map<String, Double>> percentatgeJugadors() {

        try {
            return new ResponseEntity<>(partidaService.percentatgeJugadors(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //6. Retorna el llistat de jugades per un jugador/a.
    @GetMapping("/players/{id}/games")
    public ResponseEntity<String> partides(@PathVariable Long idJugador) {
        try {
            return new ResponseEntity<>(partidaService.partidesPerJugador(idJugador), HttpStatus.OK);
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

    //8. Retorna el jugador/a amb pitjor percentatge d’èxit.
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<Jugador> rankingLoser() {
        try {
            return new ResponseEntity<>(partidaService.rankingLoser(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    //9. Retorna el jugador amb pitjor percentatge d’èxit.
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<Jugador> rankingWinner() {
        try {
            return new ResponseEntity<>(partidaService.rankingWinner(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}