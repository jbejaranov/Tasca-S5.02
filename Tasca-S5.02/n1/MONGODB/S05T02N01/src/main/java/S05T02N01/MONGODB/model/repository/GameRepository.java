package cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.repository;

import cat.itacademy.barcelonactiva.BejaranoVizuete.Juan.s05.t01.n01.model.domain.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository <Jugador,String>{

}
