package S05T02N01.MONGODB.model.repository;

import S05T02N01.MONGODB.model.domain.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Jugador,String> {

}
