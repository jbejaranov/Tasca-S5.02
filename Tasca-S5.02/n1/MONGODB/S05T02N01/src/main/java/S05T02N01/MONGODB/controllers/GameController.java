package S05T02N01.MONGODB.controllers;

import S05T02N01.MONGODB.model.domain.Jugador;
import S05T02N01.MONGODB.model.domain.Partida;
import S05T02N01.MONGODB.model.repository.GameRepository;
import S05T02N01.MONGODB.model.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    GameService gameService;

    //1. crea un jugador/a.
    @PostMapping("/players")
    public ResponseEntity<?> creaJugador(@RequestBody Jugador jugador) {
        try {
            gameService.creaJugador(jugador);
            return new ResponseEntity<Jugador>(jugador, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //2. modifica el nom del jugador/a.
    @PutMapping("/player/{id}")
    public ResponseEntity<Jugador> jugadorUpdate(@RequestBody Jugador jugador, @PathVariable String id) {
        try {
            Jugador jugadorExistent = gameService.jugadorPerId(id);
            jugadorExistent.setNom(jugador.getNom());
            gameService.creaJugador(jugadorExistent);
            return new ResponseEntity<Jugador>(jugadorExistent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }

    //3. Realitza una tirada dels daus.
    @PostMapping("/players/{id}/games/")
    public ResponseEntity<Partida> novaPartida(@PathVariable String id) {
        try {
            return new ResponseEntity<>(gameService.novaPartida(id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //4. Elimina les tirades del jugador/a.
    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<Jugador> eliminarPartidas(@PathVariable String id) {
        try {
            gameService.eliminaPartides(id);
            return ResponseEntity.status(HttpStatus.OK).body(gameService.jugadorPerId(id));
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }


    //5. Tots els jugadors/es del sistema amb el seu percentatge mitjà d’èxits.
    @GetMapping("/players/")
    public ResponseEntity<Map<String, Double>> mitjanaJugadors() {
        try {
            return new ResponseEntity<>(gameService.mitjanaJugadors(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    //6. Retorna el llistat de jugades per un jugador/a.
    @GetMapping("/players/{id}/games")
    public ResponseEntity<String> partides(@PathVariable String id) {
        try {
            return new ResponseEntity<>(gameService.partidesPerJugador(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //7. Ranking mig de tots els jugadors/es del sistema.
    @GetMapping("/players/ranking")
    public ResponseEntity<Map<String, Double>> rankingJugadors() {
        try {
            return new ResponseEntity<>(gameService.rankingJugadors(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //8. Retorna el jugador/a amb pitjor percentatge d’èxit.
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<Jugador> rankingLoser() {
        try {
            return new ResponseEntity<>(gameService.rankingLoser(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    //9. Retorna el jugador amb pitjor percentatge d’èxit.
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<Jugador> rankingWinner() {
        try {
            return new ResponseEntity<>(gameService.rankingWinner(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
    }
    //Altres metodes

    //Llista jugadors
    @GetMapping("/jugadors")
    public ResponseEntity<List<Jugador>> jugadors() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gameService.findAll());
        } catch (Exception e) {
            return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_FOUND);
        }
    }

    //Llista jugador
    @GetMapping("/jugador/{id}")
    public ResponseEntity<Jugador> jugadorPerId(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gameService.jugadorPerId(id));
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }

    //Elimina jugador
    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Jugador> eliminarJugador(@PathVariable String id) {
        try {
            gameService.eliminaJugador(id);
            return new ResponseEntity<Jugador>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }

    //Elimina jugadors
    @DeleteMapping("/elimina/jugadors")
    public ResponseEntity<Jugador> eliminarJugadors() {
        try {
            gameService.eliminaJugadors();
            return new ResponseEntity<Jugador>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
        }
    }
}