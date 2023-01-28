package S05T02N01.MONGODB.SEC.repository;

import S05T02N01.MONGODB.SEC.model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GameRepository extends MongoRepository<Jugador, String> {

    @Query(value = "{userName:'?0'}")
    Jugador findUserByUsername(String username);
}
